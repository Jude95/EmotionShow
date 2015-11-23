package com.jude.emotionshow.presentation.seed;

import android.text.SpannableString;
import android.text.Spanned;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.emotionshow.domain.entities.Comment;
import com.jude.emotionshow.presentation.widget.UserClickableSpan;
import com.jude.utils.JUtils;

/**
 * Created by Mr.Jude on 2015/11/21.
 */
public class CommentViewHolder extends BaseViewHolder<Comment> {
    public CommentViewHolder(ViewGroup parent) {
        super(new TextView(parent.getContext()));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, JUtils.dip2px(4),0,0);
        itemView.setLayoutParams(params);
    }

    @Override
    public void setData(Comment data) {
        SpannableString spannableInfo;
        if (data.getOriginalId()==0){
            spannableInfo = new SpannableString(data.getAuthorName() + " : " + data.getContent());
            spannableInfo.setSpan(new UserClickableSpan(getContext(), data.getOriginalAuthorId()), 0, data.getAuthorName().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }else{
            String originalName = data.getOriginalAuthorName();
            spannableInfo = new SpannableString(data.getAuthorName() +" 回复 "+originalName+ " : " + data.getContent());
            spannableInfo.setSpan(new UserClickableSpan(getContext(), data.getAuthorId()), 0, data.getAuthorName().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableInfo.setSpan(new UserClickableSpan(getContext(), data.getAuthorId()), data.getAuthorName().length()+4, data.getAuthorName().length()+4+originalName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        ((TextView)itemView).setText(spannableInfo);
    }
}
