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
 * Created by zhuchenxi on 15/12/2.
 */
public class UserRelationViewHolder extends BaseViewHolder<PersonBrief> {
    @Bind(R.id.avatar)
    ImageView avatar;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.sign)
    TextView sign;
    @Bind(R.id.relation)
    ImageView relation;

    public UserRelationViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_user_relation);
        ButterKnife.bind(this,itemView);
    }

    @Override
    public void setData(PersonBrief data) {
        itemView.setOnClickListener(v->{
            Intent i = new Intent(getContext(),UserPreviewActivity.class);
            i.putExtra("id",data.getId());
            getContext().startActivity(i);
        });
        Picasso.with(getContext())
                .load(ImageModel.getSmallImage(data.getAvatar()))
                .transform(new CircleTransform())
                .into(avatar);
        name.setText(data.getName());
        sign.setText(data.getSign());
        switch (data.getType()){
            case 1:
                relation.setImageResource(R.drawable.follow_each);
                break;
            case 2:
                relation.setImageResource(R.drawable.follow_add);
                break;
            case 3:
                relation.setImageResource(R.drawable.follow_done);
                break;
        }
    }
}
