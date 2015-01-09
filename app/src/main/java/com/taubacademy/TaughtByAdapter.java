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
public class TaughtByAdapter extends BaseAdapter {
    List<Course> courses;
    WeakReference<Context> contextWeakReference;
    TaughtByAdapter(Context c, List<Course> courses){
        contextWeakReference = new WeakReference<Context>(c);
        this.courses = courses;
    }
    @Override
    public int getCount() {
        return courses.size();
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
            LayoutInflater inflater = (LayoutInflater) contextWeakReference.get().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.taught_by,viewGroup,false);
            viewH = new ViewHolder();
            viewH.firstLine = (TextView)view.findViewById(R.id.CourseName);
            viewH.secondLine=(TextView)view.findViewById(R.id.CourseNum);
            viewH.firstLine.setTextColor(Color.parseColor("#0099CC"));
            viewH.secondLine.setTextColor(Color.parseColor("#0099CC"));
            view.setTag(viewH);
        }else {

            viewH = (ViewHolder) view.getTag();
        }
        viewH.firstLine.setText(courses.get(i).getName());
        viewH.secondLine.setText(courses.get(i).getCourseId().toString());
        return view;
    }


    class ViewHolder {
        TextView firstLine;
        TextView secondLine;
    }
}
