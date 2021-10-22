package com.shuoye.video.module

import com.shuoye.video.api.TimeLineService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * 依赖注入模块
 * @program video
 * @ClassName TimeLineModule
 * @author shuoye
 * @create 2021-10-22 12:34
 **/
@Module
@InstallIn(SingletonComponent::class)
class TimeLineModule {

    @Singleton
    @Provides
    fun getTimeLineService(): TimeLineService {
        return TimeLineService.create()
    }
}