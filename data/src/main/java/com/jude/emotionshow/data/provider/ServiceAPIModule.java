package com.jude.emotionshow.data.provider;

import com.jude.emotionshow.domain.api.ServiceAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

/**
 * Created by Mr.Jude on 2015/11/18.
 */
@Module
public class ServiceAPIModule {
    @Singleton
    @Provides
    ServiceAPI provideServiceAPI(final RestAdapter retrofit) {
        return retrofit.create(ServiceAPI.class);
    }
}
