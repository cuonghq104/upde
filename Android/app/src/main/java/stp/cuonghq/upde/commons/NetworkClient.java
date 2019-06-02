package stp.cuonghq.upde.commons;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by cuong.hq1 on 4/16/2019.
 */

public class NetworkClient {
    private static Retrofit sInstance;

    public static Retrofit getRetrofit() {
        if (sInstance == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            OkHttpClient okHttpClient = builder.build();

            sInstance = new Retrofit.Builder()
                    .baseUrl(Constants.ApiConstant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }

        return sInstance;
    }

    private static Retrofit sHeaderInstance;

    public static void initHeaderInstance(final String authToken) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader(Constants.ApiConstant.HEADER_AUTHORIZATION, authToken).build();
                return chain.proceed(request);
            }
        });

        sHeaderInstance = new Retrofit.Builder()
                .baseUrl(Constants.ApiConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build())
                .build();
    }

    public static Retrofit getHeaderInstance() {
        return sHeaderInstance;
    }
}
