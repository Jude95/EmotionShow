package com.jude.emotionshow.data.di;

import com.jude.emotionshow.data.model.SeedModel;
import com.jude.emotionshow.data.provider.ProviderModule;
import com.jude.emotionshow.data.provider.ServiceAPIModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Mr.Jude on 2015/11/19.
 */
@Singleton
@Component(modules = {ServiceAPIModule.class, ProviderModule.class})
public interface SeedComponent {
    void inject(SeedModel model);
}
