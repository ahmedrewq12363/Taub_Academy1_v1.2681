package com.taubacademy;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity implements communicator {
    Describtion DescFragment  = new Describtion();
    CoursesList CourFragment = new CoursesList();
    FragmentManager manager = getFragmentManager();



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        int counter = manager.getBackStackEntryCount();
        if(counter == 1)
        {
            this.finish();
        }
        manager.popBackStack();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentTransaction Transaction = manager.beginTransaction();
        setContentView(R.layout.activity_my);
        Transaction.add(R.id.CoursesFrag, CourFragment, "Courses");
        Transaction.add(R.id.DescFrag, DescFragment, "Describtions");
        Transaction.addToBackStack("Describtions And Courses");
        Transaction.commit();

    }


    @Override
    public void respond(String course) throws ParseException {
        DescFragment.changeData(course);
    }

    @Override
    public void ChangeFrag(String Name, String Email, String Rate, String Phone, ImageView image, String[] Feeds, String[] Byf, List<Course> Courses, String[] Avail) {
        Profile profile = new Profile(Name,Email,Phone,Rate,image,Avail,Feeds,Byf,Courses);
        FragmentTransaction Transaction = manager.beginTransaction();
        Transaction.remove(DescFragment);
        Transaction.remove(CourFragment);
        Transaction.add(R.id.ProfileFrag,profile,"profile");
        Transaction.addToBackStack("Profile");
        Transaction.commit();
    }


    public void SortByRank(View view)
    {
        RadioButton Salary = (RadioButton) findViewById(R.id.radioButton2);
        if(Salary.isChecked())
        {
            Salary.setChecked(false);
        }
        DescFragment.SortBy("Rank");
    }
    public void SortBySalary(View view)
    {
        RadioButton Rank = (RadioButton) findViewById(R.id.radioButton);
        if(Rank.isChecked())
        {
            Rank.setChecked(false);
        }
        DescFragment.SortBy("Salary");
    }

}
