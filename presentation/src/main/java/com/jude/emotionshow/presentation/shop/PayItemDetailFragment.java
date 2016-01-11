package com.jude.emotionshow.presentation.shop;

import android.view.ViewGroup;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.BeamListFragment;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.emotionshow.domain.entities.Pay;

/**
 * Created by mike on 2015/12/29.
 */
@RequiresPresenter(PayItemDetailPresenter.class)
public class PayItemDetailFragment extends BeamListFragment<PayItemDetailPresenter,Pay>{
    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new PayViewHolder(parent);
    }
}
