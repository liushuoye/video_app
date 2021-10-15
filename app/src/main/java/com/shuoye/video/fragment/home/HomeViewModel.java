package com.shuoye.video.fragment.home;

import android.app.Application;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.alibaba.fastjson.JSONObject;
import com.shuoye.video.fragment.Repository;
import com.shuoye.video.pojo.DataBean;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeViewModel extends AndroidViewModel {
    List<DataBean> dataBeanList;//轮播图资源列表
    Handler.Callback callback;
    Repository repository;
    Map<Integer, List<JSONObject>> dataRenew;

    public HomeViewModel(@NonNull @NotNull Application application) {
        super(application);
        dataBeanList = new ArrayList<>();
        repository = new Repository(application);
        dataRenew = new HashMap<>();
    }

    public List<DataBean> getDataBeanList() {
        return dataBeanList;
    }

    public Handler.Callback getCallback() {
        return callback;
    }

    public void setCallback(Handler.Callback callback) {
        this.callback = callback;
        repository.getDataBeanList(dataBeanList, callback);
        repository.getAnimeRenew(callback,dataRenew);
    }

    public Map<Integer, List<JSONObject>> getDataRenew() {
        return dataRenew;
    }
}
