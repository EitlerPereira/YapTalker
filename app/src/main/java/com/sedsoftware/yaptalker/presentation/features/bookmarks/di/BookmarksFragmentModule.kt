package com.sedsoftware.yaptalker.presentation.features.bookmarks.di

import com.sedsoftware.yaptalker.data.repository.YapBookmarksRepository
import com.sedsoftware.yaptalker.data.service.YapDeleteBookmarkService
import com.sedsoftware.yaptalker.di.scopes.FragmentScope
import com.sedsoftware.yaptalker.domain.repository.BookmarksRepository
import com.sedsoftware.yaptalker.domain.service.DeleteBookmarkService
import dagger.Binds
import dagger.Module

@Module
abstract class BookmarksFragmentModule {

  @FragmentScope
  @Binds
  abstract fun bookmarksRepository(repository: YapBookmarksRepository): BookmarksRepository

  @FragmentScope
  @Binds
  abstract fun deleteBookmarkService(service: YapDeleteBookmarkService): DeleteBookmarkService
}
