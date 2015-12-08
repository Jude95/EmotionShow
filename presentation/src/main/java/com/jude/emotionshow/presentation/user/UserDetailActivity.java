package com.jude.emotionshow.presentation.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.data.BeamDataActivity;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.emotionshow.R;
import com.jude.emotionshow.data.model.ImageModel;
import com.jude.emotionshow.data.model.UserModel;
import com.jude.emotionshow.domain.entities.PersonDetail;
import com.jude.emotionshow.domain.entities.SeedDetail;
import com.jude.emotionshow.presentation.seed.SeedCalendarViewHolder;
import com.jude.emotionshow.presentation.seed.SeedViewHolder;
import com.jude.emotionshow.presentation.widget.BlurTransformation;
import com.jude.emotionshow.presentation.widget.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Jude on 2015/11/22.
 */
@RequiresPresenter(UserDetailPresenter.class)
public class UserDetailActivity extends BeamDataActivity<UserDetailPresenter, PersonDetail> {
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
    @Bind(R.id.back_img)
    ImageView backImg;
    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.follow)
    LinearLayout follow;
    @Bind(R.id.recycler)
    RecyclerView recycler;
    @Bind(R.id.sort)
    ImageView sort;

    private static int style = 0;
    @Bind(R.id.follow_image)
    ImageView followImage;
    @Bind(R.id.chat_image)
    ImageView chatImage;
    @Bind(R.id.chat)
    LinearLayout chat;
    private SeedCalendarAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        RecyclerViewHeader header = RecyclerViewHeader.fromXml(this, R.layout.head_user);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setLayoutManager(style == 0 ? new LinearLayoutManager(this) : new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        header.attachTo(recycler);
        header.setOnClickListener(v->{
            Intent i = new Intent(this, UserPreviewActivity.class);
            i.putExtra("id", getPresenter().data.getId());
            startActivity(i);
        });
        ButterKnife.bind(this);
        recycler.setAdapter(adapter = new SeedCalendarAdapter(this));

        back.setOnClickListener(v -> finish());
        follow.setOnClickListener(v -> getPresenter().follow());
        sort.setOnClickListener(v -> {
            if (style == 0) {
                style = 1;
            } else {
                style = 0;
            }
            recreate();
        });
        chat.setOnClickListener(v->getPresenter().chat());
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

        followImage.setImageResource((getPresenter().data.getFollowed() == 0||getPresenter().data.getFollowed() ==2) ? R.drawable.follow_add:R.drawable.follow_done);
        if (UserModel.getInstance().isLogin()&&getPresenter().data.getId() != UserModel.getInstance().getCurAccount().getId()) {
            chat.setVisibility(View.VISIBLE);
            follow.setVisibility(View.VISIBLE);
        }
    }

    public void addSeed(List<SeedDetail> data) {
        adapter.addAll(data);
    }

    private class SeedCalendarAdapter extends RecyclerArrayAdapter<SeedDetail> {

        public SeedCalendarAdapter(Context context) {
            super(context);
        }

        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            if (style == 0) return new SeedCalendarViewHolder(parent);
            return new SeedViewHolder(parent);
        }
    }


}
