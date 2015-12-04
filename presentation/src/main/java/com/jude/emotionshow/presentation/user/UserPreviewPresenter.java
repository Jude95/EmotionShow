package com.jude.emotionshow.presentation.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.jude.beam.expansion.data.BeamDataActivityPresenter;
import com.jude.emotionshow.data.model.ImageModel;
import com.jude.emotionshow.data.model.UserModel;
import com.jude.emotionshow.data.server.DefaultTransform;
import com.jude.emotionshow.data.server.ServiceResponse;
import com.jude.emotionshow.domain.entities.PersonDetail;
import com.jude.library.imageprovider.ImageProvider;
import com.jude.library.imageprovider.OnImageSelectListener;

import java.io.File;

/**
 * Created by Mr.Jude on 2015/11/22.
 */
public class UserPreviewPresenter extends BeamDataActivityPresenter<UserPreviewActivity,PersonDetail> {
    public int id;
    private ImageProvider provider;

    @Override
    protected void onCreate(UserPreviewActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        provider = new ImageProvider(getView());
        id = getView().getIntent().getIntExtra("id",0);
        UserModel.getInstance().getUserDetail(id).subscribe(this);
    }

    public void selectImage(){
        provider.getImageFromCameraOrAlbum(new OnImageSelectListener() {
            @Override
            public void onImageSelect() {
                getView().getExpansion().showProgressDialog("上传中");
            }

            @Override
            public void onImageLoaded(Uri uri) {
                getView().setBackground(uri);
                ImageModel.getInstance().putImageSync(getView(),new File(uri.getPath()))
                        .flatMap(image -> UserModel.getInstance().modBackground(image.getUrl()))
                        .compose(new DefaultTransform<>())
                        .finallyDo(()->getView().getExpansion().dismissProgressDialog())
                        .subscribe(new ServiceResponse<Object>());
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        provider.onActivityResult(requestCode, resultCode, data);
    }
}
