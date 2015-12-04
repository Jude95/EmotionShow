package com.jude.emotionshow.presentation.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.data.BeamDataActivity;
import com.jude.emotionshow.R;
import com.jude.emotionshow.data.model.ImageModel;
import com.jude.emotionshow.data.model.UserModel;
import com.jude.emotionshow.domain.entities.PersonDetail;
import com.jude.emotionshow.presentation.widget.CircleTransform;
import com.jude.tagview.TAGView;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Jude on 2015/11/22.
 */
@RequiresPresenter(UserPreviewPresenter.class)
public class UserPreviewActivity extends BeamDataActivity<UserPreviewPresenter, PersonDetail> {
    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.background)
    ImageView background;
    @Bind(R.id.avatar)
    ImageView avatar;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.seed_count)
    TAGView seedCount;
    @Bind(R.id.fans_count)
    TAGView fansCount;
    @Bind(R.id.praise_count)
    TAGView praiseCount;
    @Bind(R.id.back_img)
    ImageView backImg;
    @Bind(R.id.sign)
    TextView sign;
    @Bind(R.id.more)
    ImageView more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_preview);
        ButterKnife.bind(this);
        back.setOnClickListener(v -> finish());
        background.setOnClickListener(v -> {
            Intent i = new Intent(this, UserDetailActivity.class);
            i.putExtra("id", getPresenter().id);
            startActivity(i);
            finish();
        });
        more.setOnClickListener(v->showToolDialog());
    }

    private void showToolDialog(){
        new MaterialDialog.Builder(this)
                .items("修改背景","编辑资料")
                .itemsCallback((dialog, itemView, which, text) -> {
                    if (which==0){
                        getPresenter().selectImage();
                    }else {
                        startActivity(new Intent(this,UserDetailEditActivity.class));
                    }
                }).show();
    }

    @Override
    public void setData(PersonDetail data) {
        if (!TextUtils.isEmpty(data.getBackground()))
        Picasso.with(this).load(ImageModel.getLargeImage(data.getBackground()))
                .into(background);
        Picasso.with(this).load(ImageModel.getSmallImage(data.getAvatar()))
                .resize(150, 150)
                .transform(new CircleTransform())
                .into(avatar);
        name.setText(data.getName());
        sign.setText(data.getSign());
        seedCount.setText(data.getSeedCount() + "\n印记");
        fansCount.setText(data.getFansCount() + "\n粉丝");
        praiseCount.setText(data.getPraiseCount() + "\n点赞");
        if (UserModel.getInstance().isLogin()&&UserModel.getInstance().getCurAccount().getId()==data.getId()){
            more.setVisibility(View.VISIBLE);
        }
    }

    public void setBackground(Uri uri){
        background.setImageURI(uri);
    }
}
