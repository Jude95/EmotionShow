package com.jude.emotionshow.presentation.seed;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.data.BeamDataActivity;
import com.jude.emotionshow.R;
import com.jude.emotionshow.domain.entities.SeedEditable;
import com.jude.exgridview.ImagePieceView;
import com.jude.exgridview.PieceViewGroup;
import com.jude.utils.JUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Jude on 2015/11/21.
 */
@RequiresPresenter(WritingPresenter.class)
public class WritingActivity extends BeamDataActivity<WritingPresenter,SeedEditable> {

    @Bind(R.id.back_img)
    ImageView backImg;
    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.done)
    LinearLayout done;
    @Bind(R.id.scope_text)
    TextView scopeText;
    @Bind(R.id.scope_container)
    LinearLayout scopeContainer;
    @Bind(R.id.content)
    EditText content;
    @Bind(R.id.images)
    PieceViewGroup images;
    @Bind(R.id.scene_text)
    TextView sceneText;
    @Bind(R.id.scene_container)
    LinearLayout sceneContainer;
    @Bind(R.id.process_text)
    TextView processText;
    @Bind(R.id.process_container)
    LinearLayout processContainer;
    @Bind(R.id.address_text)
    TextView addressText;
    @Bind(R.id.address_container)
    LinearLayout addressContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writting);
        ButterKnife.bind(this);
        back.setOnClickListener(v -> finish());
        done.setOnClickListener(v->getPresenter().publish());
        scopeContainer.setOnClickListener(v -> showScopeTypeEdit());
        processContainer.setOnClickListener(v -> showProcessEdit());
        sceneContainer.setOnClickListener(v -> showSceneEdit());
        images.setOnAskViewListener(() -> getPresenter().editPicture());
        images.setAddImageRes(R.drawable.pic_add);
        images.setOKImageRes(R.drawable.pic_ok);
        images.setOnViewDeleteListener(getPresenter());
        addressText.setText(getPresenter().data.getAddress());
        addressContainer.setOnClickListener(v->showAddressEdit());
        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getPresenter().data.setContent(s.toString());
            }
        });
    }

    public void addImage(Bitmap bitmap) {
        ImagePieceView pieceView = new ImagePieceView(this);
        pieceView.setImageBitmap(bitmap);
        images.addView(pieceView);
    }

    String[] SCOPE = {"全公开","仅朋友可见","仅自己可见","匿名发布"};
    private void showScopeTypeEdit() {
        new MaterialDialog.Builder(this)
                .title("请选择公开方式")
                .items(SCOPE)
                .itemsCallbackSingleChoice(getPresenter().data.getScope(), new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        getPresenter().data.setScope(which);
                        scopeText.setText(text);
                        return true;
                    }
                })
                .positiveText("确定")
                .show();
    }


    String[] SCENE={"咖啡厅","音乐厅","酒吧","操场","KTV","网咖"};
    private void showSceneEdit() {
        int index = 0;
        for (int i = 0; i < SCENE.length; i++) {
            if (SCENE[i].equals(getPresenter().data.getScene())){
                index = i;
                break;
            }
        }
        new MaterialDialog.Builder(this)
                .title("请选择场景")
                .items(SCENE)
                .itemsCallbackSingleChoice(index, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        getPresenter().data.setScene(text.toString());
                        sceneText.setText(text);
                        return true;
                    }
                })
                .positiveText("确定")
                .show();
    }

    String[] PROCESS={"暗恋","追求","热恋","异地恋","平淡期","求婚"};
    private void showProcessEdit() {
        int index = 0;
        for (int i = 0; i < PROCESS.length; i++) {
            if (PROCESS[i].equals(getPresenter().data.getProcess())){
                index = i;
                break;
            }
        }
        new MaterialDialog.Builder(this)
                .title("请选择场景")
                .items(PROCESS)
                .itemsCallbackSingleChoice(index, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        getPresenter().data.setProcess(text.toString());
                        processText.setText(text);
                        return true;
                    }
                })
                .positiveText("确定")
                .show();
    }

    private void showAddressEdit() {
        new MaterialDialog.Builder(this)
                .title("输入地址")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .inputRange(0, 20)
                .input("", getPresenter().data.getAddress(), new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        if (input.toString().trim().isEmpty()) {
                            JUtils.Toast("标题不能为空");
                            return;
                        }
                        getPresenter().data.setAddress(input.toString());
                        addressText.setText(input.toString());
                    }
                }).show();
    }

    public boolean requestPermission(){
        //判断当前Activity是否已经获得了该权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ||ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            //如果App的权限申请曾经被用户拒绝过，就需要在这里跟用户做出解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "please give me the permission", Toast.LENGTH_SHORT).show();
            } else {
                //进行权限请求
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA},
                        WritingPresenter.EXTERNAL_STORAGE_REQ_CODE);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case WritingPresenter.EXTERNAL_STORAGE_REQ_CODE: {
                // 如果请求被拒绝，那么通常grantResults数组为空
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    //申请成功，进行相应操作
                    getPresenter().editPicture();
                } else {
                    //申请失败，可以继续向用户解释。
                }
                return;
            }
        }
    }
}


