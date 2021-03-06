package com.sedsoftware.yaptalker.presentation.features.forumslist

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout
import com.sedsoftware.yaptalker.R
import com.sedsoftware.yaptalker.presentation.base.enums.lifecycle.FragmentLifecycle
import com.sedsoftware.yaptalker.presentation.base.enums.navigation.NavigationSection
import com.sedsoftware.yaptalker.data.SettingsManager
import com.sedsoftware.yaptalker.presentation.base.BaseFragment
import com.sedsoftware.yaptalker.presentation.extensions.setIndicatorColorScheme
import com.sedsoftware.yaptalker.presentation.extensions.stringRes
import com.sedsoftware.yaptalker.presentation.extensions.toastError
import com.sedsoftware.yaptalker.presentation.features.forumslist.adapter.ForumsAdapter
import com.sedsoftware.yaptalker.presentation.features.forumslist.adapter.ForumsItemClickListener
import com.sedsoftware.yaptalker.presentation.model.YapEntity
import com.uber.autodispose.kotlin.autoDisposable
import kotlinx.android.synthetic.main.fragment_forums_list.*
import javax.inject.Inject

class ForumsFragment : BaseFragment(), ForumsView, ForumsItemClickListener {

  companion object {
    fun getNewInstance() = ForumsFragment()
  }

  override val layoutId: Int
    get() = R.layout.fragment_forums_list

  @Inject
  lateinit var settings: SettingsManager

  @Inject
  @InjectPresenter
  lateinit var presenter: ForumsPresenter

  @ProvidePresenter
  fun provideForumsPresenter() = presenter

  private lateinit var forumsAdapter: ForumsAdapter

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    forumsAdapter = ForumsAdapter(this, settings)
    forumsAdapter.setHasStableIds(true)

    with(forums_list) {
      val linearLayout = LinearLayoutManager(context)
      layoutManager = linearLayout
      adapter = forumsAdapter
      addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
      setHasFixedSize(true)
    }

    forums_list_refresh_layout.setIndicatorColorScheme()

    subscribeViews()
  }

  override fun showLoadingIndicator() {
    forums_list_refresh_layout.isRefreshing = true
  }

  override fun hideLoadingIndicator() {
    forums_list_refresh_layout.isRefreshing = false
  }

  override fun showErrorMessage(message: String) {
    toastError(message)
  }

  override fun appendForumItem(item: YapEntity) {
    forumsAdapter.addForumsListItem(item)
  }

  override fun clearForumsList() {
    forumsAdapter.clearForumsList()
  }

  override fun updateCurrentUiState() {
    context?.stringRes(R.string.nav_drawer_forums)?.let { presenter.setAppbarTitle(it) }
    presenter.setNavDrawerItem(NavigationSection.FORUMS)
  }

  override fun onForumItemClick(forumId: Int) {
    presenter.navigateToChosenForum(forumId)
  }

  private fun subscribeViews() {
    RxSwipeRefreshLayout
        .refreshes(forums_list_refresh_layout)
        .autoDisposable(event(FragmentLifecycle.DESTROY))
        .subscribe { presenter.loadForumsList() }
  }
}
