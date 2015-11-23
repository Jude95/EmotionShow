package com.jude.emotionshow.data.model;

import android.content.Context;

import com.jude.beam.model.AbsModel;
import com.jude.emotionshow.data.di.DaggerUserComponent;
import com.jude.emotionshow.data.server.DefaultTransform;
import com.jude.emotionshow.data.server.HeaderInterceptors;
import com.jude.emotionshow.data.server.ServiceResponse;
import com.jude.emotionshow.domain.Dir;
import com.jude.emotionshow.domain.api.ServiceAPI;
import com.jude.emotionshow.domain.entities.Account;
import com.jude.emotionshow.domain.entities.PersonDetail;
import com.jude.emotionshow.domain.entities.Seed;
import com.jude.utils.JFileManager;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by Mr.Jude on 2015/11/19.
 */
public class UserModel extends AbsModel {
    public static final String FILE_ACCOUNT = "Account";

    @Inject ServiceAPI mServiceAPI;
    private Account userAccountData = null;
    public BehaviorSubject<Account> userAccountDataBehaviorSubject = BehaviorSubject.create();

    public static UserModel getInstance() {
        return getInstance(UserModel.class);
    }
    @Override
    protected void onAppCreate(Context ctx) {
        super.onAppCreate(ctx);
        DaggerUserComponent.builder().build().inject(this);
        setAccount((Account) JFileManager.getInstance().getFolder(Dir.Object).readObjectFromFile(FILE_ACCOUNT));
        if (isLogin())
            updateMyInfo().subscribe(new ServiceResponse<Account>() {
                @Override
                public void onServiceError(int status, String info) {
                }
            });
    }

    public Observable<Account> getAccountUpdate(){
        return userAccountDataBehaviorSubject.compose(new DefaultTransform<>());
    }

    public Account getCurAccount(){
        return userAccountData;
    }
    public boolean isLogin(){
        return userAccountData != null;
    }

    public Observable<Account> login(String account,String password){
        return mServiceAPI.login(account,password,"0")
                .doOnNext(account1 -> {
                    saveAccount(account1);
                    setAccount(account1);
                })
                .compose(new DefaultTransform<>());
    }

    public Observable<Account> updateMyInfo(){
        return mServiceAPI.getMyInfo()
                .doOnNext(account -> {
                    saveAccount(account);
                    setAccount(account);
                })
                .compose(new DefaultTransform<>());
    }

    void saveAccount(Account account){
        if (account == null){
            JFileManager.getInstance().getFolder(Dir.Object).deleteChild(FILE_ACCOUNT);
        }else{
            JFileManager.getInstance().getFolder(Dir.Object).writeObjectToFile(account, FILE_ACCOUNT);
        }
    }

    void setAccount(Account account){
        userAccountData = account;
        userAccountDataBehaviorSubject
                .onNext(account);
        if (account!=null){
            ImageModel.UID = account.getId()+"";
            HeaderInterceptors.TOKEN = account.getToken();
            HeaderInterceptors.UID = account.getId()+"";
        }else {
            ImageModel.UID = "";
            HeaderInterceptors.TOKEN = "";
            HeaderInterceptors.UID = "";
        }
    }

    public void logout(){
        saveAccount(null);
        setAccount(null);
    }

    public Observable<Object> checkTel(String tel){
        return mServiceAPI.checkTel(tel).compose(new DefaultTransform<>());
    }

    public Observable<Object> register(String tel,String code,String password){
        return mServiceAPI.register(tel, code, password).compose(new DefaultTransform<>());
    }

    public Observable<Object> modify(Account account){
        return mServiceAPI.modifyInfo(account.getGender(),account.getAddress(),account.getAvatar(),account.getName(),account.getSign(),account.getIntro(),account.getBackground())
                .doOnNext(data -> updateMyInfo().subscribe(new ServiceResponse<>()))
                .compose(new DefaultTransform<>());
    }

    public Observable<Object> changePassword(String originalPassword,String newPassword){
        return mServiceAPI.modPass(originalPassword, newPassword).compose(new DefaultTransform<>());
    }

    public Observable<PersonDetail> getUserDetail(int id){
        return mServiceAPI.getUserDetail(id).compose(new DefaultTransform<>());
    }

    public Observable<Object> follow(int id){
        return mServiceAPI.follow(id).compose(new DefaultTransform<>());
    }

    public Observable<Object> unFollow(int id){
        return mServiceAPI.unFollow(id).compose(new DefaultTransform<>());
    }

    public Observable<List<Seed>> getCollections(){
        return mServiceAPI.getCollections(-1).compose(new DefaultTransform<>());
    }

    public Observable<Object> findPassword(String tel,String code,String password){
        return mServiceAPI.findPassword(tel, code, password).compose(new DefaultTransform<>());
    }
}
