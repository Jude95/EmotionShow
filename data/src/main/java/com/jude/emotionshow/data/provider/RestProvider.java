package com.jude.emotionshow.data.provider;

import com.jude.emotionshow.data.server.HeaderInterceptors;
import com.jude.emotionshow.data.server.WrapperConverter;
import com.jude.emotionshow.domain.api.ServiceAPI;

import retrofit.RestAdapter;
import retrofit.client.OkClient;


final class RestProvider {

    private RestProvider() {
        // singleton
    }

    static RestAdapter provideRetrofit() {
        return RestAdapterHolder.sRetrofit;
    }

    private static class RestAdapterHolder {
        // lazy instantiate
        private static volatile RestAdapter sRetrofit =
                new RestAdapter.Builder()
                        .setEndpoint(ServiceAPI.SERVER_ADDRESS)
                        .setClient(new OkClient(HttpClientProvider.provideHttpClient()))
                        .setLogLevel( RestAdapter.LogLevel.FULL )
                        .setConverter(new WrapperConverter(GsonProvider.provideGson()))
                        .setRequestInterceptor(new HeaderInterceptors())
                        .build();
    }
}