package com.jude.emotionshow.presentation.seed;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.emotionshow.R;
import com.jude.emotionshow.domain.entities.CategoryPreview;
import com.jude.tagview.TAGView;
import com.jude.utils.JUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Jude on 2015/11/19.
 * 场景与情感模块的View，一样对外接口设置数据，显示由自己负责
 */
public class CategoryViewGroup extends LinearLayout {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.category_container)
    LinearLayout categoryContainer;
    @Bind(R.id.change)
    TAGView change;

    private OnRequestListener listener;
    public interface OnRequestListener{
        void request();
    }

    public CategoryViewGroup(Context context) {
        super(context);
        init();
    }

    public CategoryViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_category, this);
        ButterKnife.bind(this,this);
        change.setOnClickListener(v->{
            if (listener!=null)listener.request();
        });
    }

    public void setting(String title,OnRequestListener listener){
        this.title.setText(title);
        this.listener = listener;
    }

    //显示数据
    public void setCategoryList(List<CategoryPreview> list){
        categoryContainer.removeAllViews();
        for (CategoryPreview categoryPreview : list) {
            CategoryItemView item = new CategoryItemView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, JUtils.dip2px(76));
            params.setMargins(0,JUtils.dip2px(8),0,0);
            item.setLayoutParams(params);
            item.setCategory(categoryPreview);
            categoryContainer.addView(item);
        }
    }


}
