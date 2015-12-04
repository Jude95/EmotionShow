package com.jude.emotionshow.presentation.user;

import android.os.Bundle;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.emotionshow.R;

import io.rong.imkit.fragment.ConversationListFragment;

/**
 * Created by zhuchenxi on 15/7/21.
 */
@RequiresPresenter(ChatListPresenter.class)
public class ChatListActivity extends BeamBaseActivity<ChatListPresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatlist);
        ConversationListFragment fragment =  (ConversationListFragment)getSupportFragmentManager().findFragmentById(R.id.conversation);
        if (fragment !=null) fragment.setUri(getIntent().getData());
        else{
            finish();
        }
    }
}
