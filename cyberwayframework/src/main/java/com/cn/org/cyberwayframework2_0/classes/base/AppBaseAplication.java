package com.cn.org.cyberwayframework2_0.classes.base;

import android.app.Application;

import com.cn.org.cyberwayframework2_0.classes.http.LoggerInterceptor;
import com.cn.org.cyberwayframework2_0.classes.http.OkHttpUtils;
import com.cn.org.cyberwayframework2_0.classes.http.cookie.CookieJarImpl;
import com.cn.org.cyberwayframework2_0.classes.http.cookie.store.MemoryCookieStore;
import com.cn.org.cyberwayframework2_0.classes.http.https.HttpsUtils;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;

/**
 * Created by Smile on 16/11/25.
 */

public class AppBaseAplication extends Application{
    @Override
    public void onCreate()
    {
        super.onCreate();

//        ClearableCookieJar cookieJar1 = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(getApplicationContext()));

        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);

        CookieJarImpl cookieJar1 = new CookieJarImpl(new MemoryCookieStore());
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor("TAG"))
                .cookieJar(cookieJar1)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session)
                    {
                        return true;
                    }
                })
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();
        OkHttpUtils.initClient(okHttpClient);

    }
}
