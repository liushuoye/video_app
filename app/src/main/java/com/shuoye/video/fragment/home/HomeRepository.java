package com.shuoye.video.fragment.home;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.shuoye.video.R;
import com.shuoye.video.pojo.BannerData;
import com.shuoye.video.pojo.TimeLine;
import com.shuoye.video.utils.network.NetWorkManager;
import com.shuoye.video.utils.network.response.Response;

import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import lombok.SneakyThrows;

/**
 * home界面资源库
 *
 * @author shuoye
 * @program Video
 * @ClassName HomeRepository
 * @create 2021-10-16 20:40
 **/
public class HomeRepository {
    static final String TAG = "Liu_shuoye";
    private Handler.Callback callback;

    public HomeRepository() {
        NetWorkManager.getInstance().init();
    }

    public void setCallback(Handler.Callback callback) {
        this.callback = callback;
    }

    public void getDataBeans(List<BannerData> bannerData) {
        NetWorkManager.getRequest()
                .getDataBeans()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<List<BannerData>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: 开始获取轮播图");
                    }

                    @Override
                    public void onNext(@NonNull Response<List<BannerData>> dataBeansResponse) {
                        if (dataBeansResponse.getCode() == 200) {
                            bannerData.addAll(dataBeansResponse.getData());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: 获取轮播图失败", e);
                    }

                    @SneakyThrows
                    @Override
                    public void onComplete() {
                        Message message = new Message();
                        message.what = R.id.setBannerAdapter;
                        callback.handleMessage(message);
                        Log.d(TAG, "onComplete: 成功获取轮播图");
                    }
                });
    }

    public void getTimeLines(Map<Integer, List<TimeLine>> timeLines) {
        NetWorkManager.getRequest()
                .getTimeLines()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<Map<Integer, List<TimeLine>>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: 开始获取新番时间表");
                    }

                    @Override
                    public void onNext(@NonNull Response<Map<Integer, List<TimeLine>>> timeLinesResponse) {
                        if (timeLinesResponse.getCode() == 200) {
                            timeLines.putAll(timeLinesResponse.getData());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: 获取新番时间表失败", e);
                    }

                    @SneakyThrows
                    @Override
                    public void onComplete() {
                        Message message = new Message();
                        message.what = R.id.setTimeLine;
                        callback.handleMessage(message);
                        Log.d(TAG, "onComplete: 成功获取新番时间表");
                    }
                });
    }
}
