package com.shuoye.video.fragment.home;

import android.content.Context;
import android.util.AttributeSet;

import com.shuoye.video.pojo.DataBean;
import com.youth.banner.Banner;

/**
 * 自定义轮播图
 *
 * @author shuoy
 */
public class MyBanner extends Banner<DataBean, ImageAdapter> {
    public MyBanner(Context context) {
        super(context);
    }

    public MyBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
