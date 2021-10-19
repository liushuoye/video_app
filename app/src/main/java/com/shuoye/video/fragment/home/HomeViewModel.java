package com.shuoye.video.fragment.home;

import android.app.Application;
import android.os.Handler;

import androidx.lifecycle.AndroidViewModel;

import com.shuoye.video.pojo.BannerData;
import com.shuoye.video.pojo.TimeLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeViewModel extends AndroidViewModel {
    List<BannerData> bannerData;
    Map<Integer, List<TimeLine>> timeLines;
    Handler.Callback callback;
    HomeRepository repository;

    public HomeViewModel(Application application) {
        super(application);
        bannerData = new ArrayList<>();
        repository = new HomeRepository();
        timeLines = new HashMap<>();
    }

    public void setCallback(Handler.Callback callback) {
        this.callback = callback;
        this.repository.setCallback(this.callback);
        this.repository.getDataBeans(bannerData);
        this.repository.getTimeLines(timeLines);

    }

    public Handler.Callback getCallback() {
        return callback;
    }

    public List<BannerData> getDataBeans() {
        return bannerData;
    }

    public Map<Integer, List<TimeLine>> getTimeLines() {
        return timeLines;
    }
}
