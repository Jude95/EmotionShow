package com.jude.emotionshow.presentation.seed;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.data.BeamDataActivity;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.emotionshow.R;
import com.jude.emotionshow.data.model.ImageModel;
import com.jude.emotionshow.domain.entities.Comment;
import com.jude.emotionshow.domain.entities.SeedDetail;
import com.jude.emotionshow.presentation.main.ImageViewActivity;
import com.jude.emotionshow.presentation.user.UserPreviewActivity;
import com.jude.emotionshow.presentation.widget.CircleTransform;
import com.jude.emotionshow.presentation.widget.LinearWrapContentRecyclerView;
import com.jude.emotionshow.presentation.widget.NetImageAdapter;
import com.jude.exgridview.ExGridView;
import com.jude.tagview.TAGView;
import com.jude.utils.JTimeTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Jude on 2015/11/21.
 */
@RequiresPresenter(SeedDetailPresenter.class)
public class SeedDetailActivity extends BeamDataActivity<SeedDetailPresenter, SeedDetail> {

    @Bind(R.id.back_img)
    ImageView backImg;
    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.avatar)
    ImageView avatar;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.author_container)
    RelativeLayout authorContainer;
    @Bind(R.id.pictures)
    ExGridView pictures;
    @Bind(R.id.content)
    TextView content;
    @Bind(R.id.praise)
    ImageView praise;
    @Bind(R.id.praise_count)
    TextView praiseCount;
    @Bind(R.id.comment_count)
    TextView commentCount;
    @Bind(R.id.more)
    ImageView more;
    @Bind(R.id.comment_list)
    LinearWrapContentRecyclerView commentList;
    @Bind(R.id.reply)
    EditText reply;
    @Bind(R.id.reply_send)
    TAGView replySend;
    @Bind(R.id.container)
    CardView container;

    int curCommentId = 0;
    RecyclerArrayAdapter<Comment> mCommentAdapter = new RecyclerArrayAdapter<Comment>(this) {
        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return new CommentViewHolder(parent);
        }
    };
    NetImageAdapter mImageAdapter;
    @Bind(R.id.score_count)
    TextView scoreCount;
    @Bind(R.id.praise_container)
    LinearLayout praiseContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seed_detail);
        ButterKnife.bind(this);
        back.setOnClickListener(v -> finish());
        replySend.setOnClickListener(v -> getPresenter().comment(curCommentId, reply.getText().toString()));
        praiseContainer.setOnClickListener(v -> getPresenter().praise());
        container.setOnClickListener(v -> {
            reply.setHint("对印记发表评论");
            curCommentId = 0;
        });
        more.setOnClickListener(v -> showToolDialog());
    }

    @Override
    public void setData(SeedDetail data) {
        super.setData(data);
        Picasso.with(this).load(ImageModel.getSmallImage(data.getAuthor().getAvatar()))
                .transform(new CircleTransform())
                .into(avatar);
        name.setText(data.getAuthor().getName());
        time.setText(new JTimeTransform(data.getTime()).toString("MM月dd日 HH:mm"));
        content.setText(data.getContent());
        praiseCount.setText(data.getPraiseCount() + "");
        commentCount.setText(data.getCommentCount() + "");
        scoreCount.setText(data.getScore() + "");
        pictures.removeAllViews();
        if (data.getPics() != null && data.getPics().size() != 0) {
            pictures.setAdapter(mImageAdapter = new NetImageAdapter(this, data.getPics(), ImageModel.IMAGE_SIZE_SMALL * (4 - Math.min(data.getPics().size(), 3))));
            pictures.setColumnCount(Math.min(data.getPics().size(), 3));
            mImageAdapter.setListener(position -> {
                ArrayList<Uri> uris = new ArrayList<>();
                for (int i = 0; i < data.getPics().size(); i++) {
                    uris.add(Uri.parse(ImageModel.getLargeImage(data.getPics().get(i)).getUrl()));
                }
                Intent i = new Intent(SeedDetailActivity.this, ImageViewActivity.class);
                i.putParcelableArrayListExtra(ImageViewActivity.KEY_URIS, uris);
                i.putExtra(ImageViewActivity.KEY_INDEX, position);
                SeedDetailActivity.this.startActivity(i);
            });
            mImageAdapter.notifyDataSetChanged();
        }
        commentList.setOnItemClickListener(position -> {
            reply.setHint("回复: " + data.getComment().get(position).getAuthorName());
            curCommentId = data.getComment().get(position).getId();
        });
        mCommentAdapter.clear();
        mCommentAdapter.addAll(data.getComment());
        commentList.setAdapter(mCommentAdapter);
        reply.setText("");
        authorContainer.setOnClickListener(v -> {
            Intent i = new Intent(this, UserPreviewActivity.class);
            i.putExtra("id", data.getAuthor().getId());
            startActivity(i);
        });
        praise.setImageResource(data.getPraised() == 0 ? R.drawable.praise : R.drawable.praise_red);
    }

    void showToolDialog() {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .customView(R.layout.dialog_seed_tool, false)
                .show();
        View view = dialog.getCustomView();
        View share = $(view, R.id.share);
        View collect = $(view, R.id.collect);
        View report = $(view, R.id.report);

        share.setOnClickListener(v -> {
            dialog.dismiss();
            getPresenter().share();
        });
        collect.setOnClickListener(v -> {
            getPresenter().collect();
            dialog.dismiss();
        });
        report.setOnClickListener(v -> {
            getPresenter().report();
            dialog.dismiss();
        });
    }

}
