package com.sedsoftware.data.parsing.mappers

import com.sedsoftware.data.parsing.TopicPageParsed
import com.sedsoftware.data.parsing.mappers.util.PostContentParser
import com.sedsoftware.domain.entity.BaseEntity
import com.sedsoftware.domain.entity.base.NavigationPanel
import com.sedsoftware.domain.entity.base.SinglePost
import com.sedsoftware.domain.entity.base.TopicInfoBlock

/**
 * Mapper class used to transform parsed topic page from the data layer into BaseEntity list
 * in the domain layer.
 */
class TopicPageMapper {

  companion object {
    private const val POSTS_PER_PAGE = 25
  }

  fun transform(topicPage: TopicPageParsed): List<BaseEntity> {

    val result: MutableList<BaseEntity> = ArrayList(POSTS_PER_PAGE)

    with(topicPage) {
      result.add(TopicInfoBlock(
          topicTitle = topicTitle,
          isClosed = isClosed.isNotEmpty(),
          authKey = authKey,
          topicRating = topicRating.toInt(),
          topicRatingPlusAvailable = topicRatingPlusAvailable.isNotEmpty(),
          topicRatingMinusAvailable = topicRatingMinusAvailable.isNotEmpty(),
          topicRatingPlusClicked= topicRatingPlusClicked.isNotEmpty(),
          topicRatingMinusClicked = topicRatingMinusClicked.isNotEmpty(),
          topicRatingTargetId = topicRatingTargetId.toInt()
      ))

      result.add(NavigationPanel(
          currentPage = navigation.currentPage.toInt(),
          totalPages = navigation.totalPages.toInt()
      ))

      posts.forEach { post ->
        result.add(SinglePost(
            authorNickname = post.authorNickname,
            authorProfile = post.authorProfile,
            authorAvatar = post.authorAvatar,
            authorMessagesCount = post.authorMessagesCount.toInt(),
            postDate = post.postDate,
            postRank = post.postRank,
            postRankPlusAvailable = post.postRankPlusAvailable.isNotEmpty(),
            postRankMinusAvailable = post.postRankMinusAvailable.isNotEmpty(),
            postRankPlusClicked = post.postRankPlusClicked.isNotEmpty(),
            postRankMinusClicked = post.postRankMinusClicked.isNotEmpty(),
            postContentParsed = PostContentParser(post.postContent).getParsedPost(),
            postId = post.postId.toInt()
        ))
      }
    }

    return result
  }
}
