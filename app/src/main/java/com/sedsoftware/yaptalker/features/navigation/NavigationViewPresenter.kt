package com.sedsoftware.yaptalker.features.navigation

import android.os.Bundle
import com.arellomobile.mvp.InjectViewState
import com.sedsoftware.yaptalker.features.base.BasePresenter
import com.sedsoftware.yaptalker.commons.enums.PresenterLifecycle
import com.uber.autodispose.kotlin.autoDisposeWith

@InjectViewState
class NavigationViewPresenter : BasePresenter<NavigationView>() {

  override fun onFirstViewAttach() {
    super.onFirstViewAttach()

    titleChannel
        .autoDisposeWith(event(PresenterLifecycle.DESTROY))
        .subscribe { text -> viewState.setAppbarTitle(text) }
  }

  fun initLayout(savedInstanceState: Bundle?) {
    viewState.initDrawer(savedInstanceState)
  }

  fun onNavigationClicked(@Navigation.Section section: Long) {
    viewState.goToChosenSection(section)
  }

  fun getFirstLaunchPage() = settings.getStartingPage()
}
