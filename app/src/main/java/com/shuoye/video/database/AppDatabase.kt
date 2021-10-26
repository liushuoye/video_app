package com.shuoye.video.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shuoye.video.DATABASE_NAME
import com.shuoye.video.database.dao.*
import com.shuoye.video.database.pojo.*

/**
 * TODO
 * @program Video
 * @ClassName AppDatabase
 * @author shuoye
 * @create 2021-10-23 00:22
 **/
@Database(
    entities = [
        TimeLine::class, TimeLineKey::class,
        RecentUpdates::class,
        RecommendedDaily::class,
        Banner::class, BannerKey::class,
        Tag::class,
        AnimeInfo::class,
        Player::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun timeLineDao(): TimeLineDao
    abstract fun timeLineKeyDao(): TimeLineKeyDao
    abstract fun recentUpdatesDao(): RecentUpdatesDao
    abstract fun recommendedDailyDao(): RecommendedDailyDao
    abstract fun bannerDao(): BannerDao
    abstract fun bannerKeyDao(): BannerKeyDao
    abstract fun tagDao(): TagDao
    abstract fun animeInfoDao(): AnimeInfoDao
    abstract fun animeDao(): AnimeDao
    abstract fun playerDao(): PlayerDao

    companion object {

        //单例实例化
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // 创建并预填充数据库。有关更多详细信息，请参阅这篇文章：
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                // 破坏性数据库迁移
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}