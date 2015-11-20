package com.jude.emotionshow.presentation.user;

import com.jude.beam.bijection.Presenter;
import com.jude.emotionshow.data.model.UserModel;
import com.jude.emotionshow.data.server.ServiceResponse;
import com.jude.utils.JUtils;

/**
 * Created by Mr.Jude on 2015/11/20.
 */
public class PasswordEditPresenter extends Presenter<PasswordEditActivity> {

    public void edit(String originalPassword,String newPassword){
        getView().getExpansion().showProgressDialog("提交中");
        UserModel.getInstance().changePassword(originalPassword,newPassword)
                .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                .subscribe(new ServiceResponse<Object>() {
                    @Override
                    public void onNext(Object o) {
                        getView().finish();
                        JUtils.Toast("修改成功");
                    }
                });
    }
}
