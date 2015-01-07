package com.taubacademy;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.SearchView;
import android.widget.TextView;

/**
 * Created by ziad on 1/2/2015.
 */
public class SearchBar extends android.widget.SearchView implements SearchView.OnQueryTextListener ,SearchView.OnCloseListener {
    CoursesList getActivity;
    public SearchBar(Context context) {
        super(context);
        this.setOnCloseListener(this);
        setIconifiedByDefault(true);
    }

    public void setActivity(CoursesList activity)
    {
        getActivity = activity;
    }
    public SearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        getActivity.search(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        getActivity.search(query);
        return false;
    }

    @Override
    public boolean onClose() {
        getActivity.restoreListView();
        return false;
    }
}
