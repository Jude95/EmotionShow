package com.jude.emotionshow.presentation.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.emotionshow.R;
import com.jude.emotionshow.data.model.ImageModel;
import com.jude.emotionshow.domain.entities.Notify;
import com.jude.emotionshow.presentation.seed.SeedDetailActivity;
import com.jude.emotionshow.presentation.widget.CircleTransform;
import com.jude.emotionshow.presentation.widget.ItemSlideHelper;
import com.jude.emotionshow.presentation.widget.LinearItemDecoration;
import com.jude.emotionshow.presentation.widget.RecentDateFormat;
import com.jude.utils.JTimeTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ske on 2016/1/27.
 */
@RequiresPresenter(NotifyCommentPresenter.class)
public class NotifyCommentActivity extends BeamBaseActivity<NotifyCommentPresenter> {
    @Bind(R.id.recycler)
    EasyRecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_comment);
        ButterKnife.bind(this);
        setupView();
    }

    private List<Notify> notifyList;
    private SlideItemAdapter adapter;

    private void setupView() {
        notifyList = new ArrayList<>();
        $(R.id.back).setOnClickListener(v -> finish());
        recyclerView.setLayoutManager(new LinearLayoutManager(NotifyCommentActivity.this));
        recyclerView.addItemDecoration(new LinearItemDecoration(Color.BLACK));
        recyclerView.setAdapter(adapter = new SlideItemAdapter(notifyList));
        recyclerView.setEmptyView(R.layout.beam_view_list_con_empty);
    }

    public void setNotifyList(List<Notify> notifies) {
        notifyList.clear();
        notifyList.addAll(notifies);
        adapter.notifyDataSetChanged();
    }

    class SlideItemAdapter extends RecyclerView.Adapter<SlideItemAdapter.ViewHolder> implements ItemSlideHelper.Callback {
        private List<Notify> notifyList;

        public SlideItemAdapter(List<Notify> notifies) {
            notifyList = notifies;
        }

        private RecyclerView mRecyclerView;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notify_comment, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Notify data = notifyList.get(position);
            Picasso.with(NotifyCommentActivity.this).load(ImageModel.getSmallImage(data.getFace())).transform(new CircleTransform()).into(holder.avatar);
            holder.name.setText(data.getName());
            holder.time.setText(new JTimeTransform(data.getTime()).toString(new RecentDateFormat()));
            holder.content.setText(data.getContent());
            holder.itemView.setOnClickListener(v -> {
                Intent i = new Intent(NotifyCommentActivity.this, SeedDetailActivity.class);
                i.putExtra("id", data.getData());
                startActivity(i);
            });
            holder.delete.setOnClickListener(v -> {
                getPresenter().delComment(data.getCid());
                notifyList.remove(position);
                notifyItemRemoved(position);
            });
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);

            mRecyclerView = recyclerView;
            mRecyclerView.addOnItemTouchListener(new ItemSlideHelper(mRecyclerView.getContext(), this));
        }

        @Override
        public int getItemCount() {
            return notifyList.size();
        }


        @Override
        public int getHorizontalRange(RecyclerView.ViewHolder holder) {
            if (holder.itemView instanceof LinearLayout) {
                ViewGroup viewGroup = (ViewGroup) holder.itemView;
                if (viewGroup.getChildCount() == 2) {
                    return viewGroup.getChildAt(1).getLayoutParams().width;
                }
            }
            return 0;
        }

        @Override
        public RecyclerView.ViewHolder getChildViewHolder(View childView) {
            return mRecyclerView.getChildViewHolder(childView);
        }

        @Override
        public View findTargetView(float x, float y) {
            return mRecyclerView.findChildViewUnder(x, y);
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.avatar)
            ImageView avatar;
            @Bind(R.id.name)
            TextView name;
            @Bind(R.id.time)
            TextView time;
            @Bind(R.id.content)
            TextView content;
            @Bind(R.id.tv_delete)
            TextView delete;

            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }
}
