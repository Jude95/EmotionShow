package com.jude.emotionshow.presentation.seed;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jude.emotionshow.R;
import com.jude.emotionshow.data.model.ImageModel;
import com.jude.emotionshow.domain.entities.Image;
import com.jude.emotionshow.domain.entities.Topic;
import com.jude.emotionshow.presentation.widget.NetImageAdapter;
import com.jude.exgridview.ExGridView;
import com.jude.utils.JUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Jude on 2015/11/19.
 * 发现的精选秀什么的View，同样设置数据就行了
 */
public class TopicView extends FrameLayout {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.image)
    ExGridView image;

    private NetImageAdapter adapter;
    public TopicView(Context context) {
        super(context);
        init();
    }

    public TopicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_topic, this);
        ButterKnife.bind(this, this);
        setLayoutParams(new ViewGroup.LayoutParams((int) (JUtils.getScreenWidth() * 0.6), ViewGroup.LayoutParams.WRAP_CONTENT));
        image.setAdapter(adapter = new NetImageAdapter(getContext()));
        adapter.setSize(ImageModel.IMAGE_SIZE_SMALL);

    }

    public void setTopic(Topic topic){
        title.setText(topic.getTitle());
        adapter.clear();
        ArrayList<Image> urls = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            if (topic.getData().size()>i){
                urls.add(topic.getData().get(i).getPics().get(0));
            }
        }
        adapter.addAll(urls);
        setOnClickListener(v -> {
            Intent i = new Intent(getContext(), RecommendActivity.class);
            i.putExtra("type",topic.getType());
            getContext().startActivity(i);
        });
    }

}
