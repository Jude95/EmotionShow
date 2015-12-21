package com.jude.emotionshow.presentation.seed;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.emotionshow.R;
import com.jude.emotionshow.data.model.ImageModel;
import com.jude.emotionshow.domain.entities.Seed;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhuchenxi on 15/12/4.
 * 就是现在挂着“双蛋有礼”的那一块View。只要传数据进来就行了，怎么显示由本View处理。
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
        //初始化5个写死的View
        inflate(getContext(), R.layout.view_activity, this);
        ButterKnife.bind(this, this);
        imageViews = new ImageView[]{item1, item2, item3, item4, item5};
        //响应点击时间
        more.setOnClickListener(v->{
            if (listener!=null){
                listener.more();
            }
        });
    }

    //显示数据
    public void setImage(List<Seed> list) {
        for (int i = 0; i < list.size(); i++) {
            setImage(i, list.get(i));
        }
    }

    private void setImage(int position, Seed seed) {
        if (position < imageViews.length)
            Picasso.with(getContext()).load(ImageModel.getSizeImage(seed.getPics().get(0), 600).getUrl()).into(imageViews[position]);
        imageViews[position].setOnClickListener(v->{
            Intent i = new Intent(getContext(), SeedDetailActivity.class);
            i.putExtra("id",seed.getId());
            getContext().startActivity(i);
        });
    }

    //设置标题，毕竟双蛋过了就不会再挂双蛋有礼了
    public void setTitle(String titleStr){
        title.setText(titleStr);
    }

    private OnMoreListener listener;
    public interface OnMoreListener {
        void more();
    }

    //“查看更多”
    public void setMoreListener(OnMoreListener listener) {
        this.listener = listener;
    }
}
