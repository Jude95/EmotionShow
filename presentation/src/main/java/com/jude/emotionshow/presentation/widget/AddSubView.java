package com.jude.emotionshow.presentation.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jude.emotionshow.R;

/**
 * Created by mike on 2015/12/26.
 */
public class AddSubView extends LinearLayout {
    Button add;
    Button sub;
    EditText textNum;

    OnNumChangeListener onNumChangeListener;

    int num;
    int MAX_NUM = Integer.MAX_VALUE;
    int MIN_NUM = 0;

    public AddSubView(Context context) {
        super(context);
        initView(context);
    }

    public AddSubView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public AddSubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void initView(Context context) {
        View view = View.inflate(context, R.layout.view_addsub, this);
        add = (Button) view.findViewById(R.id.add);
        sub = (Button) view.findViewById(R.id.sub);
        textNum = (EditText) view.findViewById(R.id.et_num);

        num = TextUtils.isEmpty(textNum.getText().toString()) ? 1 : Integer.parseInt(textNum.getText().toString());

        add.setOnClickListener(new OnButtonClickListener());
        sub.setOnClickListener(new OnButtonClickListener());
        textNum.addTextChangedListener(new OnTextChangeListener());
    }

    public void setOnNumChangeListener(OnNumChangeListener onNumChangeListener) {
        this.onNumChangeListener = onNumChangeListener;
    }

    class OnTextChangeListener implements TextWatcher {

        @Override
        public void afterTextChanged(Editable s) {
            updateText(s.toString());
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

    }

    private void updateText(String numString) {
        if (TextUtils.isEmpty(numString)) {
            num = MIN_NUM;
            if (onNumChangeListener != null) {
                onNumChangeListener.onNumChange(AddSubView.this, num);
            }
        } else {
            int numInt = Integer.parseInt(numString);
            if (numInt < MIN_NUM) {
                textNum.setText(MIN_NUM + "");
            } else {
                if (numInt > MAX_NUM) {
                    num = MAX_NUM;
                    textNum.setText(MAX_NUM + "");
                } else {
                    num = numInt;
                }
                // 设置EditText光标位置 为文本末端
                textNum.setSelection(textNum.getText().toString().length());
                if (onNumChangeListener != null) {
                    onNumChangeListener.onNumChange(AddSubView.this, num);
                }
            }
        }
    }

    private void updateMaxText() {
        String numString = textNum.getText().toString();
        if (TextUtils.isEmpty(numString)) {
            num = MIN_NUM;
            textNum.setText(MIN_NUM + "");
        } else {
            int numInt = Integer.parseInt(numString);
            if (numInt > MAX_NUM) {
                num = MAX_NUM;
                textNum.setText(MAX_NUM + "");
                textNum.setSelection(textNum.getText().toString().length());
                if (onNumChangeListener != null) {
                    onNumChangeListener.onNumChange(AddSubView.this, num);
                }
            }
        }
    }

    class OnButtonClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            String numString = textNum.getText().toString();
            if (TextUtils.isEmpty(numString)) {
                num = MIN_NUM;
                textNum.setText(MIN_NUM + "");
            } else {
                if (v.getId() == R.id.sub) {
                    if (--num < MIN_NUM) { // 先减，再判断
                        num++;
                        textNum.setText(MIN_NUM + "");
                    } else {
                        textNum.setText(String.valueOf(num));
                        if (onNumChangeListener != null) {
                            onNumChangeListener.onNumChange(AddSubView.this, num);
                        }
                    }
                } else if (v.getId() == R.id.add) {
                    if (++num > MAX_NUM) { // 先加，再判断
                        num--;
                        textNum.setText(MAX_NUM + "");
                    } else {
                        textNum.setText(String.valueOf(num));

                        if (onNumChangeListener != null) {
                            onNumChangeListener.onNumChange(AddSubView.this, num);
                        }
                    }
                }
            }
        }
    }

    public int getNum() {
        return num;
    }

    public void setMaxNum(int maxNum) {
        this.MAX_NUM = maxNum;
        updateMaxText();
    }

    public interface OnNumChangeListener {
        void onNumChange(View view, int num);
    }
}