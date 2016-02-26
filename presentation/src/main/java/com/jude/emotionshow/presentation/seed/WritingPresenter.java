package com.jude.emotionshow.presentation.seed;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.jude.beam.expansion.data.BeamDataActivityPresenter;
import com.jude.emotionshow.data.model.ImageModel;
import com.jude.emotionshow.data.model.LocationModel;
import com.jude.emotionshow.data.model.SeedModel;
import com.jude.emotionshow.data.server.ServiceResponse;
import com.jude.emotionshow.domain.entities.Category;
import com.jude.emotionshow.domain.entities.SeedEditable;
import com.jude.exgridview.PieceViewGroup;
import com.jude.library.imageprovider.ImageProvider;
import com.jude.library.imageprovider.OnImageSelectListener;
import com.jude.utils.JUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.Jude on 2015/11/21.
 */
public class WritingPresenter extends BeamDataActivityPresenter<WritingActivity,SeedEditable> implements  PieceViewGroup.OnViewDeleteListener{
    private ImageProvider provider;
    public ArrayList<Uri> uriArrayList = new ArrayList<>();

    public List<Category> categoryList;

    public static final int EXTERNAL_STORAGE_REQ_CODE = 10 ;

    SeedEditable data;
    OnImageSelectListener listener = new OnImageSelectListener() {

        @Override
        public void onImageSelect() {
            getView().getExpansion().showProgressDialog("加载中");
        }

        @Override
        public void onImageLoaded(Uri uri) {
            getView().getExpansion().dismissProgressDialog();
            getView().addImage(ImageProvider.readImageWithSize(uri, 300, 300));
            uriArrayList.add(uri);
        }

        @Override
        public void onError() {

        }
    };

    public void getCategoryList(){
        SeedModel.getInstance().getCategoryList().subscribe(new ServiceResponse<List<Category>>(){
            @Override
            public void onNext(List<Category> categories) {
                categoryList = categories;
            }
        });
    }

    public void editPicture(){
        if (!getView().requestPermission()){
            return;
        }
        if (uriArrayList.size()>=9){
            JUtils.Toast("最多上传9张图片");
            return;
        }
        provider.getImageFromCameraOrAlbum(listener,9-uriArrayList.size());
    }

    @Override
    protected void onCreate(WritingActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        data = new SeedEditable();
        data.setScene(0);
        data.setProcess(0);
        data.setAddress(LocationModel.getInstance().getCurLocation().getAddress());
        provider = new ImageProvider(getView());
        editPicture();
        getCategoryList();
    }

    public void publish(){
        if (uriArrayList.size()==0){
            JUtils.Toast("请先选择图片");
            return;
        }
        if (data.getScene()==-1){
            JUtils.Toast("请选择场景");
            return;
        }
        if (data.getProcess()<0||data.getProcess()>=getView().PROCESS.length){
            JUtils.Toast("请选择情感");
            return;
        }

        getView().getExpansion().showProgressDialog("上传中");
        File[] files = new File[uriArrayList.size()];
        for (int i = 0; i < files.length; i++) {
            files[i] = new File(uriArrayList.get(i).getPath());
            JUtils.Log("File:"+(files[i]==null));
        }
        data.setPictures(new ArrayList<>());
        ImageModel.getInstance().putImageSync(getView(),files)
                .doOnNext(s -> data.getPictures().add(s))
                .toList()
                .flatMap(images -> SeedModel.getInstance().publishSeed(data))
                .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                .subscribe(new ServiceResponse<Object>() {
                    @Override
                    public void onNext(Object o) {
                        getView().finish();
                        JUtils.Toast("上传成功");
                    }
                })
        ;
    }

    @Override
    public void onViewDelete(int index) {
        uriArrayList.remove(index);
    }
    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        provider.onActivityResult(requestCode, resultCode, data);
    }

}
