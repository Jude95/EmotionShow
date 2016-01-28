package com.jude.emotionshow.data.model;

import android.content.Context;
import android.support.annotation.Nullable;

import com.jude.beam.model.AbsModel;
import com.jude.emotionshow.data.di.DaggerUserComponent;
import com.jude.emotionshow.data.server.DefaultTransform;
import com.jude.emotionshow.data.server.HeaderInterceptors;
import com.jude.emotionshow.data.server.ServiceResponse;
import com.jude.emotionshow.domain.Dir;
import com.jude.emotionshow.domain.api.ServiceAPI;
import com.jude.emotionshow.domain.entities.Account;
import com.jude.emotionshow.domain.entities.PersonBrief;
import com.jude.emotionshow.domain.entities.PersonDetail;
import com.jude.emotionshow.domain.entities.Seed;
import com.jude.emotionshow.domain.entities.ThirdInfo;
import com.jude.utils.JFileManager;
import com.jude.utils.JUtils;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import rx.Observable;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;

/**
 * Created by Mr.Jude on 2015/11/19.
 * 用户操作部分
 */
public class UserModel extends AbsModel {
    public static final String FILE_ACCOUNT = "Account";

    @Inject ServiceAPI mServiceAPI;
    private Account userAccountData = null;
    //有缓存当前帐号的功能，订阅即收到当前帐号和后续变动
    public BehaviorSubject<Account> userAccountDataBehaviorSubject = BehaviorSubject.create();

    public static UserModel getInstance() {
        return getInstance(UserModel.class);
    }
    @Override
    protected void onAppCreate(Context ctx) {
        super.onAppCreate(ctx);
        //模块注入
        DaggerUserComponent.builder().build().inject(this);
        //帐号是持久化存储了的，服务器不拒绝(返回400)就永不失效。
        setAccount((Account) JFileManager.getInstance().getFolder(Dir.Object).readObjectFromFile(FILE_ACCOUNT));
        //当帐号变动就更新JPush
        getAccountUpdate().subscribe(new Action1<Account>() {
            @Override
            public void call(Account account) {
                //设置别名
                JPushInterface.setAliasAndTags(ctx,
                        (account != null) ? account.getId() + "" : "",
                        null,
                        new TagAliasCallback() {
                            @Override
                            public void gotResult(int i, String s, Set<String> set) {
                                JUtils.Log("Has set Alias");
                            }
                        });
            }
        });
    }
    //返回帐号的Behavior以供订阅
    public Observable<Account> getAccountUpdate(){
        return userAccountDataBehaviorSubject.compose(new DefaultTransform<>());
    }
    //同步取当前帐号，可能为空
    @Nullable
    public Account getCurAccount(){
        return userAccountData;
    }
    //是否登录
    public boolean isLogin(){
        return userAccountData != null;
    }

    //登录
    public Observable<Account> login(String account,String password){
        return mServiceAPI.login(account,password,"0")
                .doOnNext(account1 -> {
                    //登录后应用帐号
                    saveAccount(account1);
                    setAccount(account1);
                })
                .compose(new DefaultTransform<>());
    }

    public Observable<Account> loginByThird(String uid,String avatar,String name,int type){
        return mServiceAPI.loginByThird("0", uid, avatar, name, type)
                .doOnNext(account1 -> {
                    saveAccount(account1);
                    setAccount(account1);
                })
                .compose(new DefaultTransform<>());
    }
    //更新账号信息
    public Observable<Account> updateMyInfo(){
        return mServiceAPI.getMyInfo()
                .onErrorResumeNext(Observable.just(null))
                .doOnNext(account -> {
                    //应用更新的账号信息
                    saveAccount(account);
                    setAccount(account);
                })
                .compose(new DefaultTransform<>());
    }
    //持久化保存帐号
    void saveAccount(Account account){
        if (account == null){
            JFileManager.getInstance().getFolder(Dir.Object).deleteChild(FILE_ACCOUNT);
        }else{
            JFileManager.getInstance().getFolder(Dir.Object).writeObjectToFile(account, FILE_ACCOUNT);
        }
    }
    //应用帐号
    void setAccount(Account account){
        userAccountData = account;
        //发布帐号变动
        userAccountDataBehaviorSubject
                .onNext(account);
        //设置帐号，虽然这里不太优雅，应该由他们订阅帐号变动
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

    //发布空帐号即为登出
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
        return mServiceAPI.modifyInfo(account.getGender(),account.getAddress(),account.getAvatar(),account.getName(),account.getSign(),account.getIntro(),account.getStreet(),account.getRealname(),account.getLovestatus())
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

    public Observable<List<PersonBrief>> searchUser(String text){
        return mServiceAPI.searchUser(text).compose(new DefaultTransform<>());
    }

    public Observable<ThirdInfo> thirdBind(int type,String uid,String name){
        return mServiceAPI.thirdBind(type, uid, name).compose(new DefaultTransform<>());
    }

    public Observable<ThirdInfo> getThirdInfo(){
        return mServiceAPI.getThirdBind().compose(new DefaultTransform<>());
    }

    public Observable<Object> telBind(String tel,String code){
        return mServiceAPI.bindTel(tel, code).compose(new DefaultTransform<>());
    }

    public PersonBrief getUserBrief(String id){
        return mServiceAPI.getUserBrief(id);
    }

    public Observable<List<PersonBrief>> getFriends(){
        return mServiceAPI.getFriends("-1").compose(new DefaultTransform<>());
    }

    public Observable<Object> modBackground(String background){
        return mServiceAPI.modifyBackGround(background).compose(new DefaultTransform<>());
    }

    public Observable<Object> invite(int id){
        return mServiceAPI.invitePerson(id).compose(new DefaultTransform<>());
    }
}
