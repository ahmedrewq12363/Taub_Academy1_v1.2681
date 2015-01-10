package com.taubacademy;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * Created by Ahmed on 1/9/2015.
 */
class FeedBacksAdapter extends BaseAdapter {
    String[] names;
    String[] feedbacks;
    WeakReference<Context> contextWeakReference;

    FeedBacksAdapter(Context c, Map<String, String> feddbacks) {
        super();
        contextWeakReference = new WeakReference<Context>(c);
        this.names = (String[]) feddbacks.keySet().toArray();
        this.feedbacks = (String[]) feddbacks.values().toArray();
    }

    @Override
    public int getCount() {
        return feedbacks.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewH = null;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) contextWeakReference.get().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.feed, viewGroup, false);
            viewH = new ViewHolder();
            viewH.firstLine = (TextView) view.findViewById(R.id.Feedback);
            viewH.secondLine = (TextView) view.findViewById(R.id.By);
            viewH.firstLine.setTextColor(Color.parseColor("#0099CC"));
            viewH.secondLine.setTextColor(Color.parseColor("#0099CC"));
            view.setTag(viewH);
        } else {
            viewH = (ViewHolder) view.getTag();
        }
        viewH.firstLine.setText(feedbacks[i]);
        viewH.secondLine.setText("By " + names[i]);
        return view;
    }


    class ViewHolder {
        TextView firstLine;
        TextView secondLine;
    }
}