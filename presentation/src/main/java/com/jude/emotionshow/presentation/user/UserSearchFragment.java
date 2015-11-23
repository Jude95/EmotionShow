package com.jude.emotionshow.presentation.user;

import android.view.ViewGroup;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.BeamListFragment;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.emotionshow.domain.entities.PersonBrief;

/**
 * Created by zhuchenxi on 15/11/23.
 */
@RequiresPresenter(UserSearchPresenter.class)
public class UserSearchFragment  extends BeamListFragment<UserSearchPresenter,PersonBrief>{
    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return null;
    }
}
