package com.taubacademy;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.parse.ParseException;


public class MainActivity extends Activity implements communicator {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        try {
            Course.getTutorsOfCourse(234114);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void respond(String course) {
        FragmentManager f = getFragmentManager();
        Describtion dFragment = (Describtion) f.findFragmentById(R.id.fragment2);
        try {
            dFragment.changeData(course);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public void SortByRank(View view)
    {
        RadioButton Salary = (RadioButton) findViewById(R.id.radioButton2);
        if(Salary.isChecked())
        {
            Salary.setChecked(false);
        }
        FragmentManager f = getFragmentManager();
        Describtion dFragment = (Describtion) f.findFragmentById(R.id.fragment2);
        dFragment.SortBy("Rank");
    }
    public void SortBySalary(View view)
    {
        RadioButton Rank = (RadioButton) findViewById(R.id.radioButton);
        if(Rank.isChecked())
        {
            Rank.setChecked(false);
        }
        FragmentManager f = getFragmentManager();
        Describtion dFragment = (Describtion) f.findFragmentById(R.id.fragment2);
        dFragment.SortBy("Salary");
    }

}
