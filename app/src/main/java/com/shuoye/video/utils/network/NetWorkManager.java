package com.shuoye.video.utils.network;


import com.shuoye.video.utils.network.converter.FastJsonConverterFactory;
import com.shuoye.video.utils.network.interceptor.CacheInterceptor;
import com.shuoye.video.utils.network.interceptor.EncodingInterceptor;
import com.shuoye.video.utils.network.request.Request;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 网络管理器
 **/
public class NetWorkManager {

    private static NetWorkManager mInstance;
    private static Retrofit retrofit;
    private static volatile Request request = null;

    public static NetWorkManager getInstance() {
        if (mInstance == null) {
            synchronized (NetWorkManager.class) {
                if (mInstance == null) {
                    mInstance = new NetWorkManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化必要对象和参数
     */
    public void init() {
        init(false);
    }

    /**
     * 初始化必要对象和参数
     *
     * @param isProxy boolean 是否使用代理
     */
    public void init(boolean isProxy) {
//
//        if (retrofit != null) {
//            return;
//        }
        String host = "localhost";
        int port = 10810;

        // 初始化okhttp
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(logging)//日志
                .addInterceptor(chain -> {
                    okhttp3.Request build = chain.request()
                            .newBuilder()
                            .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36")
                            .addHeader("Accept", "*/*")
                            .build();
                    return chain.proceed(build);
                })//请求头
                .connectTimeout(60, TimeUnit.SECONDS)//链接超时为60秒，单位为秒
                .writeTimeout(60, TimeUnit.SECONDS)//写入超时
                .readTimeout(60, TimeUnit.SECONDS)//读取超时
                .retryOnConnectionFailure(true)//连接失败重试
                .addInterceptor(new EncodingInterceptor("gbk"))//设置编码拦截器
                .addNetworkInterceptor(new CacheInterceptor())//设置缓存拦截器
                .cache(new Cache(new File("cache", "responses"), 1024 * 1024 * 500));//设置缓存路径

        OkHttpClient client;
        if (isProxy) {
            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(host, port), 1000); //设置超时1000毫秒
                socket.close();
                client = builder
                        .proxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(host, port)))//加速器代理
                        .hostnameVerifier(HttpsUtils.getHostnameVerifier(false))//验证主机名
                        .sslSocketFactory(HttpsUtils.getSslSocketFactoryUnsafe().sSLSocketFactory, HttpsUtils.getSslSocketFactoryUnsafe().trustManager)//设置SSL
                        .build();
            } catch (IOException e) {
                client = builder
                        .build();
            }
        } else {
            client = builder.build();
        }
        // 初始化 Retrofit
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(Request.HOST)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())//支持返回原始String
                .addConverterFactory(FastJsonConverterFactory.create())//支持返回FastJson解析后的实体类
                .build();
    }

    public static Request getRequest() {
        if (retrofit == null) {
            NetWorkManager.getInstance().init();
        }
        if (request == null) {
            synchronized (Request.class) {
                request = retrofit.create(Request.class);
            }
        }
        return request;
    }
}
