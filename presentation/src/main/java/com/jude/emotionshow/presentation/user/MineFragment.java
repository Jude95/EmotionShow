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
public class MineFragment extends BeamDataFragment<MinePresenter,Account> {
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
        containerUser.setOnClickListener(v->{
            startActivity(new Intent(getActivity(),UserDetailEditActivity.class));
        });
        about.setOnClickListener(v->startActivity(new Intent(getActivity(),AboutActivity.class)));
        return view;
    }

    @Override
    public void setData(Account data) {
        super.setData(data);
        Picasso.with(getContext())
                .load(data.getAvatar())
                .transform(new CircleTransform())
                .into(avatar);
        name.setText(data.getName());
        sign.setText(data.getSign());
        praiseCount.setText(""+data.getPraiseCount());
        seedCount.setText(""+data.getSeedCount());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
