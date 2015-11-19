package com.jude.emotionshow.domain.api;


import com.jude.emotionshow.domain.entities.Account;
import com.jude.emotionshow.domain.entities.Banner;
import com.jude.emotionshow.domain.entities.Category;
import com.jude.emotionshow.domain.entities.PersonDetail;
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
    Observable<List<Category>> getProcess();

    @GET("/home/discover/ctagIndex")
    Observable<List<Category>> getScence();

    @FormUrlEncoded
    @POST("/home/user/login")
    Observable<Account> login(
            @Field("tel") String tel,
            @Field("password") String password,
            @Field("type") String type);

    @FormUrlEncoded
    @POST("/home/user/register")
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
    @POST("/home/user/getUserinfo")
    Observable<PersonDetail> getUserinfo(
            @Field("id") String id);

    @FormUrlEncoded
    @POST("/home/user/feedback")
    Observable<Object> feedback(
            @Field("content") String content);

    @GET("/home/user/myAttend")
    Observable<Object> myAttend();

    @GET("/home/user/getMyinfo")
    Observable<Account> getMyinfo();

    @FormUrlEncoded
    @POST("/home/user/attend")
    Observable<Object> attend(
            @Field("id") String id);
}
