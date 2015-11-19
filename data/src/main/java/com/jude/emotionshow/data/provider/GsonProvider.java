package com.jude.emotionshow.data.provider;

import com.google.gson.Gson;

/**
 * Created by Mr.Jude on 2015/11/18.
 */
final class GsonProvider {
    private GsonProvider() {
        // singleton
    }
    static Gson provideGson() {
        return GsonHolder.sGson;
    }

    private static class GsonHolder {
        // lazy instantiate
        private static volatile Gson sGson;

        static {
            sGson = new Gson();
            //sOkHttpClient.networkInterceptors().add(new StethoInterceptor());
        }
    }
}
