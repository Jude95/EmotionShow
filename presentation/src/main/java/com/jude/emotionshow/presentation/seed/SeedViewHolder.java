package com.jude.emotionshow.presentation.seed;

import android.content.Intent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.emotionshow.R;
import com.jude.emotionshow.data.model.ImageModel;
import com.jude.emotionshow.data.model.SeedModel;
import com.jude.emotionshow.data.server.ServiceResponse;
import com.jude.emotionshow.domain.entities.Image;
import com.jude.emotionshow.domain.entities.Seed;
import com.jude.emotionshow.presentation.widget.CircleTransform;
import com.jude.utils.JUtils;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Jude on 2015/11/22.
 */
public class SeedViewHolder extends BaseViewHolder<Seed> {
    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.avatar)
    ImageView avatar;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.praise)
    LinearLayout praise;
    @Bind(R.id.praise_count)
    TextView praiseCount;

    public SeedViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_seed);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(Seed data) {
        name.setText(data.getName());
        Picasso.with(getContext()).load(ImageModel.getSmallImage(data.getFace())).transform(new CircleTransform()).into(avatar);
        Image background = ImageModel.getSizeImage(data.getPics().get(0), 300);
        Picasso.with(getContext()).load(background.getUrl()).into(image);
        int width = JUtils.getScreenWidth() / 2;
        int height = width * background.getHeight() / background.getWidth();
        image.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
//        if (data.getUid() == UserModel.getInstance().getCurAccount().getId()){
//            praise.setVisibility(View.GONE);
//        }
        praiseCount.setText(data.getPraiseCount() + "");
        praise.setOnClickListener(v -> {
            ((BeamBaseActivity)getContext()).getExpansion().showProgressDialog("点赞中");
            SeedModel.getInstance().praise(data.getId())
                    .finallyDo(() -> ((BeamBaseActivity)getContext()).getExpansion().dismissProgressDialog())
                    .subscribe(new ServiceResponse<Object>() {
                        @Override
                        public void onNext(Object o) {
                            data.setPraiseCount(data.getPraiseCount() + 1);
                            praiseCount.setText(data.getPraiseCount() + "");
                        }

                        @Override
                        public void onServiceError(int status, String info) {
                            super.onServiceError(status, info);
                        }
                    });

        });
        itemView.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), SeedDetailActivity.class);
            i.putExtra("id", data.getId());
            getContext().startActivity(i);
        });
    }
}
