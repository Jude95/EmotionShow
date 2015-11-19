package com.jude.emotionshow.data.server;

import android.content.Context;

import com.jude.emotionshow.domain.api.Code;
import com.jude.utils.JActivityManager;
import com.jude.utils.JUtils;

import rx.Observer;

/**
 * Created by Mr.Jude on 2015/8/24.
 * 服务器返回的回调
 */
public class ServiceResponse<T> implements Observer<T> {

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        if (e.getCause() instanceof ServiceException){
            JUtils.Log("Server Error:" + e.getLocalizedMessage());
            onServiceError(((ServiceException) e.getCause()).getStatus(), ((ServiceException) e.getCause()).getInfo());
        }else{
            JUtils.Log("UnKnow Error:" + e.getLocalizedMessage());
            onServiceError(Code.NetInvalid, "网络错误");
        }
    }

    public void onServiceError(int status,String info){
        if(status == Code.UserInvalid){
            Context ctx = JActivityManager.getInstance().currentActivity();
            //ctx.startActivity(new Intent(ctx, LoginActivity.class));
            return;
        }
        JUtils.Toast(info);
    }

}
