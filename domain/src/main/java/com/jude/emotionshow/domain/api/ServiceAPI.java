package com.jude.emotionshow.domain.api;


import com.jude.emotionshow.domain.entities.Account;
import com.jude.emotionshow.domain.entities.Banner;
import com.jude.emotionshow.domain.entities.Category;
import com.jude.emotionshow.domain.entities.CategoryDetail;
import com.jude.emotionshow.domain.entities.CategoryPreview;
import com.jude.emotionshow.domain.entities.Notify;
import com.jude.emotionshow.domain.entities.PersonBrief;
import com.jude.emotionshow.domain.entities.PersonDetail;
import com.jude.emotionshow.domain.entities.PushSet;
import com.jude.emotionshow.domain.entities.Seed;
import com.jude.emotionshow.domain.entities.SeedDetail;
import com.jude.emotionshow.domain.entities.ThirdInfo;
import com.jude.emotionshow.domain.entities.Token;
import com.jude.emotionshow.domain.entities.Topic;

import java.util.List;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by Mr.Jude on 2015/11/18.
 */
public interface ServiceAPI {
    String SERVER_ADDRESS = "http://114.215.86.90/meme.php";

    @GET("/home/discover/getBanner")
    Observable<List<Banner>> getBanner();

    @GET("/home/discover/index")
    Observable<List<Topic>> getTopic();

    @GET("/home/discover/ltagIndex")
    Observable<List<CategoryPreview>> getProcess();

    @GET("/home/discover/ctagIndex")
    Observable<List<CategoryPreview>> getScene();

    @FormUrlEncoded
    @POST("/home/user/login")
    Observable<Account> login(
            @Field("tel") String tel,
            @Field("password") String password,
            @Field("type") String type);

    @FormUrlEncoded
    @POST("/home/user/registerForA")
    Observable<Object> register(
            @Field("tel") String tel,
            @Field("code") String code,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("/home/user/checkTel")
    Observable<Object> checkTel(
            @Field("tel") String tel);

    @FormUrlEncoded
    @POST("/home/user/bindTel")
    Observable<Object> bindTel(
            @Field("tel") String tel,
            @Field("code") String code);

    @FormUrlEncoded
    @POST("/home/user/modPass")
    Observable<Object> modPass(
            @Field("oldpwd") String oldPwd,
            @Field("newpwd") String newPwd);

    @FormUrlEncoded
    @POST("/home/user/modIntro")
    Observable<Object> modIntro(
            @Field("intro") String intro);

    @FormUrlEncoded
    @POST("/home/user/getUserDetail")
    Observable<PersonDetail> getUserDetail(
            @Field("id") String id);

    @FormUrlEncoded
    @POST("/home/index/feedback")
    Observable<Object> feedback(
            @Field("content") String content);

    @GET("/home/user/myAttend")
    Observable<Object> myAttend();

    @GET("/home/user/getMyinfo")
    Observable<Account> getMyInfo();

    @FormUrlEncoded
    @POST("/home/user/modInfo")
    Observable<Object> modifyInfo(
            @Field("gender") int gender,
            @Field("address") String address,
            @Field("face") String face,
            @Field("name") String name,
            @Field("sign") String sign,
            @Field("intro") String intro
    );

    @FormUrlEncoded
    @POST("/home/user/modInfo")
    Observable<Object> modifyBackGround(
            @Field("bg") String bg
    );


    @FormUrlEncoded
    @POST("/home/user/attend")
    Observable<Object> follow(
            @Field("id") int id);

    @FormUrlEncoded
    @POST("/home/user/unAttend")
    Observable<Object> unFollow(
            @Field("id") int id);

    @FormUrlEncoded
    @POST("/home/history/add")
    Observable<Object> addSeed(
            @Field("content") String content,
            @Field("ctag") int scene,
            @Field("ltag") int process,
            @Field("address") String address,
            @Field("rights") int scope,
            @Field("tag") String tag,
            @Field("pics") String pics
            );

    @GET("/home/index/qiniuToken")
    Observable<Token> qiniuToken();

    @FormUrlEncoded
    @POST("/home/user/address")
    Observable<Object> uploadAddress(
            @Field("lat") double lat,
            @Field("lng") double lng);

    @FormUrlEncoded
    @POST("/home/history/item")
    Observable<SeedDetail> getSeedDetail(
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST("/home/history/comment")
    Observable<Object> comment(
            @Field("hid") int seedId,
            @Field("fid") int commentId,
            @Field("content") String content
    );

    @FormUrlEncoded
    @POST("/home/history/zan")
    Observable<SeedDetail> praiseSeed(
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST("/home/history/collect")
    Observable<Object> collect(
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST("/home/index/jubao")
    Observable<Object> report(
            @Field("hid") int hid
    );

    @FormUrlEncoded
    @POST("/home/user/getUserinfo")
    Observable<PersonDetail> getUserDetail(
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST("/home/history/homepage")
    Observable<List<SeedDetail>> getUserSeedList(
            @Field("page") int page,
            @Field("id") int id,
            @Field("first") int first
    );

    @FormUrlEncoded
    @POST("/home/history/myCollect")
    Observable<List<Seed>> getCollections(
            @Field("page") int page
    );


    @FormUrlEncoded
    @POST("/home/discover/getTag")
    Observable<CategoryDetail> getCategoryDetail(
            @Field("id") String id,
            @Field("type") String type
    );

    @FormUrlEncoded
    @POST("/home/discover/getByLtag")
    Observable<List<Seed>> getProcessSeedList(
            @Field("page") int page,
            @Field("ltag") String process
    );

    @FormUrlEncoded
    @POST("/home/discover/getByCtag")
    Observable<List<Seed>> getSceneSeedList(
            @Field("page") int page,
            @Field("ctag") String process
    );
    @FormUrlEncoded
    @POST("/home/discover/getHistoryList")
    Observable<List<Seed>> getRecommendSeedList(
            @Field("type") int type,
            @Field("page") int page
    );

    @FormUrlEncoded
    @POST("/home/user/resetPass")
    Observable<Object> findPassword(
            @Field("tel") String tel,
            @Field("code") String code,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("/home/history/search")
    Observable<List<Seed>> searchSeed(
            @Field("key") String key);

    @FormUrlEncoded
    @POST("/home/user/search")
    Observable<List<PersonBrief>> searchUser(
            @Field("key") String key);

    @FormUrlEncoded
    @POST("/home/user/thirdLogin")
    Observable<Account> loginByThird(
            @Field("type") String type,
            @Field("uid") String uid,
            @Field("face") String face,
            @Field("name") String name,
            @Field("authType") int authType);

    @FormUrlEncoded
    @POST("/home/user/bindThird")
    Observable<ThirdInfo> thirdBind(
            @Field("type") int type,
            @Field("uid") String uid,
            @Field("name") String name);

    @GET("/home/user/getBindInfo")
    Observable<ThirdInfo> getThirdBind();

    @FormUrlEncoded
    @POST("/home/user/getBriefInfo")
    PersonBrief getUserBrief(
            @Field("id") String id);

    @FormUrlEncoded
    @POST("/home/user/myFriend")
    Observable<List<PersonBrief>> getFriends(
            @Field("page") String page);


    @GET("/home/discover/activity")
    Observable<CategoryPreview> getActivityList();

    @FormUrlEncoded
    @POST("/home/user/getNotify")
    Observable<List<Notify>> getNotify(
            @Field("page") int page,
            @Field("type") String type,
            @Field("size") int size);

    @FormUrlEncoded
    @POST("/home/user/invite")
    Observable<List<PersonBrief>> invitePerson(
            @Field("id") int id);


    @GET("/home/history/ctagList")
    Observable<List<Category>> getCategoryList();


    @GET("/home/user/getPushset")
    Observable<PushSet> getPushSet();

    @FormUrlEncoded
    @POST("/home/user/pushSet")
    Observable<List<PersonBrief>> uploadPushSet(
            @Field("zan") int zan,
            @Field("comment") int comment,
            @Field("care") int care,
            @Field("community") int community);
}
