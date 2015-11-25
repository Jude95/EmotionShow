package com.jude.emotionshow.presentation.setting;

import com.jude.beam.bijection.Presenter;
import com.umeng.share.ShareManager;

/**
 * Created by Mr.Jude on 2015/11/20.
 */
public class AboutPresenter extends Presenter<AboutActivity>{
    public void share(){
        String content = "么么秀是你最好的选择";
        ShareManager.getInstance(getView()).share(getView(), content, "么么秀推荐", "http://baidu.com", "http://7xnrrg.com2.z0.glb.qiniucdn.com/logo72.png");
    }
}
