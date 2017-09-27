package com.sedsoftware.yaptalker.data.remote.video

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.github.salomonbrys.kodein.with
import com.sedsoftware.yaptalker.data.remote.CoubLoader
import com.sedsoftware.yaptalker.data.remote.RutubeLoader
import com.sedsoftware.yaptalker.data.remote.VkLoader
import com.sedsoftware.yaptalker.data.remote.YapVideoLoader
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

val thumbnailsManagerModule = Kodein.Module {

  constant("COUB_VIDEO_ENDPOINT") with "http://coub.com/"
  constant("RUTUBE_VIDEO_ENDPOINT") with "http://rutube.ru/"
  constant("YAP_VIDEO_ENDPOINT") with "http://api.yapfiles.ru/"
  constant("VK_VIDEO_ENDPOINT") with "https://api.vk.com/"

  bind<CoubLoader>("CoubLoader") with singleton {
    Retrofit.Builder()
        .baseUrl(instance<String>("COUB_VIDEO_ENDPOINT"))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(CoubLoader::class.java)
  }

  bind<RutubeLoader>("RutubeLoader") with singleton {
    Retrofit.Builder()
        .baseUrl(instance<String>("RUTUBE_VIDEO_ENDPOINT"))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(RutubeLoader::class.java)
  }

  bind<YapVideoLoader>("YapVideoLoader") with singleton {
    Retrofit.Builder()
        .baseUrl(instance<String>("YAP_VIDEO_ENDPOINT"))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(YapVideoLoader::class.java)
  }

  bind<VkLoader>("VkLoader") with singleton {
    Retrofit.Builder()
        .baseUrl(instance<String>("VK_VIDEO_ENDPOINT"))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(VkLoader::class.java)
  }

  bind<ThumbnailsManager>() with singleton {
    ThumbnailsManager(
        instance("CoubLoader"),
        instance("RutubeLoader"),
        instance("YapVideoLoader"),
        instance("VkLoader"))
  }
}