package com.sedsoftware.yaptalker.model.base

import com.sedsoftware.yaptalker.model.YapEntity
import com.sedsoftware.yaptalker.model.YapEntityTypes

/**
 * Class which represents active topic item in presentation layer.
 */
class ActiveTopicModel(
    val title: String,
    val link: String,
    val isPinned: Boolean,
    val isClosed: Boolean,
    val forumTitle: String,
    val forumLink: String,
    val rating: Int,
    val answers: Int,
    val lastPostDate: String
) : YapEntity {

  override fun getBaseEntityType(): Int = YapEntityTypes.ACTIVE_TOPIC_ITEM
}
