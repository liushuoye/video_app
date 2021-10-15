package com.shuoye.video;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.shuoye.video.utils.Utils;

import static com.shuoye.video.utils.Utils.getContext;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.init(this);
        setContentView(R.layout.activity_main);
    }

}