package com.shuoye.video.database.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.shuoye.video.AppExecutors
import com.shuoye.video.api.request.Resource
import com.shuoye.video.api.response.ApiResponse

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 *
 *
 * You can read more about it in the [Architecture
 * Guide](https://developer.android.com/arch).
 * @param <ResultType>
 * @param <RequestType>
</RequestType></ResultType> */
abstract class NetworkBoundResource<ResultType, RequestType>
@MainThread constructor(private val appExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)
        @Suppress("LeakingThis")
        val dbSource = loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    setValue(Resource.success(newData))
                }
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()
        // 我们重新附加 dbSource 作为一个新的源，它会快速发送它的最新值
        result.addSource(dbSource) { newData ->
            setValue(Resource.loading(newData))
        }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response.code) {
                200 -> {
                    appExecutors.diskIO().execute {
                        saveCallResult(processResponse(response))
                        appExecutors.mainThread().execute {
                            // 我们特别请求一个新的实时数据，否则我们将立即获得最后一个缓存值，
                            // 该值可能不会更新为从网络接收到的最新结果。
                            result.addSource(loadFromDb()) { newData ->
                                setValue(Resource.success(newData))
                            }
                        }
                    }
                }
                204 -> {
                    appExecutors.mainThread().execute {
                        // reload from disk whatever we had
                        result.addSource(loadFromDb()) { newData ->
                            setValue(Resource.success(newData))
                        }
                    }
                }
                404 -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                        setValue(Resource.error(response.msg, newData))
                    }
                }
            }
        }
    }

    /**
     * 网络数据获取失败时调用
     */
    protected open fun onFetchFailed() {}

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    @WorkerThread
    protected open fun processResponse(apiResponse: ApiResponse<RequestType>) = apiResponse.data

    /**
     * 当要把网络数据存储到数据库中时调用
     */
    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    /**
     * 决定是否去网络获取数据
     */
    @MainThread
    protected abstract  fun shouldFetch(data: ResultType?): Boolean

    /**
     * 用于从数据库中获取缓存数据
     */
    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    /**
     * 创建网络数据请求
     */
    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
}
