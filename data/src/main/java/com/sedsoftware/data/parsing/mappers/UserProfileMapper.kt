package com.sedsoftware.data.parsing.mappers

import com.sedsoftware.data.parsing.UserProfileParsed
import com.sedsoftware.domain.entity.BaseEntity
import com.sedsoftware.domain.entity.base.UserProfile

/**
 * Mapper class used to transform parsed user profile from the data layer into BaseEntity in the domain layer.
 */
class UserProfileMapper {

  fun transform(profile: UserProfileParsed): BaseEntity =
      UserProfile(
          nickname = profile.nickname,
          avatar = profile.avatar,
          photo = profile.photo,
          group = profile.group,
          status = profile.status,
          uq = profile.uq,
          signature = profile.signature,
          rewards = profile.rewards,
          registerDate = profile.registerDate,
          timeZone = profile.timeZone,
          website = profile.website,
          birthDate = profile.birthDate,
          location = profile.location,
          interests = profile.interests,
          sex = profile.sex,
          messagesCount = profile.messagesCount,
          messsagesPerDay = profile.messsagesPerDay,
          bayans = profile.bayans,
          todayTopics = profile.todayTopics,
          email = profile.email,
          icq = profile.icq
      )
}
