package com.jude.emotionshow.presentation.seed;

import android.net.Uri;
import android.os.Bundle;

import com.jude.beam.expansion.data.BeamDataActivityPresenter;
import com.jude.emotionshow.domain.entities.SeedDetail;
import com.jude.exgridview.PieceViewGroup;
import com.jude.library.imageprovider.ImageProvider;
import com.jude.library.imageprovider.OnImageSelectListener;
import com.jude.utils.JUtils;

import java.util.ArrayList;

/**
 * Created by Mr.Jude on 2015/11/21.
 */
public class WritingPresenter extends BeamDataActivityPresenter<WritingActivity,SeedDetail> implements  PieceViewGroup.OnViewDeleteListener{
    private ImageProvider provider;
    private ArrayList<Uri> uriArrayList = new ArrayList<>();

    SeedDetail data;
    OnImageSelectListener listener = new OnImageSelectListener() {

        @Override
        public void onImageSelect() {
            getView().getExpansion().showProgressDialog("加载中");
        }

        @Override
        public void onImageLoaded(Uri uri) {
            getView().getExpansion().dismissProgressDialog();
            //getView().addImage(ImageProvider.readImageWithSize(uri, 300, 300));
            uriArrayList.add(uri);
        }

        @Override
        public void onError() {

        }
    };

    public void editFace(){
        if (uriArrayList.size()>=9){
            JUtils.Toast("最多上传9张图片");
            return;
        }
        provider.getImageFromCameraOrAlbum(listener);
    }

    @Override
    protected void onCreate(WritingActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        data = new SeedDetail();
        provider = new ImageProvider(getView());
    }

    @Override
    public void onViewDelete(int index) {
        uriArrayList.remove(i);
    }
}
