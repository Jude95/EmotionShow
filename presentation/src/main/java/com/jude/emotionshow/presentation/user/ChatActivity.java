package com.jude.emotionshow.presentation.user;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.emotionshow.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.rong.imkit.fragment.ConversationFragment;

/**
 * Created by zhuchenxi on 15/7/21.
 */
@RequiresPresenter(ChatPresenter.class)
public class ChatActivity extends BeamBaseActivity<ChatPresenter> {
    String id;

    @Bind(R.id.back_img)
    ImageView backImg;
    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        back.setOnClickListener(v->finish());
        id = getIntent().getData().getQueryParameter("targetId");
        title.setText(getIntent().getData().getQueryParameter("title"));
        ConversationFragment fragment = (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.conversation);
        if (fragment != null) fragment.setUri(getIntent().getData());
        else {
            finish();
        }
    }
}
