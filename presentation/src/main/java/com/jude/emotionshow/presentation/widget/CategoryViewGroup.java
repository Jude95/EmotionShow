package com.jude.emotionshow.presentation.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.emotionshow.R;
import com.jude.emotionshow.domain.entities.Category;
import com.jude.tagview.TAGView;
import com.jude.utils.JUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Jude on 2015/11/19.
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

    public void setCategoryList(List<Category> list){
        categoryContainer.removeAllViews();
        for (Category category : list) {
            CategoryItemView item = new CategoryItemView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, JUtils.dip2px(76));
            params.setMargins(0,JUtils.dip2px(8),0,0);
            item.setLayoutParams(params);
            item.setCategory(category);
            categoryContainer.addView(item);
        }
    }


}
