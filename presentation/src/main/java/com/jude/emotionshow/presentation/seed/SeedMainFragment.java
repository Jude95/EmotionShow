package com.jude.emotionshow.presentation.seed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.beam.bijection.BeamFragment;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.emotionshow.R;
import com.jude.emotionshow.domain.entities.Banner;
import com.jude.emotionshow.domain.entities.Category;
import com.jude.emotionshow.domain.entities.Topic;
import com.jude.emotionshow.presentation.main.SearchActivity;
import com.jude.emotionshow.presentation.widget.LoopRecyclerViewPagerAdapter;
import com.jude.emotionshow.presentation.widget.RecyclerViewPager;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Jude on 2015/11/18.
 */
@RequiresPresenter(SeedMainPresenter.class)
public class SeedMainFragment extends BeamFragment<SeedMainPresenter> {
    private static SeedMainFragment instance;
    @Bind(R.id.banner)
    RollPagerView banner;
    @Bind(R.id.seed_cards)
    RecyclerViewPager seedCards;
    @Bind(R.id.category_scence)
    CategoryViewGroup categoryScence;
    @Bind(R.id.category_process)
    CategoryViewGroup categoryProcess;
    @Bind(R.id.search)
    ImageView search;

    public static SeedMainFragment getInstance() {
        if (instance == null) instance = new SeedMainFragment();
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seed_main, container, false);
        ButterKnife.bind(this, view);
        LinearLayoutManager layout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        seedCards.setLayoutManager(layout);
        seedCards.setHasFixedSize(true);
        search.setOnClickListener(v -> startActivity(new Intent(getContext(), SearchActivity.class)));
        seedCards.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
//                mPositionText.setText("First: " + seedCards.getFirstVisiblePosition());
                int childCount = seedCards.getChildCount();
                int width = seedCards.getChildAt(0).getWidth();
                int padding = (seedCards.getWidth() - width) / 2;

                for (int j = 0; j < childCount; j++) {
                    View v = recyclerView.getChildAt(j);
                    //往左 从 padding 到 -(v.getWidth()-padding) 的过程中，由大到小
                    float rate = 0;
                    ;
                    if (v.getLeft() <= padding) {
                        if (v.getLeft() >= padding - v.getWidth()) {
                            rate = (padding - v.getLeft()) * 1f / v.getWidth();
                        } else {
                            rate = 1;
                        }
                        v.setScaleY(1 - rate * 0.1f);
                        v.setScaleX(1 - rate * 0.1f);

                    } else {
                        //往右 从 padding 到 recyclerView.getWidth()-padding 的过程中，由大到小
                        if (v.getLeft() <= recyclerView.getWidth() - padding) {
                            rate = (recyclerView.getWidth() - padding - v.getLeft()) * 1f / v.getWidth();
                        }
                        v.setScaleY(0.9f + rate * 0.1f);
                        v.setScaleX(0.9f + rate * 0.1f);
                    }
                }
            }
        });

        seedCards.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (seedCards.getChildCount() < 3) {
                    if (seedCards.getChildAt(1) != null) {
                        if (seedCards.getCurrentPosition() == 0) {
                            View v1 = seedCards.getChildAt(1);
                            v1.setScaleY(0.9f);
                            v1.setScaleX(0.9f);
                        } else {
                            View v1 = seedCards.getChildAt(0);
                            v1.setScaleY(0.9f);
                            v1.setScaleX(0.9f);
                        }
                    }
                } else {
                    if (seedCards.getChildAt(0) != null) {
                        View v0 = seedCards.getChildAt(0);
                        v0.setScaleY(0.9f);
                        v0.setScaleX(0.9f);
                    }
                    if (seedCards.getChildAt(2) != null) {
                        View v2 = seedCards.getChildAt(2);
                        v2.setScaleY(0.9f);
                        v2.setScaleX(0.9f);
                    }
                }

            }
        });
        return view;
    }

    public void setBanner(List<Banner> banners) {
        if (banners==null||banners.size() == 0) {
            return;
        }
        banner.setAdapter(new StaticPagerAdapter() {
            @Override
            public View getView(ViewGroup container, int position) {
                ImageView imageView = new ImageView(getContext());
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Picasso.with(container.getContext())
                        .load(banners.get(position).getUrl())
                        .into(imageView);
                return imageView;
            }

            @Override
            public int getCount() {
                return banners.size();
            }
        });
    }

    public void setTopic(List<Topic> topic) {
        for (Topic topic1 : new ArrayList<>(topic)) {
            if (topic1.getData().size() == 0) {
                topic.remove(topic1);
            }
        }
        if (topic.size()==0){return;}

        TopicAdapter adapter = new TopicAdapter(getContext());
        seedCards.setAdapter(new LoopRecyclerViewPagerAdapter<>(seedCards, adapter));
        adapter.addAll(topic);
        seedCards.scrollToPosition(100);
    }

    public void setCategoryScene(List<Category> list){
        if (list==null||list.size()==0){return;}
        categoryScence.setting("场景", () -> getPresenter().getCategoryScence());
        categoryScence.setCategoryList(list);
    }


    public void setCategoryProcess(List<Category> list){
        if (list==null||list.size()==0){return;}
        categoryProcess.setting("情感", () -> getPresenter().getCategoryProcess());
        categoryProcess.setCategoryList(list);
    }


    private class TopicAdapter extends RecyclerArrayAdapter<Topic> {

        public TopicAdapter(Context context) {
            super(context);
        }

        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return new TopicViewHolder(parent);
        }
    }

    private class TopicViewHolder extends BaseViewHolder<Topic> {

        public TopicViewHolder(ViewGroup parent) {
            super(new TopicView(parent.getContext()));
        }

        @Override
        public void setData(Topic data) {
            ((TopicView) itemView).setTopic(data);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
