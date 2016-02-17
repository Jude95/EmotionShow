package com.jude.emotionshow.presentation.seed;

import android.os.Bundle;
import android.text.TextUtils;

import com.jude.beam.expansion.data.BeamDataActivityPresenter;
import com.jude.emotionshow.data.model.SeedModel;
import com.jude.emotionshow.data.server.ServiceResponse;
import com.jude.emotionshow.domain.entities.SeedDetail;
import com.jude.utils.JUtils;
import com.umeng.share.ShareManager;

import rx.Subscriber;

/**
 * Created by Mr.Jude on 2015/11/21.
 */
public class SeedDetailPresenter extends BeamDataActivityPresenter<SeedDetailActivity, SeedDetail> {
    public int id;
    public SeedDetail mData;

    @Override
    protected void onCreate(SeedDetailActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        id = getView().getIntent().getIntExtra("id", -1);
        if (id == -1) {
            getView().finish();
            JUtils.Toast("对不起，该印记已被删除");
        } else
            refresh();
    }

    private void refresh() {
        SeedModel.getInstance().getSeedDetail(id)
                .doOnNext(data -> mData = data)
                .unsafeSubscribe(new Subscriber<SeedDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SeedDetail seedDetail) {
                        publishObject(seedDetail);
                    }
                });
    }

    public void comment(int commentId, String content) {
        if (TextUtils.isEmpty(content)) {
            JUtils.Toast("请填写内容");
            return;
        }
        getView().getExpansion().showProgressDialog("提交中");
        SeedModel.getInstance().comment(id, commentId, content)
                .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                .subscribe(new ServiceResponse<Object>() {
                    @Override
                    public void onNext(Object o) {
                        super.onNext(o);
                        JUtils.Toast("评论成功");
                        refresh();
                    }
                });
    }

    public void praise() {
        getView().getExpansion().showProgressDialog("点赞中");
        SeedModel.getInstance().praise(id)
                .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                .subscribe(new ServiceResponse<Object>() {
                    @Override
                    public void onNext(Object o) {
                        JUtils.Toast("您赞了这条印记");
                        refresh();
                    }
                });
    }

    public void collect() {
        getView().getExpansion().showProgressDialog("收藏中");
        SeedModel.getInstance().collect(id)
                .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                .subscribe(new ServiceResponse<Object>() {
                    @Override
                    public void onNext(Object o) {
                        getView().updateCollect(id);
                        JUtils.Toast("您收藏了这条印记");
                    }
                });
    }

    public void report() {
        SeedModel.getInstance().report(id).subscribe(new ServiceResponse<Object>() {
            @Override
            public void onNext(Object o) {
                JUtils.Toast("举报成功");
            }
        });
    }

    public void delete() {
        SeedModel.getInstance().deleteSeed(id).subscribe(new ServiceResponse<Object>() {
            @Override
            public void onNext(Object o) {
                JUtils.Toast("删除成功");
                getView().finish();
            }
        });
    }

    public void share() {
        String content = mData.getContent();
        ShareManager.getInstance(getView()).share(getView(), content, "么么秀分享", "http://114.215.86.90/meme.php/home/index/share/" + mData.getId(), mData.getPics().get(0).getUrl());
    }


    public void unCollect() {
        getView().getExpansion().showProgressDialog("取消收藏");
        SeedModel.getInstance().unCollect(id)
                .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                .subscribe(new ServiceResponse<Object>() {
                    @Override
                    public void onNext(Object o) {
                        getView().updateCollect(-1);
                        JUtils.Toast("您取消收藏了这条印记");
                    }
                });
    }
}

