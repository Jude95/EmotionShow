package com.jude.emotionshow.presentation.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.jude.emotionshow.data.model.ImageModel;
import com.jude.emotionshow.domain.entities.Image;
import com.squareup.picasso.Picasso;

import java.util.Collection;

/**
 * Created by Mr.Jude on 2015/9/16.
 */
public class  NetImageAdapter extends ArrayAdapter<Image> implements View.OnClickListener{

    public NetImageAdapter(Context context) {
        super(context, 0);
    }

    public NetImageAdapter(Context context, Image[] objects) {
        super(context, 0, objects);
    }

    public NetImageAdapter(Context context, Collection<Image> objects) {
        super(context, 0, objects.toArray(new Image[objects.size()]));
    }

    private int size;

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView view = new ImageView(parent.getContext());
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        if (size !=0){
            Image image = ImageModel.getSizeImage(getItem(position), size);
            Picasso.with(getContext())
                    .load(image.getUrl())
                    .resize(image.getWidth(), image.getHeight())
                    .into(view);
        }else{
            Picasso.with(getContext())
                    .load(getItem(position).getUrl())
                    .into(view);
        }
        return view;
    }


    @Override
    public void onClick(View v) {
//        ArrayList<Uri> uris = new ArrayList<>();
//        for (int i = 0; i < getCount(); i++) {
//            uris.add(ImageModel.getInstance().getLargeImage(getItem(i)));
//        }
//        Intent i = new Intent(v.getContext(), ImageViewActivity.class);
//        i.putParcelableArrayListExtra(ImageViewActivity.KEY_URIS,uris);
//        i.putExtra(ImageViewActivity.KEY_INDEX, (int) v.getTag());
//        v.getContext().startActivity(i);
    }
}
