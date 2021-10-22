package com.shuoye.video.database

import android.content.Context
import android.util.JsonReader
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.common.reflect.TypeToken
import com.shuoye.video.database.pojo.TimeLine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * TODO
 * @program Video
 * @ClassName SeedDatabaseWorker
 * @author shuoye
 * @create 2021-10-23 00:46
 **/
class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val fileName = inputData.getString(KEY_FILENAME)
            if (fileName != null) {
                applicationContext.assets.open(fileName).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val plantType = object : TypeToken<List<TimeLine>>() {}.type
//                        val plantList: List<TimeLine> = Gson().fromJson(jsonReader, plantType)

                        val database = AppDatabase.getInstance(applicationContext)
//                        database.getTimeLiteDao().inserts(plantList)

                        Result.success()
                    }
                }
            } else {
                Log.e(TAG, "播种数据库时出错 - 没有有效的文件名")
                Result.failure()
            }
        } catch (ex: Exception) {
            Log.e(TAG, "播种数据库时出错", ex)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "SeedDatabaseWorker"
        const val KEY_FILENAME = "VIDEO_DATA_FILENAME"
    }
}