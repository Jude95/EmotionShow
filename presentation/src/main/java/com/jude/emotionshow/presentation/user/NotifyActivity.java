package com.jude.emotionshow.presentation.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.emotionshow.R;
import com.jude.emotionshow.presentation.setting.PushSettingActivity;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhuchenxi on 15/12/4.
 */
@RequiresPresenter(NotifyPresenter.class)
public class NotifyActivity extends BeamBaseActivity<NotifyPresenter> {
    @Bind(R.id.back_img)
    ImageView backImg;
    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.done)
    LinearLayout done;
    @Bind(R.id.praise)
    LinearLayout praise;
    @Bind(R.id.comment)
    LinearLayout comment;
    @Bind(R.id.follow)
    LinearLayout follow;
    @Bind(R.id.invite)
    LinearLayout invite;

    private static final HashMap<Integer,String> mTypeMap = new HashMap<>();
    {
        mTypeMap.put(1,"赞");
        mTypeMap.put(2,"评论");
        mTypeMap.put(3,"关注");
        mTypeMap.put(4,"私信");
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(NotifyActivity.this,NotifyItemActivity.class);
            i.putExtra("type",(String) v.getTag());
            i.putExtra("title",mTypeMap.get(Integer.parseInt((String)v.getTag())));
            startActivity(i);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        ButterKnife.bind(this);
        back.setOnClickListener(v->finish());
        done.setOnClickListener(v->startActivity(new Intent(this, PushSettingActivity.class)));
        praise.setOnClickListener(listener);
        comment.setOnClickListener(listener);
        follow.setOnClickListener(listener);
        invite.setOnClickListener(listener);
    }
}
