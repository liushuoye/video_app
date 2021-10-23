package com.shuoye.video.module

import android.content.Context
import com.shuoye.video.database.AppDatabase
import com.shuoye.video.database.dao.TimeLineDao
import com.shuoye.video.database.dao.TimeLineKeyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * TODO
 * @program Video
 * @ClassName DatabaseModule
 * @author shuoye
 * @create 2021-10-23 13:15
 **/
@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }
    @Singleton
    @Provides
    fun provideTimeLineDao(appDatabase: AppDatabase): TimeLineDao {
        return appDatabase.timeLineDao()
    }
    @Singleton
    @Provides
    fun provideTimeLineKeyDao(appDatabase: AppDatabase): TimeLineKeyDao {
        return appDatabase.timeLineKeyDao()
    }
}