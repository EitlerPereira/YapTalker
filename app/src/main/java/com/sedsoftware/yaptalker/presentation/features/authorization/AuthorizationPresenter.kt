package com.sedsoftware.yaptalker.presentation.features.authorization

import com.arellomobile.mvp.InjectViewState
import com.sedsoftware.yaptalker.presentation.base.enums.lifecycle.PresenterLifecycle
import com.sedsoftware.yaptalker.presentation.base.enums.navigation.RequestCode
import com.sedsoftware.yaptalker.domain.entity.BaseEntity
import com.sedsoftware.yaptalker.domain.interactor.SendSignInRequest
import com.sedsoftware.yaptalker.domain.interactor.SendSignInRequest.Params
import com.sedsoftware.yaptalker.presentation.base.BasePresenter
import com.sedsoftware.yaptalker.presentation.mappers.ServerResponseModelMapper
import com.sedsoftware.yaptalker.presentation.model.YapEntity
import com.sedsoftware.yaptalker.presentation.model.base.ServerResponseModel
import com.uber.autodispose.kotlin.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class AuthorizationPresenter @Inject constructor(
    private val router: Router,
    private val signInRequestUseCase: SendSignInRequest,
    private val serverResponseMapper: ServerResponseModelMapper
) : BasePresenter<AuthorizationView>() {

  companion object {
    private const val ERROR_MESSAGE = "Обнаружены следующие ошибки"
    private const val SUCCESS_MESSAGE = "Спасибо"
  }

  override fun attachView(view: AuthorizationView?) {
    super.attachView(view)
    viewState.updateCurrentUiState()
  }

  override fun detachView(view: AuthorizationView?) {
    viewState.hideKeyboard()
    super.detachView(view)
  }

  fun handleSignInButton(enabled: Boolean) {
    viewState.signInButtonEnabled(enabled)
  }

  fun performLoginAttempt(userLogin: String, userPassword: String) {
    signInRequestUseCase
        .buildUseCaseObservable(Params(login = userLogin, password = userPassword))
        .subscribeOn(Schedulers.io())
        .map { response: BaseEntity -> serverResponseMapper.transform(response) }
        .observeOn(AndroidSchedulers.mainThread())
        .autoDisposable(event(PresenterLifecycle.DESTROY))
        .subscribe(getSignInResponseObserver())
  }

  private fun getSignInResponseObserver() =
      object : DisposableObserver<YapEntity>() {

        override fun onNext(response: YapEntity) {
          checkIfSignedInSuccessfully(response)
        }

        override fun onComplete() {
          Timber.i("Sign In request completed.")
        }

        override fun onError(e: Throwable) {
          e.message?.let { viewState.showErrorMessage(it) }
        }
      }

  private fun checkIfSignedInSuccessfully(serverResponse: YapEntity) {

    serverResponse as ServerResponseModel

    if (serverResponse.text.contains(ERROR_MESSAGE)) {
      viewState.loginErrorMessage()
    } else if (serverResponse.text.contains(SUCCESS_MESSAGE)) {
      viewState.loginSuccessMessage()
      router.exitWithResult(RequestCode.SIGN_IN, true)
    }
  }
}
