package BmobApi;


import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class InterceptorUtil {
    /**
     * 在Request中添加Header内容
     *
     * @return
     */
    public static Interceptor headerInterceptor() {
        return new Interceptor() {
            public Response intercept(okhttp3.Interceptor.Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("Content-Type", "application/json; charset=UTF-8")
                        .addHeader("X-Bmob-Application-Id", BmobBaseConfig.appId)
                        .addHeader("X-Bmob-REST-API-Key", BmobBaseConfig.apiKey)
                        .build();
                return chain.proceed(request);
            }
        };
    }
//
//    /**
//     * 日志拦截器
//     * @return
//     */
//    public static HttpLoggingInterceptor logInterceptor(){
//        //日志显示级别
//        HttpLoggingInterceptor.Level level= HttpLoggingInterceptor.Level.BODY;
//        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
//            public void log(String message) {
//                log.log(Level.INFO, message);
//            }
//        }).setLevel(level);
//    }
}
