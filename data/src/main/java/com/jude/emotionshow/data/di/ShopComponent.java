package com.jude.emotionshow.data.di;

import com.jude.emotionshow.data.model.ShopModel;
import com.jude.emotionshow.data.provider.ProviderModule;
import com.jude.emotionshow.data.provider.ServiceAPIModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by mike on 2015/12/29.
 */
@Singleton
@Component(modules = {ServiceAPIModule.class, ProviderModule.class})
public interface ShopComponent {
    void inject(ShopModel shopModel);
}
