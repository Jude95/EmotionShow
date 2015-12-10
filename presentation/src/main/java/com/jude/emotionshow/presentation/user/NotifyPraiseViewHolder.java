package com.jude.emotionshow.presentation.user;

import android.content.Intent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.emotionshow.R;
import com.jude.emotionshow.data.model.ImageModel;
import com.jude.emotionshow.domain.entities.Notify;
import com.jude.emotionshow.presentation.seed.SeedDetailActivity;
import com.jude.emotionshow.presentation.widget.CircleTransform;
import com.jude.emotionshow.presentation.widget.RecentDateFormat;
import com.jude.utils.JTimeTransform;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhuchenxi on 15/12/7.
 */
public class NotifyPraiseViewHolder extends BaseViewHolder<Notify> {
    @Bind(R.id.avatar)
    ImageView avatar;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.content)
    TextView content;

    public NotifyPraiseViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_notify);
        ButterKnife.bind(this,itemView);
    }

    @Override
    public void setData(Notify data) {
        Picasso.with(getContext()).load(ImageModel.getSmallImage(data.getFace())).transform(new CircleTransform()).into(avatar);
        name.setText(data.getName());
        time.setText(new JTimeTransform(data.getTime()).toString(new RecentDateFormat()));
        content.setText(data.getContent());
        itemView.setOnClickListener(v->{
            Intent i = new Intent(getContext(), SeedDetailActivity.class);
            i.putExtra("id",data.getData());
            getContext().startActivity(i);
        });
    }
}
