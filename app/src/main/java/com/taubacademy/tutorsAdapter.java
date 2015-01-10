package com.taubacademy;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseFile;
import com.parse.ParseImageView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Ahmed on 1/9/2015.
 */
public class tutorsAdapter extends BaseAdapter {
    WeakReference<Context> contextWeakReference;
    ArrayList<Tutor> tutors;

    tutorsAdapter(Context c, ArrayList<Tutor> tutors) {

        contextWeakReference = new WeakReference<Context>(c);
        this.tutors = tutors;
    }

    @Override
    public int getCount() {
        return tutors.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = View.inflate(contextWeakReference.get(), R.layout.teacher_item, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.firstLine = (TextView) view.findViewById(R.id.firstLine1);
            viewHolder.secondLine = (TextView) view.findViewById(R.id.secondLine1);
            viewHolder.icon = (ImageView) view.findViewById(R.id.icon11);
            viewHolder.thirdLine = (TextView) view.findViewById(R.id.thirdLine);
            view.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        ParseImageView todoImage = (ParseImageView) holder.icon;
        ParseFile imageFile = tutors.get(i).getPhotoFile();
        if (imageFile != null) {
            todoImage.setParseFile(imageFile);
            todoImage.loadInBackground();
        }
        holder.firstLine.setText(tutors.get(i).getName());
        holder.secondLine.setText("Rank : " + tutors.get(i).getRating().toString() + "/5");
        holder.thirdLine.setText("Salary : " + tutors.get(i).getSalary().toString() + " nis");
        holder.firstLine.setTextColor(Color.parseColor("#0099CC"));
        holder.secondLine.setTextColor(Color.parseColor("#0099CC"));
        holder.thirdLine.setTextColor(Color.parseColor("#0099CC"));
        return view;
    }

    class ViewHolder {
        TextView firstLine;
        TextView secondLine;
        TextView thirdLine;
        ImageView icon;
    }
}