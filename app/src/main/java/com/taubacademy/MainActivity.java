package com.taubacademy;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.parse.ParseInstallation;


public class MainActivity extends Activity implements communicator {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

    @Override
    public void respond(String course) {
        FragmentManager f = getFragmentManager();
        Describtion dFragment = (Describtion) f.findFragmentById(R.id.fragment2);
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
