package com.jude.emotionshow.presentation.seed;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.data.BeamDataActivity;
import com.jude.emotionshow.R;
import com.jude.emotionshow.domain.entities.SeedDetail;
import com.jude.exgridview.PieceViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Jude on 2015/11/21.
 */
@RequiresPresenter(WritingPresenter.class)
public class WritingActivity extends BeamDataActivity<WritingPresenter,SeedDetail> {

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
        scopeContainer.setOnClickListener(v -> showScopeTypeEdit());
        processContainer.setOnClickListener(v -> showProcessEdit());
        sceneContainer.setOnClickListener(v -> showSceneEdit());
        //images.setOnAskViewListener(this::showSelectorDialog);
        images.setAddImageRes(R.drawable.pic_add);
        images.setOKImageRes(R.drawable.pic_ok);
        images.setOnViewDeleteListener(getPresenter());
    }

    @Override
    public void setData(SeedDetail data) {
        super.setData(data);
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

}


