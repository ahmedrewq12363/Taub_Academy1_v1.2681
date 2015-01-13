package com.taubacademy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends FragmentActivity implements communicator {
    Describtion DescFragment = new Describtion();
    CoursesList CourFragment = new CoursesList();
    FragmentManager manager = getSupportFragmentManager();
    Profile profile;
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
//                if(ParseUser.getCurrentUser() != null && ParseFacebookUtils.isLinked(ParseUser.getCurrentUser()))
//                {
//                    profile = new Profile((Tutor)ParseUser.getCurrentUser().get("Tutor"));
//                    FragmentTransaction Transaction = manager.beginTransaction();
//                    Transaction.replace(R.id.ProfileFrag, profile,null);
//                    Transaction.addToBackStack(null);
//                    Transaction.commit();
//                    return true;
//                }
//                List<String> permissions = Arrays.asList("public_profile", "email", "user_mobile_phone");
//                ParseFacebookUtils.logIn(permissions, this, new LogInCallback() {
//                    @Override
//                    public void done(ParseUser user, ParseException err) {
//                        if (user == null) {
//                        } else if (user.isNew()) {
//                            Tutor.createNewTutor();
//                            FragmentTransaction Transaction = manager.beginTransaction();
//                            Transaction.replace(R.id.ProfileFrag, new MyProfileFragment(),null);
//                            Transaction.addToBackStack(null);
//                            Transaction.commit();
//                        } else {
//
//                        }
//                    }
//                });
                FragmentTransaction Transaction = manager.beginTransaction();
                Transaction.add(R.id.ProfileFrag, new MyProfileFragment(), null);
                Transaction.addToBackStack(null);
                Transaction.commit();
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
        ParseUser.getCurrentUser().fetchInBackground();
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
        profile = new Profile(tutor);
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
    public void onSendMailClick(View v){
        final Context context = getApplicationContext();
        sendEmail(context, new String[]{profile.tutor.getEmail()}, "Sending Email",
                "Test Email", "I am body");
    }
    public static void sendEmail(Context context, String[] recipientList,
                                 String title, String subject, String body) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, recipientList);
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent.createChooser(emailIntent, title);
        context.startActivity(emailIntent);
    }
    public void onPhoneClick(View v){
        call(profile.tutor.getPhone());

    }
    public void call(String phone) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phone));
        startActivity(callIntent);
    }
    public void onCoursesButtonClick(View v){
        Log.w("alaa", "clicked");
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.custom, null);
        alertDialog.setView(convertView);
        alertDialog.setTitle("List");
        ListView lv = (ListView) convertView.findViewById(R.id.listView1);
        String[] names ;
        String[] numbers ;
        Resources res = getResources();
        numbers = res.getStringArray(R.array.CoursesNumber);
        names= res.getStringArray(R.array.CoursesNames);
        lv.setAdapter(new StableArrayAdapter(getBaseContext(), names, numbers));
        alertDialog.show();
    }

    private class StableArrayAdapter extends BaseAdapter {
        String[] Courses;
        String[] Names;
        WeakReference<Context> contextWeakReference;

        StableArrayAdapter(Context c, String[] courses_names, String[] courses_nums) {
            contextWeakReference = new WeakReference<Context>(c);
            Courses = courses_nums;
            Names = courses_names;
        }

        @Override
        public int getCount() {
            return Courses.length;
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
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) contextWeakReference.get().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.pick_course_item, viewGroup, false);
                viewH = new ViewHolder();
                viewH.firstLine = (TextView) view.findViewById(R.id.course_name_pick);
                viewH.secondLine = (TextView) view.findViewById(R.id.course_number_pick);
                viewH.firstLine.setTextColor(Color.parseColor("#0099CC"));
                viewH.secondLine.setTextColor(Color.parseColor("#0099CC"));
                view.setTag(viewH);
            } else {
                viewH = (ViewHolder) view.getTag();
            }
            viewH.firstLine.setText(Names[i % Names.length]);
            viewH.secondLine.setText(Courses[i % (Courses.length)]);
            return view;
        }


        class ViewHolder {
            TextView firstLine;
            TextView secondLine;
        }



    }
}
