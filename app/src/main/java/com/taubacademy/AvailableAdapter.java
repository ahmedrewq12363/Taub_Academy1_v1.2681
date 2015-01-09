package com.taubacademy;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Created by Ahmed on 1/9/2015.
 */

public class AvailableAdapter extends BaseAdapter {
    String[] Avail;
    int global_items;
    WeakReference<Context> contextWeakReference;
    public AvailableAdapter(Context baseContext, String[] available) {
        contextWeakReference = new WeakReference<Context>(baseContext);
        Avail=available;
        global_items = Avail.length;
    }

    @Override
    public int getCount() {
        return global_items;
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
        if(view == null){
//                view is null
            LayoutInflater inflater = (LayoutInflater) contextWeakReference.get().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.avail,viewGroup,false);
            viewH = new ViewHolder();
            viewH.firstLine = (TextView)view.findViewById(R.id.AvailId);
            viewH.firstLine.setTextColor(Color.parseColor("#0099CC"));
//                viewH.icon = (ImageView) view.findViewById(R.id.icon11);
            view.setTag(viewH);
        }else {
            //view != null
            viewH = (ViewHolder) view.getTag();
        }
        //set curr view (the suitable indexes in the arrays).
        viewH.firstLine.setText(Avail[i%Avail.length]);
        return view;
    }
    class ViewHolder {
        TextView firstLine;
        int position;
    }
}