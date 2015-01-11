package com.taubacademy;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends FragmentActivity implements communicator {
    Describtion DescFragment = new Describtion();
    CoursesList CourFragment = new CoursesList();
    FragmentManager manager = getFragmentManager();
    private MainFragment mainFragment;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
    }
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
                LogIn logIn = new LogIn();
                List<String> permissions = Arrays.asList("public_profile", "user_friends", "user_about_me",
                        "user_relationships", "user_birthday", "user_location");
                ParseFacebookUtils.logIn(permissions, this, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException err) {
                        if (user == null) {
                            Log.d("Ahmed",
                                    "Uh oh. The user cancelled the Facebook login.");
                        } else if (user.isNew()) {

                            try {
                                user.signUp();
                                user.save();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Log.d("Ahmed",
                                    "User signed up and logged in through Facebook!");
                            //showUserDetailsActivity();
                        } else {
                            try {
                                user.signUp();
                                user.save();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Log.d("Ahmed",
                                    "User logged in through Facebook!");
                            //showUserDetailsActivity();
                        }
                    }
                });
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
