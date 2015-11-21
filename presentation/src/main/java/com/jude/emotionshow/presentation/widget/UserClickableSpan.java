package com.jude.emotionshow.presentation.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by zhuchenxi on 15/9/27.
 */
public class UserClickableSpan extends ClickableSpan{
    private int id;
    private Context ctx;

    public UserClickableSpan(Context ctx, int id) {
        this.ctx = ctx;
        this.id = id;
    }

    @Override
    public void onClick(View view) {
//        Intent i = new Intent(ctx, User.class);
//        i.putExtra("id",id);
//        ctx.startActivity(i);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(Color.parseColor("#5F88CB"));
    }
}
