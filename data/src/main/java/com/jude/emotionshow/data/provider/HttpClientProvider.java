package com.jude.emotionshow.data.provider;

import com.squareup.okhttp.OkHttpClient;

/**
 * Created by Mr.Jude on 2015/11/18.
 */
final class HttpClientProvider {
    private HttpClientProvider(){}

    static OkHttpClient provideHttpClient() {
        return OkHttpClientHolder.sOkHttpClient;
    }

    private static class OkHttpClientHolder {
        // lazy instantiate
        private static volatile OkHttpClient sOkHttpClient;

        static {
            sOkHttpClient = new OkHttpClient();
            //sOkHttpClient.networkInterceptors().add(new StethoInterceptor());
        }
    }
}
