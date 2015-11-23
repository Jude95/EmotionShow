package com.jude.emotionshow.presentation.seed;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.data.BeamDataActivity;
import com.jude.emotionshow.R;
import com.jude.emotionshow.data.model.ImageModel;
import com.jude.emotionshow.domain.entities.CategoryDetail;
import com.jude.emotionshow.domain.entities.Seed;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Jude on 2015/11/22.
 */
@RequiresPresenter(CategoryPresenter.class)
public class CategoryActivity extends BeamDataActivity<CategoryPresenter, CategoryDetail> {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.recycler)
    RecyclerView recycler;
    @Bind(R.id.background)
    ImageView background;
    @Bind(R.id.seed_count)
    TextView seedCount;
    @Bind(R.id.container_seed)
    LinearLayout containerSeed;
    @Bind(R.id.visit_count)
    TextView visitCount;
    @Bind(R.id.container_visit)
    LinearLayout containerVisit;
    @Bind(R.id.praise_count)
    TextView praiseCount;
    @Bind(R.id.container_praise)
    LinearLayout containerPraise;

    private SeedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        RecyclerViewHeader header = RecyclerViewHeader.fromXml(this, R.layout.head_category);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        header.attachTo(recycler);
        ButterKnife.bind(this);
        recycler.setAdapter(adapter = new SeedAdapter(this));
        adapter.setMore(new View(this), () -> {
            getPresenter().loadMore();
        });
    }

    @Override
    public void setData(CategoryDetail data) {
        title.setText(data.getName());
        Picasso.with(this).load(ImageModel.getLargeImage(data.getBackground())).into(background);
        seedCount.setText(data.getSeedCount()+"");
        visitCount.setText(data.getVisitCount()+"");
        praiseCount.setText(data.getPraiseCount()+"");
    }

    public void addSeed(List<Seed> data){
        adapter.addAll(data);
    }
}
