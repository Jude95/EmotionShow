package com.jude.emotionshow.presentation.user;

import android.content.Intent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.emotionshow.R;
import com.jude.emotionshow.data.model.ImageModel;
import com.jude.emotionshow.domain.entities.PersonBrief;
import com.jude.emotionshow.presentation.widget.CircleTransform;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhuchenxi on 15/11/23.
 */
public class UserViewHolder extends BaseViewHolder<PersonBrief> {
    @Bind(R.id.avatar)
    ImageView avatar;
    @Bind(R.id.name)
    TextView name;

    private int id;

    public UserViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_user);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(v->{
            Intent i = new Intent(getContext(),UserDetailActivity.class);
            i.putExtra("id",id);
            getContext().startActivity(i);
        });
    }

    @Override
    public void setData(PersonBrief data) {
        id = data.getId();
        Picasso.with(getContext())
                .load(ImageModel.getSmallImage(data.getAvatar()))
                .transform(new CircleTransform())
                .into(avatar);
        name.setText(data.getName());
    }
}
