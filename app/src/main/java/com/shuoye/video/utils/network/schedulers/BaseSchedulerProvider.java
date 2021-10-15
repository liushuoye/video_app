package com.shuoye.video.utils.network.schedulers;


import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;

/**
 * TODO
 **/

public interface BaseSchedulerProvider {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();

    @NonNull
    <T> ObservableTransformer<T, T> applySchedulers();
}
