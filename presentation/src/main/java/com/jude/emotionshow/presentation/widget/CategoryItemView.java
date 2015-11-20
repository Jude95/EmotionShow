package com.jude.emotionshow.presentation.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.emotionshow.R;
import com.jude.emotionshow.data.model.ImageModel;
import com.jude.emotionshow.domain.entities.Category;
import com.jude.emotionshow.domain.entities.Image;
import com.jude.emotionshow.domain.entities.Seed;
import com.jude.utils.JUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Jude on 2015/11/19.
 */
public class CategoryItemView extends LinearLayout {


    @Bind(R.id.background)
    ImageView background;
    @Bind(R.id.title_zh)
    TextView titleZh;
    @Bind(R.id.title_en)
    TextView titleEn;
    @Bind(R.id.list)
    EasyRecyclerView list;

    Adapter adapter;

    public CategoryItemView(Context context) {
        super(context);
        init();
    }

    public CategoryItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        inflate(getContext(), R.layout.view_category_item, this);
        ButterKnife.bind(this, this);
        list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        list.setAdapter(adapter = new Adapter(getContext()));
    }

    public void setCategory(Category category){
        Picasso.with(getContext())
                .load(category.getTitle().getUrl())
                .resize(100,100)
                .into(background);
        titleEn.setText(category.getTitle().getEn());
        titleZh.setText(category.getTitle().getCh());
        adapter.clear();
        ArrayList<Image> arr = new ArrayList<>();
        for (Seed seed : category.getData()) {
            arr.add(seed.getPics().get(0));
        }
        adapter.addFooter(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.view_category_footer,parent,false);
                return view;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
        adapter.addAll(arr);
    }

    private class Adapter extends RecyclerArrayAdapter<Image> {
        public Adapter(Context context) {
            super(context);
        }

        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(parent);
        }
    }

    private class ViewHolder extends BaseViewHolder<Image>{

        public ViewHolder(ViewGroup parent) {
            super(new ImageView(parent.getContext()));
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(JUtils.dip2px(84), JUtils.dip2px(76));
            itemView.setLayoutParams(new ViewGroup.LayoutParams(params));
            itemView.setPadding(0, 0, JUtils.dip2px(8), 0);
            ((ImageView) itemView).setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        @Override
        public void setData(Image data) {
            Image image = ImageModel.getSmallImage(data);
            Picasso.with(getContext())
                    .load(image.getUrl())
                    .resize(image.getWidth(),image.getHeight())
                    .into((ImageView) itemView);
        }

    }
}
