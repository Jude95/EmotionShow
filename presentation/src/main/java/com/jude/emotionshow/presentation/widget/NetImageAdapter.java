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
public class  NetImageAdapter extends ArrayAdapter<Image> {

    private OnItemClickListener mListener;

    public NetImageAdapter(Context context) {
        super(context, 0);
    }

    public NetImageAdapter(Context context, Image[] objects) {
        super(context, 0, objects);
    }

    public NetImageAdapter(Context context, Collection<Image> objects) {
        super(context, 0, objects.toArray(new Image[objects.size()]));
    }

    public NetImageAdapter(Context context, Collection<Image> objects,int size) {
        super(context, 0, objects.toArray(new Image[objects.size()]));
        this.size = size;
    }
    private int size = ImageModel.IMAGE_SIZE_SMALL;

    public void setSize(int size) {
        this.size = size;
    }
    public void setListener(OnItemClickListener mListener) {
        this.mListener = mListener;
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
                    .into(view);
        }else{
            Picasso.with(getContext())
                    .load(getItem(position).getUrl())
                    .into(view);
        }
        if (mListener!=null){
            view.setOnClickListener(v->mListener.onClick(position));
        }
        return view;
    }


    public interface OnItemClickListener{
        void onClick(int position);
    }
}
