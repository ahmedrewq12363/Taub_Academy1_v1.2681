package com.taubacademy;

import android.widget.ImageView;

import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ziad on 12/26/2014.
 */
public interface communicator {
    public void respond(String i) throws ParseException;
    public void ChangeFrag(String Name , String Email , String Rate, String Phone , ImageView image,String[] Feeds,String[] Byf,List<Course> Courses,String[] Avail);
}
