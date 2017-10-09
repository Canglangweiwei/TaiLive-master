package jc.geecity.taihua.config;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import jc.geecity.taihua.BuildConfig;
import jc.geecity.taihua.base.AbsBaseApplication;
import jc.geecity.taihua.config.api.IUserApiService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <p>
 * 初始化网络请求工具
 * </p>
 * Created by weiwei on 2017/6/23.
 */
@SuppressWarnings("ALL")
public class RetrofitFactory {

    private static IUserApiService iUserApiService;

    private static RetrofitFactory retrofitFactory;

    private RetrofitFactory(Context context) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        /**
         * 用户信息
         */
        Retrofit userRetrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        iUserApiService = userRetrofit.create(IUserApiService.class);
    }

    /**
     * 单例模式
     */
    public static synchronized RetrofitFactory get() {
        RetrofitFactory tmp = retrofitFactory;
        if (tmp == null) {
            synchronized (RetrofitFactory.class) {
                tmp = retrofitFactory;
                if (tmp == null) {
                    if (AbsBaseApplication.sApp != null) {
                        tmp = new RetrofitFactory(AbsBaseApplication.sApp);
                    } else {
                        tmp = new RetrofitFactory(null);
                    }
                    retrofitFactory = tmp;
                }
            }
        }
        return tmp;
    }

    /**
     * 获取用户信息
     */
    public IUserApiService getUserApiService() {
        return iUserApiService;
    }
}
