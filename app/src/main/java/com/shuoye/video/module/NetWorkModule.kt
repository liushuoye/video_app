package com.shuoye.video.module

import com.shuoye.video.api.NetWorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * TODO
 * @program Video
 * @ClassName NetWorkModule
 * @author shuoye
 * @create 2021-10-23 16:17
 **/
@InstallIn(SingletonComponent::class)
@Module
class NetWorkModule {

    @Singleton
    @Provides
    fun provideNetWorkManager(): NetWorkManager {
        return NetWorkManager.getInstance()
    }
}