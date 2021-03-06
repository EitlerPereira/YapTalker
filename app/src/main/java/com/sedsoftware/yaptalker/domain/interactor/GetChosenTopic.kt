package com.sedsoftware.yaptalker.domain.interactor

import com.sedsoftware.yaptalker.domain.entity.BaseEntity
import com.sedsoftware.yaptalker.domain.repository.ChosenTopicRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetChosenTopic @Inject constructor(
    private val chosenTopicRepository: ChosenTopicRepository
) : UseCase<List<BaseEntity>, GetChosenTopic.Params> {

  override fun buildUseCaseObservable(params: Params): Observable<List<BaseEntity>> =
      chosenTopicRepository
          .getChosenTopic(params.forumId, params.topicId, params.startPage)

  class Params(val forumId: Int, val topicId: Int, val startPage: Int)
}
