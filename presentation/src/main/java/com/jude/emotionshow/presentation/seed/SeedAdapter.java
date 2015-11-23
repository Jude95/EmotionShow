package com.jude.emotionshow.presentation.seed;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.emotionshow.domain.entities.Seed;

/**
 * Created by Mr.Jude on 2015/11/22.
 */
public class SeedAdapter  extends RecyclerArrayAdapter<Seed>{
    public SeedAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new SeedViewHolder(parent);
    }
}
