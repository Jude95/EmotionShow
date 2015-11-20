package com.jude.emotionshow.presentation.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.jude.beam.expansion.data.BeamDataActivityPresenter;
import com.jude.emotionshow.data.model.UserModel;
import com.jude.emotionshow.data.server.ServiceResponse;
import com.jude.emotionshow.domain.entities.Account;
import com.jude.library.imageprovider.ImageProvider;
import com.jude.library.imageprovider.OnImageSelectListener;
import com.jude.utils.JUtils;

/**
 * Created by Mr.Jude on 2015/11/20.
 */
public class UserDetailModifyPresenter extends BeamDataActivityPresenter<UserDetailModifyActivity,Account> {
    Account data;
    private ImageProvider provider;
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
    protected void onCreate(UserDetailModifyActivity view, Bundle savedState) {
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
        UserModel.getInstance().modify(data).subscribe(new ServiceResponse<Object>(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
                getView().finish();
                JUtils.Toast("修改成功");
            }
        });
    }


}
