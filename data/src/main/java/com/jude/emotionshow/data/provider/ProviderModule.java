package com.jude.emotionshow.data.provider;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

/**
 * Created by Mr.Jude on 2015/11/18.
 */
@Module
public class ProviderModule {

    /**
     * Provide the {@link Gson} singleton.
     *
     * @return the {@link Gson} singleton.
     */
    @Singleton
    @Provides
    Gson provideGson() {
        return GsonProvider.provideGson();
    }

    /**
     * Provide the {@link RestAdapter} singleton.
     *
     * @return the {@link RestAdapter} singleton.
     */
    @Singleton
    @Provides
    RestAdapter provideRetrofit() {
        return RestProvider.provideRetrofit();
    }

    /**
     * Provide the {@link OkHttpClient} singleton.
     *
     * @return the {@link OkHttpClient} singleton.
     */
    @Singleton
    @Provides
    OkHttpClient provideHttpClient() {
        return HttpClientProvider.provideHttpClient();
    }
}