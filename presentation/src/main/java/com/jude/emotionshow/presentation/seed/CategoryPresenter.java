package com.jude.emotionshow.presentation.seed;

import android.os.Bundle;

import com.jude.beam.expansion.data.BeamDataActivityPresenter;
import com.jude.emotionshow.data.model.SeedModel;
import com.jude.emotionshow.data.server.ServiceResponse;
import com.jude.emotionshow.domain.entities.Category;
import com.jude.emotionshow.domain.entities.CategoryDetail;
import com.jude.emotionshow.domain.entities.Seed;
import com.jude.utils.JUtils;

import java.util.List;

/**
 * Created by Mr.Jude on 2015/11/22.
 */
public class CategoryPresenter extends BeamDataActivityPresenter<CategoryActivity,CategoryDetail> {
    CategoryDetail data;
    int page = 0;

    @Override
    protected void onCreate(CategoryActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        Category category = (Category) getView().getIntent().getSerializableExtra("category");
        //加载Header的信息
        SeedModel.getInstance().getCategoryDetail(category.getId()+"",category.getType()+"")
                .doOnNext(categoryDetail -> data = categoryDetail)
                .finallyDo(this::loadMore)
                .subscribe(this);
    }

    //加载item的数据
    void loadMore(){
        SeedModel.getInstance().getCategorySeedList(data.getId(),page,data.getType()).subscribe(new ServiceResponse<List<Seed>>(){
            @Override
            public void onNext(List<Seed> seeds) {
                getView().addSeed(seeds);
                page++;
                JUtils.Toast("第" + page + "页");
            }
        });
    }
}
