package com.jude.emotionshow.presentation.setting;

import com.jude.beam.bijection.Presenter;
import com.jude.emotionshow.data.model.CommonModel;
import com.jude.emotionshow.data.server.ServiceResponse;
import com.jude.utils.JUtils;

/**
 * Created by Mr.Jude on 2015/11/20.
 */
public class FeedBackPresenter extends Presenter<FeedbackActivity> {

    public void feedBack(String content){
        CommonModel.getInstance().feedBack(content).subscribe(new ServiceResponse<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().finish();
                JUtils.Toast("感谢您的意见");
            }
        });
    }
}
