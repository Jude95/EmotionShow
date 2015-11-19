package com.jude.emotionshow.presentation.user;

import android.content.Intent;

import com.jude.beam.bijection.Presenter;
import com.jude.emotionshow.data.model.UserModel;
import com.jude.emotionshow.data.server.ServiceResponse;
import com.jude.emotionshow.domain.entities.Account;
import com.jude.emotionshow.presentation.main.MainActivity;

/**
 * Created by Mr.Jude on 2015/11/19.
 */
public class LoginPresenter extends Presenter<LoginActivity> {


    public void login(String account,String password){
        UserModel.getInstance().login(account,password).subscribe(new ServiceResponse<Account>(){
            @Override
            public void onNext(Account account) {
                getView().finish();
                getView().startActivity(new Intent(getView(), MainActivity.class));
            }
        });
    }
}
