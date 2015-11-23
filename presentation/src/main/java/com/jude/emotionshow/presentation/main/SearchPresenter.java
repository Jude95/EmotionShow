package com.jude.emotionshow.presentation.main;

import com.jude.beam.bijection.Presenter;

/**
 * Created by zhuchenxi on 15/11/23.
 */
public class SearchPresenter extends Presenter<SearchActivity> {
    public interface OnSearchAsked{
        void search(String query);
    }
}
