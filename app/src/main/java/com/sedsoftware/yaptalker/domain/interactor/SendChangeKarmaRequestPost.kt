package com.sedsoftware.yaptalker.domain.interactor

import com.sedsoftware.yaptalker.domain.entity.BaseEntity
import com.sedsoftware.yaptalker.domain.service.KarmaService
import io.reactivex.Observable
import javax.inject.Inject

class SendChangeKarmaRequestPost @Inject constructor(
    private val karmaService: KarmaService
) : UseCase<BaseEntity, SendChangeKarmaRequestPost.Params> {

  override fun buildUseCaseObservable(params: Params): Observable<BaseEntity> =
      karmaService
          .requestPostKarmaChange(params.targetPostId, params.targetTopicId, params.diff)

  class Params(val targetPostId: Int, val targetTopicId: Int, val diff: Int)
}
