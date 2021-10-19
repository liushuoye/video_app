package com.shuoye.video.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.RequiresApi;

import com.alibaba.fastjson.JSONObject;
import com.shuoye.video.pojo.BannerData;
import com.shuoye.video.pojo.PlayLine;

import java.util.List;
import java.util.Map;

public class Repository {
    static final String TAG = "shuoye";
    Context context;

    public Repository(Context context) {
        // Database database = Database.getInstance(context.getApplicationContext());

        this.context = context;
    }

    /**
     * 获取轮播图
     *
     * @param bannerDataList 返回数据
     * @param callback     传递消息
     */
    public void getDataBeanList(List<BannerData> bannerDataList, Handler.Callback callback) {

    }

    /**
     * 获取更新番剧
     *
     * @param callback  传递消息
     * @param dataRenew 返回数据
     */
    public void getAnimeRenew(Handler.Callback callback, Map<Integer, List<JSONObject>> dataRenew) {

    }

    /**
     * 获取分类标签
     *
     * @param callback     传递消息
     * @param dataClassTag 返回数据
     */
    public void getAnimeClassTag(Handler.Callback callback, Map<Integer, List<String>> dataClassTag) {

    }

    /**
     * 获取番剧列表
     *
     * @param callback  传递消息
     * @param animeList 返回数据
     * @param data      post参数
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getAnimeList(Handler.Callback callback, List<JSONObject> animeList, Map<String, String> data) {

    }

    /**
     * 获取番剧信息
     *
     * @param callback  传递消息
     * @param animeInfo 返回数据
     * @param data      post参数
     */
    public void getAnimeInfo(Handler.Callback callback, List<JSONObject> animeInfo, Map<String, String> data) {

    }

    /**
     * 获取播放链接
     *
     * @param callback     传递消息
     * @param playLineList 返回数据
     * @param id           post参数
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getAnimePlayData(Handler.Callback callback, List<PlayLine> playLineList, String id) {

    }

    /**
     * 获取播放链接
     *
     * @param callback 传递消息
     * @param dramaUrl 源地址
     */
    public void getAnimePlayUrl(Handler.Callback callback, String dramaUrl) {

    }
}
