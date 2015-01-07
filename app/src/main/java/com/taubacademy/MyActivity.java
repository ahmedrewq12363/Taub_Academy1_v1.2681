package com.taubacademy;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import java.util.ArrayList;


public class MyActivity extends Activity implements communicator {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        // Check whether the activity is using the layout version with
        // the fragment_container FrameLayout. If so, we must add the first fragment
    }

    @Override
    public void respond(String course) {
        FragmentManager f = getFragmentManager();
        Describtion dFragment = (Describtion) f.findFragmentById(R.id.fragment2);
        //ArrayList<Tutor> tutors = Course.getTutorsOnThisCourse(Integer.parseInt(course));
        dFragment.changeData(course);
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
