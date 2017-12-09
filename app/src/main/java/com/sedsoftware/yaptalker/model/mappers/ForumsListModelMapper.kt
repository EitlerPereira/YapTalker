package com.sedsoftware.yaptalker.model.mappers

import com.sedsoftware.domain.entity.BaseEntity
import com.sedsoftware.domain.entity.base.Forum
import com.sedsoftware.yaptalker.model.YapEntity
import com.sedsoftware.yaptalker.model.base.ForumModel

/**
 * Mapper class used to transform forums list entities from the domain layer into YapEntity list
 * in the presentation layer.
 */
class ForumsListModelMapper {

  fun transform(items: List<BaseEntity>): List<YapEntity> {

    val result: MutableList<YapEntity> = ArrayList(items.size)

    items.forEach { item ->
      if (item is Forum) {
        result.add(ForumModel(
            title = item.title,
            forumId = item.forumId,
            lastTopicTitle = item.lastTopicTitle,
            lastTopicAuthor = item.lastTopicAuthor,
            date = item.date
        ))
      }
    }

    return result
  }
}
