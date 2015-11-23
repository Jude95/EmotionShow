package com.jude.emotionshow.presentation.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.data.BeamDataActivity;
import com.jude.emotionshow.R;
import com.jude.emotionshow.data.model.ImageModel;
import com.jude.emotionshow.data.model.UserModel;
import com.jude.emotionshow.domain.entities.Image;
import com.jude.emotionshow.domain.entities.PersonDetail;
import com.jude.emotionshow.domain.entities.Seed;
import com.jude.emotionshow.presentation.seed.SeedDetailActivity;
import com.jude.emotionshow.presentation.widget.BlurTransformation;
import com.jude.emotionshow.presentation.widget.CircleTransform;
import com.jude.emotionshow.presentation.widget.NetImageAdapter;
import com.jude.exgridview.ExGridView;
import com.jude.utils.JUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Jude on 2015/11/22.
 */
@RequiresPresenter(UserDetailPresenter.class)
public class UserDetailActivity extends BeamDataActivity<UserDetailPresenter, PersonDetail> {

    @Bind(R.id.back_img)
    ImageView backImg;
    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.follow)
    LinearLayout follow;
    @Bind(R.id.follow_text)
    TextView followText;
    @Bind(R.id.background)
    ImageView background;
    @Bind(R.id.avatar)
    ImageView avatar;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.sign)
    TextView sign;
    @Bind(R.id.seed_count)
    TextView seedCount;
    @Bind(R.id.container_seed)
    LinearLayout containerSeed;
    @Bind(R.id.visit_count)
    TextView visitCount;
    @Bind(R.id.container_visit)
    LinearLayout containerVisit;
    @Bind(R.id.praise_count)
    TextView praiseCount;
    @Bind(R.id.container_praise)
    LinearLayout containerPraise;
    @Bind(R.id.pictures)
    ExGridView pictures;

    NetImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);
        back.setOnClickListener(v -> finish());
        follow.setOnClickListener(v -> getPresenter().follow());
        pictures.setAdapter(adapter = new NetImageAdapter(this));

    }

    @Override
    public void setData(PersonDetail data) {
        Picasso.with(this).load(ImageModel.getSmallImage(data.getAvatar())).resize(150, 150).transform(new CircleTransform()).into(avatar);
        Picasso.with(this).load(data.getAvatar()).transform(new BlurTransformation(this, 20)).into(background);
        name.setText(data.getName());
        sign.setText(data.getSign());
        seedCount.setText(data.getSeedCount() + "");
        visitCount.setText(data.getVisitCount() + "");
        praiseCount.setText(data.getPraiseCount() + "");
        JUtils.Log("data.getFollowed" + data.getFollowed());
        followText.setText(data.getFollowed() == 0 ? "关注" : "取消关注");
        if (data.getId() == UserModel.getInstance().getCurAccount().getId()){
            followText.setVisibility(View.INVISIBLE);
        }
    }

    public void setSeedList(List<Seed> data){
        adapter.setListener(position -> {
            Intent i = new Intent(this, SeedDetailActivity.class);
            i.putExtra("id",data.get(position).getId());
            startActivity(i);
        });
        adapter.clear();
        List<Image> list = new ArrayList<>();
        for (Seed seed : data) {
            list.add(seed.getPics().get(0));
        }
        adapter.addAll(list);

    }
}
