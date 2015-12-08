package com.jude.emotionshow.presentation.user;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.emotionshow.R;
import com.jude.emotionshow.data.model.ImageModel;
import com.jude.emotionshow.domain.entities.Notify;
import com.jude.emotionshow.presentation.widget.CircleTransform;
import com.jude.emotionshow.presentation.widget.RecentDateFormat;
import com.jude.utils.JTimeTransform;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhuchenxi on 15/12/7.
 */
public class NotifyViewHolder extends BaseViewHolder<Notify> {
    @Bind(R.id.avatar)
    ImageView avatar;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.content)
    TextView content;

    public NotifyViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_notify);
        ButterKnife.bind(this,itemView);
    }

    @Override
    public void setData(Notify data) {
        Picasso.with(getContext()).load(ImageModel.getSmallImage(data.getFace())).transform(new CircleTransform()).into(avatar);
        name.setText(data.getName());
        time.setText(new JTimeTransform(data.getTime()).toString(new RecentDateFormat()));
    }
}
