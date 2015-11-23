package com.jude.emotionshow.presentation.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.jude.beam.expansion.data.BeamDataActivityPresenter;
import com.jude.emotionshow.data.model.ImageModel;
import com.jude.emotionshow.data.model.RegionModel;
import com.jude.emotionshow.data.model.UserModel;
import com.jude.emotionshow.data.server.ServiceResponse;
import com.jude.emotionshow.domain.entities.Account;
import com.jude.emotionshow.domain.entities.Region;
import com.jude.library.imageprovider.ImageProvider;
import com.jude.library.imageprovider.OnImageSelectListener;
import com.jude.utils.JUtils;

import java.io.File;

/**
 * Created by Mr.Jude on 2015/11/20.
 */
public class UserDetailEditPresenter extends BeamDataActivityPresenter<UserDetailEditActivity,Account> {
    Account data;
    private ImageProvider provider;
    Uri face;
    OnImageSelectListener listener = new OnImageSelectListener() {

        @Override
        public void onImageSelect() {
            getView().getExpansion().showProgressDialog("加载中");
        }

        @Override
        public void onImageLoaded(Uri uri) {
            getView().getExpansion().dismissProgressDialog();
            provider.corpImage(uri, 200, 200, new OnImageSelectListener() {
                @Override
                public void onImageSelect() {

                }

                @Override
                public void onImageLoaded(Uri uri) {
                    face = uri;
                    getView().setAvatar(uri);
                }

                @Override
                public void onError() {

                }
            });
        }

        @Override
        public void onError() {

        }
    };


    @Override
    protected void onCreate(UserDetailEditActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        provider = new ImageProvider(getView());
        UserModel.getInstance().getAccountUpdate()
                .doOnNext(account->data=account.clone())
                .subscribe(this);
    }

    public void editFace(int style) {
        switch (style) {
            case 0:
                provider.getImageFromCamera(listener);
                break;
            case 1:
                provider.getImageFromAlbum(listener);
                break;
        }
    }

    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        provider.onActivityResult(requestCode, resultCode, data);
    }

    public void submit(){
        getView().getExpansion().showProgressDialog("提交中");
        ImageModel.getInstance().putImageSync(getView(),new File(face.getPath()))
                .doOnError(throwable -> JUtils.Toast("上传失败"))
                .doOnNext(image -> data.setAvatar(image.getUrl()))
                .flatMap(image -> UserModel.getInstance().modify(data))
                .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                .subscribe(new ServiceResponse<Object>() {
                    @Override
                    public void onNext(Object o) {
                        getView().finish();
                        JUtils.Toast("修改成功");
                    }
                });
    }


    public void finishAddCity(Region region) {
        String province =RegionModel.getInstance().findProvince(region.getCid()).getName();
        String city=RegionModel.getInstance().findCity(region.getCid()).getName();
        String district =RegionModel.getInstance().findRegion(region.getCid()).getName();
        String address = province;
        if (!province.equals(city)){
            address+=city;
        }
        address+=district;
        data.setAddress(address);
        publishObject(data);
    }
}
