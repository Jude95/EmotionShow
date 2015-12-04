package com.jude.emotionshow.presentation.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.data.BeamDataFragment;
import com.jude.emotionshow.R;
import com.jude.emotionshow.data.model.ImageModel;
import com.jude.emotionshow.data.model.RongYunModel;
import com.jude.emotionshow.domain.entities.Account;
import com.jude.emotionshow.presentation.setting.AboutActivity;
import com.jude.emotionshow.presentation.setting.SettingActivity;
import com.jude.emotionshow.presentation.widget.CircleTransform;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Jude on 2015/11/18.
 */
@RequiresPresenter(MinePresenter.class)
public class MineFragment extends BeamDataFragment<MinePresenter, Account> {
    private static MineFragment instance;
    @Bind(R.id.avatar)
    ImageView avatar;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.sign)
    TextView sign;
    @Bind(R.id.head_arrows)
    ImageView headArrows;
    @Bind(R.id.container_user)
    RelativeLayout containerUser;
    @Bind(R.id.seed_count)
    TextView seedCount;
    @Bind(R.id.container_seed)
    LinearLayout containerSeed;
    @Bind(R.id.praise_count)
    TextView praiseCount;
    @Bind(R.id.container_praise)
    LinearLayout containerPraise;
    @Bind(R.id.collect)
    LinearLayout collect;
    @Bind(R.id.seed)
    LinearLayout seed;
    @Bind(R.id.setting)
    LinearLayout setting;
    @Bind(R.id.about)
    LinearLayout about;
    @Bind(R.id.friends)
    LinearLayout friends;
    @Bind(R.id.score_count)
    TextView scoreCount;
    @Bind(R.id.container_score)
    LinearLayout containerScore;
    @Bind(R.id.chats)
    LinearLayout chats;
    @Bind(R.id.message)
    LinearLayout message;

    public static MineFragment getInstance() {
        if (instance == null) instance = new MineFragment();
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
        setting.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), SettingActivity.class));
        });
        containerUser.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), UserDetailEditActivity.class));
        });
        about.setOnClickListener(v -> startActivity(new Intent(getActivity(), AboutActivity.class)));
        collect.setOnClickListener(v -> startActivity(new Intent(getActivity(), CollectionActivity.class)));
        friends.setOnClickListener(v -> startActivity(new Intent(getActivity(), FriendsActivity.class)));
        chats.setOnClickListener(v-> RongYunModel.getInstance().chatList(getActivity()));
        return view;
    }

    @Override
    public void setData(Account data) {
        super.setData(data);
        if (data == null) {
            avatar.setImageBitmap(null);
            name.setText("");
            sign.setText("");
            seedCount.setText("0");
            praiseCount.setText("0");
            seed.setOnClickListener(null);
        } else {
            Picasso.with(getContext())
                    .load(ImageModel.getSmallImage(data.getAvatar()))
                    .transform(new CircleTransform())
                    .into(avatar);
            name.setText(data.getName());
            sign.setText(data.getSign());
            praiseCount.setText("" + data.getPraiseCount());
            seedCount.setText("" + data.getSeedCount());
            seed.setOnClickListener(v -> {
                Intent i = new Intent(getContext(), UserDetailActivity.class);
                i.putExtra("id", data.getId());
                startActivity(i);
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
