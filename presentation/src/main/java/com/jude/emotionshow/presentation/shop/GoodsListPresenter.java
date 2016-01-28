package com.jude.emotionshow.presentation.shop;

import android.os.Bundle;

import com.jude.beam.expansion.list.BeamListFragmentPresenter;
import com.jude.emotionshow.data.model.ShopModel;
import com.jude.emotionshow.domain.entities.Goods;

/**
 * Created by mike on 2015/12/23.
 */
public class GoodsListPresenter extends BeamListFragmentPresenter<GoodsListFragment, Goods> {
    private int type;
    private int sort = 1;//0升序 1降序

    @Override
    protected void onCreate(GoodsListFragment view, Bundle savedState) {
        super.onCreate(view, savedState);
        type = getView().getArguments().getInt("type");
    }

    @Override
    protected void onCreateView(GoodsListFragment view) {
        super.onCreateView(view);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        ShopModel.getInstance().getGoodsList(type, 0, sort).unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        ShopModel.getInstance().getGoodsList(type, getCurPage(), sort).unsafeSubscribe(getMoreSubscriber());
    }

    public void sortByAscOrDesc() {
        if (sort==0){
            sort = 1;
            onRefresh();
        }else {
            sort = 0;
            onRefresh();
        }
    }

    public void sortByDesc() {
        sort = 1;
        onRefresh();
    }

    public void sortByAsc() {
        sort = 0;
        onRefresh();
    }
}
