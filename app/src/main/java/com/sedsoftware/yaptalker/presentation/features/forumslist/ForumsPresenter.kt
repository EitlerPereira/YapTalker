package com.sedsoftware.yaptalker.presentation.features.forumslist

import com.arellomobile.mvp.InjectViewState
import com.sedsoftware.yaptalker.presentation.base.enums.lifecycle.PresenterLifecycle
import com.sedsoftware.yaptalker.presentation.base.enums.navigation.NavigationScreen
import com.sedsoftware.yaptalker.domain.entity.BaseEntity
import com.sedsoftware.yaptalker.domain.interactor.GetForumsList
import com.sedsoftware.yaptalker.presentation.base.BasePresenter
import com.sedsoftware.yaptalker.presentation.mappers.ForumsListModelMapper
import com.sedsoftware.yaptalker.presentation.model.YapEntity
import com.uber.autodispose.kotlin.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class ForumsPresenter @Inject constructor(
    private val router: Router,
    private val forumsListUseCase: GetForumsList,
    private val forumsListModelMapper: ForumsListModelMapper
) : BasePresenter<ForumsView>() {

  private var clearCurrentList = false

  override fun onFirstViewAttach() {
    super.onFirstViewAttach()
    loadForumsList()
  }

  override fun attachView(view: ForumsView?) {
    super.attachView(view)
    viewState.updateCurrentUiState()
  }

  fun navigateToChosenForum(forumId: Int) {
    router.navigateTo(NavigationScreen.CHOSEN_FORUM_SCREEN, forumId)
  }

  fun loadForumsList() {

    clearCurrentList = true

    forumsListUseCase
        .buildUseCaseObservable(Unit)
        .subscribeOn(Schedulers.io())
        .map { forumItem: BaseEntity -> forumsListModelMapper.transform(forumItem) }
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { viewState.showLoadingIndicator() }
        .doFinally { viewState.hideLoadingIndicator() }
        .autoDisposable(event(PresenterLifecycle.DESTROY))
        .subscribe(getForumsListObserver())
  }

  private fun getForumsListObserver() =
      object : DisposableObserver<YapEntity>() {

        override fun onNext(item: YapEntity) {

          if (clearCurrentList) {
            clearCurrentList = false
            viewState.clearForumsList()
          }

          viewState.appendForumItem(item)
        }

        override fun onComplete() {
          Timber.i("Forums list loading completed.")
        }

        override fun onError(e: Throwable) {
          e.message?.let { viewState.showErrorMessage(it) }
        }
      }
}
