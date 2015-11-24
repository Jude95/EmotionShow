package com.jude.emotionshow.presentation.seed;

import android.content.Intent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.emotionshow.R;
import com.jude.emotionshow.data.model.ImageModel;
import com.jude.emotionshow.domain.entities.Image;
import com.jude.emotionshow.domain.entities.SeedDetail;
import com.jude.utils.JTimeTransform;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Jude on 2015/11/24.
 */
public class SeedCalendarViewHolder extends BaseViewHolder<SeedDetail> {
    @Bind(R.id.day)
    TextView day;
    @Bind(R.id.month)
    TextView month;
    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.content)
    TextView content;

    public static final String[] MONTH_STRING = {"一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"};

    public SeedCalendarViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_seed_calendar);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(SeedDetail data) {
        day.setText(new JTimeTransform(data.getTime()).getDay()+"");
        month.setText(MONTH_STRING[new JTimeTransform(data.getTime()).getMonth()-1]);
        Image img = ImageModel.getSizeImage(data.getPics().get(0), 300);
        Picasso.with(getContext()).load(img.getUrl()).into(image);
        content.setText(data.getContent());
        itemView.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), SeedDetailActivity.class);
            i.putExtra("id", data.getId());
            getContext().startActivity(i);
        });
    }
}
