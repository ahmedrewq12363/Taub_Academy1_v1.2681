package com.taubacademy;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Ahmed on 1/9/2015.
 */

public class AvailableAdapter extends BaseAdapter {
    List<String> Avail;
    WeakReference<Context> contextWeakReference;

    public AvailableAdapter(Context baseContext, List<String> available) {
        contextWeakReference = new WeakReference<Context>(baseContext);
        Avail = available;
    }

    @Override
    public int getCount() {
        return Avail == null ? 0 :Avail.size();
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
//                view is null
            LayoutInflater inflater = (LayoutInflater) contextWeakReference.get().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.avail, viewGroup, false);
            viewH = new ViewHolder();
            viewH.firstLine = (TextView) view.findViewById(R.id.AvailId);
            viewH.firstLine.setTextColor(Color.parseColor("#0099CC"));
            view.setTag(viewH);
        } else {
            viewH = (ViewHolder) view.getTag();
        }
        viewH.firstLine.setText(Avail.get(i));
        return view;
    }

    class ViewHolder {
        TextView firstLine;
    }
}