package com.jude.emotionshow.presentation.seed;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.emotionshow.R;
import com.jude.emotionshow.data.model.ImageModel;
import com.jude.emotionshow.domain.entities.Image;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhuchenxi on 15/12/4.
 */
public class ActivityView extends LinearLayout {

    @Bind(R.id.item1)
    ImageView item1;
    @Bind(R.id.item2)
    ImageView item2;
    @Bind(R.id.item3)
    ImageView item3;
    @Bind(R.id.item4)
    ImageView item4;
    @Bind(R.id.item5)
    ImageView item5;
    ImageView[] imageViews;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.more)
    TextView more;

    public ActivityView(Context context) {
        super(context);
        init();
    }

    public ActivityView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ActivityView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_activity, this);
        ButterKnife.bind(this, this);
        imageViews = new ImageView[]{item1, item2, item3, item4, item5};
        more.setOnClickListener(v->{
            if (listener!=null){
                listener.more();
            }
        });
    }

    public void setImage(List<Image> list) {
        for (int i = 0; i < list.size(); i++) {
            setImage(i, list.get(i));
        }
    }

    private void setImage(int position, Image image) {
        if (position < imageViews.length)
            Picasso.with(getContext()).load(ImageModel.getSizeImage(image, 600).getUrl()).into(imageViews[position]);
    }

    public void setTitle(String titleStr){
        title.setText(titleStr);
    }

    private OnMoreListener listener;
    public interface OnMoreListener {
        void more();
    }

    public void setMoreListener(OnMoreListener listener) {
        this.listener = listener;
    }
}
