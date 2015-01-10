package com.taubacademy;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ClipData;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;

import com.parse.ParseException;


public class MainActivity extends FragmentActivity implements communicator {
    Describtion DescFragment = new Describtion();
    CoursesList CourFragment = new CoursesList();
    FragmentManager manager = getFragmentManager();



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

            // Inflate the menu items for use in the action bar
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.action_bar, menu);
            return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.Login_button:
                LogIn loginpage = new LogIn();
                FragmentTransaction Transaction = manager.beginTransaction();
                Transaction.add(R.id.ProfileFrag, loginpage, "login");
                Transaction.addToBackStack("login");
                Transaction.commit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        int counter = manager.getBackStackEntryCount();
        if (counter == 1) {
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
//        Tutor.updateAlTutorials();
    }


     AdapterView.OnItemClickListener onLoginClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

        }
    };
    @Override
    public void respond(String course) throws ParseException {
        DescFragment.changeData(course);
    }

    @Override
    public void ChangeFrag(Tutor tutor) {
        Profile profile = new Profile(tutor);
        FragmentTransaction Transaction = manager.beginTransaction();
        Transaction.add(R.id.ProfileFrag, profile, "profile");
        Transaction.addToBackStack("Profile");
        Transaction.commit();
    }


    public void SortByRank(View view) {
        RadioButton Salary = (RadioButton) findViewById(R.id.radioButton2);
        if (Salary.isChecked()) {
            Salary.setChecked(false);
        }
        DescFragment.SortBy("Rank");
    }

    public void SortBySalary(View view) {
        RadioButton Rank = (RadioButton) findViewById(R.id.radioButton);
        if (Rank.isChecked()) {
            Rank.setChecked(false);
        }
        DescFragment.SortBy("Salary");
    }

}
