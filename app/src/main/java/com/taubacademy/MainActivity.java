package com.taubacademy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MainActivity extends FragmentActivity implements communicator, Profile.ClickListner {
    public Set<Course> CoursesSelected;
    public Describtion DescFragment;
    public CoursesList CourFragment;
    public ListView lv;
    public AlertDialog.Builder alertDialog;
    public AlertDialog AddCourseDialog;
    public List<Model> list;
    public int[] days_arr;
    public Menu menu;
    boolean isTwoView;
    FragmentManager manager = getSupportFragmentManager();
    Profile profile;
    AdapterView.OnItemClickListener onLoginClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

        }
    };

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ((ParseUser.getCurrentUser() == null || (!ParseFacebookUtils.isLinked(ParseUser.getCurrentUser()))) && this.menu != null) {
            MenuItem Login = menu.findItem(R.id.Login_button);
            Login.setIcon(R.drawable.login_64);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        if (ParseUser.getCurrentUser() == null || (!ParseFacebookUtils.isLinked(ParseUser.getCurrentUser()))) {
            MenuItem Login = (MenuItem) menu.findItem(R.id.Login_button);
            Login.setIcon(R.drawable.login_64);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final MenuItem menuItem = item;
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.HomeButton:
                FragmentTransaction Transaction = manager.beginTransaction();
                if (getSupportFragmentManager().getFragments() == null) {
                    return true;
                } else if (getSupportFragmentManager().getFragments().size() == 0) {
                    return true;
                }

                int count = manager.getBackStackEntryCount();
                for (int i = 0; i < count; ++i) {
                    manager.popBackStack();
                }
                break;
            case R.id.Login_button:
                if (!isOnline()) {
                    Log.w("alaa", "not connected to internet");
                    Toast.makeText(this, "Please Check Your Internet Connection !", Toast.LENGTH_LONG).show();
                    return true;
                }
                if (ParseUser.getCurrentUser() != null && ParseFacebookUtils.isLinked(ParseUser.getCurrentUser())) {
                    new AsyncTask<Void, Void, Profile>() {
                        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

                        @Override
                        protected void onPreExecute() {
                            Tutor t = ((Tutor)ParseUser.getCurrentUser().get("Tutor"));
                            try {
                                t.fetch();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            super.onPreExecute();
                            progressDialog.setMessage("loading " +t.getName() + " Page please wait...");
                            progressDialog.show();
                        }

                        @Override
                        protected Profile doInBackground(Void... voids) {
                            MainActivity.this.profile =  new Profile((Tutor) ParseUser.getCurrentUser().get("Tutor"));
                            Profile myProfile = null;
                            FragmentTransaction Transaction = manager.beginTransaction();
                            Fragment fragment = MainActivity.this.getSupportFragmentManager().findFragmentById(R.id.main_continer);
                            if (!(fragment instanceof Profile)) {
                                Transaction = manager.beginTransaction();
                                Transaction.setCustomAnimations(R.anim.animated_fragment, R.anim.animated_fragment2);

                                Transaction.replace(R.id.main_continer, profile, "Profile");
                                Transaction.addToBackStack(null);
                                Transaction.commit();
                            } else {
                                myProfile = (Profile) fragment;
                                if (!myProfile.tutor.equals(profile.tutor)) {
                                    Transaction = manager.beginTransaction();
                                    Transaction.setCustomAnimations(R.anim.animated_fragment, R.anim.animated_fragment2);
                                    Transaction.replace(R.id.main_continer, profile, "Profile");
                                    Transaction.addToBackStack(null);
                                    Transaction.commit();
                                }
                            }
                            return MainActivity.this.profile;
                        }

                        @Override
                        protected void onPostExecute(Profile profile) {
                            super.onPostExecute(profile);
                            progressDialog.dismiss();
                        }
                    }.execute();

                    return true;
                }
                List<String> permissions = Arrays.asList("public_profile", "email", "user_mobile_phone");
                ParseFacebookUtils.logIn(permissions, this, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException err) {
                        if (user == null) {
                            Toast.makeText(getBaseContext(), "There is Error in The LogIn", Toast.LENGTH_LONG);
                        } else if (user.isNew()) {
                            Tutor.createNewTutor(MainActivity.this);
                            menuItem.setIcon(R.drawable.user_100_blue);
                        } else {
                            menuItem.setIcon(R.drawable.user_100_blue);
                        }
                    }
                });

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ParseUser.getCurrentUser().fetchInBackground();

        DescFragment = (Describtion) getSupportFragmentManager().findFragmentById(R.id.descripion_frag);

        CourFragment = (CoursesList) getSupportFragmentManager().findFragmentById(R.id.courses_frag);
        if (DescFragment == null) {
            isTwoView = false;
        } else isTwoView = true;
        CoursesSelected = new HashSet<Course>();
        days_arr = new int[7];
    }

    @Override
    public void respond(String course) throws ParseException {
        if (!isTwoView) {
            DescFragment = new Describtion();
            Bundle bundle = new Bundle();
            bundle.putString("COURSE", course);
            DescFragment.setArguments(bundle);
            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.main_continer, DescFragment)
                    .setCustomAnimations(R.anim.animated_fragment, R.anim.animated_fragment2, R.anim.animated_fragment, R.anim.animated_fragment2)
                    .addToBackStack(null).commit();
        }
        if (DescFragment != null && isTwoView)
            DescFragment.changeData(course);
    }

    @Override
    public void ChangeFrag(final Tutor tutor) {
        new AsyncTask<Void, Void, Profile>() {
            ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.setMessage("loading " +tutor.getName() + " Page please wait...");
                progressDialog.show();
            }

            @Override
            protected Profile doInBackground(Void... voids) {
                try {
                    tutor.fetch();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                MainActivity.this.profile = new Profile(tutor);
                return MainActivity.this.profile;
            }

            @Override
            protected void onPostExecute(Profile profile) {
                super.onPostExecute(profile);
                progressDialog.dismiss();
                FragmentTransaction Transaction = manager.beginTransaction();
                Transaction.setCustomAnimations(R.anim.animated_fragment, R.anim.animated_fragment2, R.anim.animated_fragment, R.anim.animated_fragment2);
                Transaction.replace(R.id.main_continer, profile, "Profile");
                Transaction.addToBackStack(null);
                Transaction.commit();
            }
        }.execute();


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

    public void onSendMailClick(View v) {
        final Context context = getApplicationContext();
        sendEmail(context, new String[]{profile.tutor.getEmail()}, "Sending Email",
                "Taub Academy Application Mail", "Hi " + profile.tutor.getName() + "," +
                        "\n I need your help on: " +
                        "\n CoureNumber: " +
                        "\n Day: " +
                        "\n Time: " +
                        "\n Thanks. ");
    }

    public void onPhoneClick(View v) {
        call(profile.tutor.getPhone());

    }

    public void call(String phone) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phone));
        startActivity(callIntent);
    }

    public void onCoursesButtonClick(View v) {
        Log.w("alaa", "clicked");
        alertDialog = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.custom, null);
        SearchView search = (SearchView) convertView.findViewById(R.id.searchView2);
        SearchCourses SearchStrings = new SearchCourses(MainActivity.this.getBaseContext());
        search.setSubmitButtonEnabled(true);
        search.setOnQueryTextListener(SearchStrings);
        alertDialog.setView(convertView);
        alertDialog.setTitle("Choose a Course");
        lv = (ListView) convertView.findViewById(R.id.listView1);
        String[] names;
        String[] numbers;
        Resources res = getResources();
        numbers = res.getStringArray(R.array.CoursesNumber);
        names = res.getStringArray(R.array.CoursesNames);
        list = getModel(numbers, names);
        lv.setAdapter(new StableArrayAdapter(getBaseContext(), names, numbers, getModel(numbers, names)));
        AddCourseDialog = alertDialog.create();
        AddCourseDialog.show();
    }

    public void onAddCoursesClicked(View v) {
        Log.w("alaa", "on add courses clicked");
        AddCourseDialog.dismiss();
    }

    public void onDayClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.Sunday_checkbox:
                if (checked)
                    days_arr[0] = 1;
                else
                    days_arr[0] = 0;
                break;
            case R.id.Monday_checkbox:
                if (checked)
                    days_arr[1] = 1;
                else
                    days_arr[1] = 0;
                break;
            case R.id.Tuesday_checkbox:
                if (checked)
                    days_arr[2] = 1;
                else
                    days_arr[2] = 0;
                break;
            case R.id.Wednesday_checkbox:
                if (checked)
                    days_arr[3] = 1;
                else
                    days_arr[3] = 0;
                break;
            case R.id.Thursday_checkbox:
                if (checked)
                    days_arr[4] = 1;
                else
                    days_arr[4] = 0;
                break;
            case R.id.Friday_checkbox:
                if (checked)
                    days_arr[5] = 1;
                else
                    days_arr[5] = 0;
                break;
            case R.id.Saturday_checkbox:
                if (checked)
                    days_arr[6] = 1;
                else
                    days_arr[6] = 0;
                break;
        }
    }

    public void onSubmitButtonClick(View v) {
        Log.w("alaa", "on submit");
        final Tutor t = (Tutor) ParseUser.getCurrentUser().get("Tutor");
        EditText name = (EditText) findViewById(R.id.name_edit);
        EditText phone = (EditText) findViewById(R.id.phone_edit);
        EditText email = (EditText) findViewById(R.id.email_edit);
        EditText salary = (EditText) findViewById(R.id.salaryEditText);
        for (Course c : CoursesSelected) {
            Log.w("alaa", c.getName() + " " + c.getCourseId());
        }
        ArrayList<String> Avalibale = new ArrayList<String>();
        TextView view_start;
        TextView view_end;
        CheckBox box;
        for (int i = 0; i < 7; i++) {
            switch (i) {
                case 0:
                    box = (CheckBox) findViewById(R.id.Sunday_checkbox);
                    if(box.isChecked()) {
                        view_start = (TextView) findViewById(R.id.Sunday_start_time);
                        view_end = (TextView) findViewById(R.id.Sunday_end_time);
                        Avalibale.add("Sunday " + view_start.getText() + "-" + view_end.getText());
                    }
                    break;
                case 1:
                    box = (CheckBox) findViewById(R.id.Monday_checkbox);
                    if(box.isChecked()) {
                        view_start = (TextView) findViewById(R.id.Monday_start_time);
                        view_end = (TextView) findViewById(R.id.Monday_end_time);
                        Avalibale.add("Monday " + view_start.getText() + "-" + view_end.getText());
                        Log.w("alaa", "Monday " + view_start.getText() + "-" + view_end.getText());
                    }
                    break;
                case 2:
                    box = (CheckBox) findViewById(R.id.Tuesday_checkbox);
                    if(box.isChecked()) {
                        view_start = (TextView) findViewById(R.id.Tuesday_start_time);
                        view_end = (TextView) findViewById(R.id.Tuesday_end_time);
                        Avalibale.add("Tuesday " + view_start.getText() + "-" + view_end.getText());
                        Log.w("alaa", "Sunday " + view_start.getText() + "-" + view_end.getText());
                    }
                    break;
                case 3:
                    box = (CheckBox) findViewById(R.id.Wednesday_checkbox);
                    if(box.isChecked()) {
                        view_start = (TextView) findViewById(R.id.Wednesday_start_time);
                        view_end = (TextView) findViewById(R.id.Wednesday_end_time);
                        Avalibale.add("Wednesday " + view_start.getText() + "-" + view_end.getText());
                        Log.w("alaa", "Sunday " + view_start.getText() + "-" + view_end.getText());
                    }
                    break;
                case 4:
                    box = (CheckBox) findViewById(R.id.Thursday_checkbox);
                    if(box.isChecked()) {
                        view_start = (TextView) findViewById(R.id.Thursday_start_time);
                        view_end = (TextView) findViewById(R.id.Thursday_end_time);
                        Avalibale.add("Thursday " + view_start.getText() + "-" + view_end.getText());
                        Log.w("alaa", "Thursday " + view_start.getText() + "-" + view_end.getText());
                    }
                    break;
                case 5:
                    box = (CheckBox) findViewById(R.id.Friday_checkbox);
                    if(box.isChecked()) {
                        view_start = (TextView) findViewById(R.id.Friday_start_time);
                        view_end = (TextView) findViewById(R.id.Friday_end_time);
                        Avalibale.add("Friday " + view_start.getText() + "-" + view_end.getText());
                        Log.w("alaa", "Friday " + view_start.getText() + "-" + view_end.getText());
                    }
                    break;
                case 6:
                    box = (CheckBox) findViewById(R.id.Saturday_checkbox);
                    if(box.isChecked()) {
                        view_start = (TextView) findViewById(R.id.Saturday_start_time);
                        view_end = (TextView) findViewById(R.id.Saturday_end_time);
                        Avalibale.add("Saturday " + view_start.getText() + "-" + view_end.getText());
                        Log.w("alaa", "Saturday " + view_start.getText() + "-" + view_end.getText());
                    }
                    break;
            }
        }
        t.setAvailableTime(Avalibale);
        Log.w("alaa", "name " + name.getText().toString() + " email " + email.getText().toString() + " phone " + phone.getText().toString());
        t.setName(name.getText().toString());
        t.setEmail(email.getText().toString());
        t.setPhone(phone.getText().toString());
        t.setSalary(Integer.parseInt(salary.getText().toString()));
        t.saveInBackground();
        ArrayList<Integer> coures_number = new ArrayList<Integer>();
        if (list != null)
            for (Model m : list) {
                if (m.isSelected()) {
                    Log.w("alaa", "course number " + m.getName() + " course name " + m.getNumber());
                    coures_number.add(m.getNumber());
                }
            }
        ParseQuery query = ParseQuery.getQuery("Course");
        query.whereContainedIn("CourseId", coures_number);
        query.findInBackground(new FindCallback() {
            @Override
            public void done(List list, ParseException e) {
                List<Course> oldCoursesList = t.getAllCourses() == null ? new ArrayList<Course>(): t.getAllCourses();
                ArrayList<Tutor> tutors = new ArrayList<Tutor>();
                tutors.add(t);
                for(Course c : oldCoursesList)
                {
                    try {
                        c.fetchIfNeeded();
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    c.removeAll("tutors", tutors);
                    c.saveInBackground();
                }
                List<Course> courses = (List<Course>) list;
                for (Course course : courses) {
                    course.addTutorToList(t);
                    course.saveInBackground();
                }
                t.put("Courses", courses);
                try {
                    t.save();
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }
        });
        new AsyncTask<Void, Void, Profile>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Profile doInBackground(Void... voids) {
                if(MainActivity.this.profile == null)
                {
                    profile = new Profile(((Tutor)ParseUser.getCurrentUser().get("Tutor")));
                }
                try {
                    MainActivity.this.profile.tutor.fetch();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return MainActivity.this.profile;
            }

            @Override
            protected void onPostExecute(Profile profile) {
                super.onPostExecute(profile);
                MainActivity.this.profile.Refresh();
                manager.popBackStackImmediate();
                FragmentTransaction Transaction = manager.beginTransaction();
                Transaction.setCustomAnimations(R.anim.animated_fragment, R.anim.animated_fragment2);

                Transaction.replace(R.id.main_continer, profile, "Profile");
                if(manager.getBackStackEntryCount() ==0)
                {
                    Transaction.addToBackStack("Profile");
                }
                Transaction.commit();
            }
        }.execute();

    }

    private List<Model> getModel(String[] Courses, String[] names) {
        Tutor t = (Tutor) ParseUser.getCurrentUser().get("Tutor");
        List<Course> mycourses = t.getAllCourses() == null ? new ArrayList<Course>() : t.getAllCourses();
        Set<Integer> CoursesNumber = new HashSet<Integer>();
        for (Course c : mycourses) {
            CoursesNumber.add(c.getCourseId());
        }
        List<Model> list = new ArrayList<Model>();
        int i = 0;
        for (String course : Courses) {
            list.add(new Model(Integer.parseInt(course), names[i]));
            if (CoursesNumber.contains(Integer.parseInt(course))) {
                Log.w("alaa", "has Course" + Integer.parseInt(course));
                list.get(i).setSelected(true);
            }
            i++;
        }
        return list;
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment((TextView) v);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @Override
    public void logOut() {


        MenuItem item = menu.findItem(R.id.Login_button);
        item.setIcon(R.drawable.login_64);
        int count = manager.getBackStackEntryCount();
        for (int i = 0; i < count; ++i) {
            manager.popBackStack();
        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {
        TextView v;

        public TimePickerFragment() {

        }

        public TimePickerFragment(TextView view) {
            v = view;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            v.setText(hourOfDay + ":" + minute);
        }
    }

    private class StableArrayAdapter extends BaseAdapter {
        String[] Courses;
        String[] Names;
        WeakReference<Context> contextWeakReference;


        StableArrayAdapter(Context c, String[] courses_names, String[] courses_nums, List<Model> list) {
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

            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) contextWeakReference.get().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.pick_course_item, viewGroup, false);
                final ViewHolder viewH = new ViewHolder();
                final int position = i;
                viewH.firstLine = (TextView) view.findViewById(R.id.course_name_pick);
                viewH.secondLine = (TextView) view.findViewById(R.id.course_number_pick);
                viewH.box = (CheckBox) view.findViewById(R.id.checkBox);
                viewH.box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        Model element = (Model) viewH.box.getTag();
                        element.setSelected(compoundButton.isChecked());
                        Course course = new Course();
                        course.setCourseId(Integer.parseInt(Courses[position % (Courses.length)]));
                        course.setName(Names[position % (Names.length)]);
                        if (b) {
                            CoursesSelected.add(course);
                        } else {
                            CoursesSelected.remove(course);
                        }
                    }
                });
                viewH.firstLine.setTextColor(Color.parseColor("#0099CC"));
                viewH.secondLine.setTextColor(Color.parseColor("#0099CC"));
                viewH.box.setTag(list.get(i));
                view.setTag(viewH);
            } else {
                ((ViewHolder) view.getTag()).box.setTag(list.get(i));
            }

            ViewHolder viewH = (ViewHolder) view.getTag();
            viewH.firstLine.setText(Names[i % Names.length]);
            viewH.secondLine.setText(Courses[i % (Courses.length)]);
            viewH.box.setChecked(list.get(i).isSelected());
            return view;
        }


        class ViewHolder {
            TextView firstLine;
            TextView secondLine;
            CheckBox box;
        }
    }

    public class Model {

        private int course_number;
        private String course_name;
        private boolean selected;

        public Model(int number, String name) {
            this.course_number = number;
            this.course_name = name;
            selected = false;
        }

        public int getNumber() {
            return course_number;
        }

        public String getName() {
            return course_name;
        }

        public void setCourse_number(int number) {
            this.course_number = number;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

    }

    class SearchCourses extends android.widget.SearchView implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {

        public SearchCourses(Context context) {
            super(context);
            this.setOnCloseListener(this);
            setIconifiedByDefault(true);
        }


        @Override
        public boolean onQueryTextSubmit(String query) {
            search(query);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String query) {
            search(query);
            return false;
        }

        @Override
        public boolean onClose() {
            restoreListView();
            return false;
        }

        public void search(String query) {
            String[] names;
            String[] numbers;
            Resources res = getResources();
            numbers = res.getStringArray(R.array.CoursesNumber);
            names = res.getStringArray(R.array.CoursesNames);
            if (query == "" || query == null) {
                lv.setAdapter(new StableArrayAdapter(getBaseContext(), names, numbers, getModel(numbers, names)));
                return;
            }
            ArrayList<String> query_result_names = new ArrayList<String>();
            ArrayList<String> query_result_numbers = new ArrayList<String>();
            int k = 0;
            for (int i = 0; i < numbers.length; ++i) {
                if (numbers[i].toLowerCase().contains(query.toLowerCase()) || numbers[i].toLowerCase().contains(query.toLowerCase())) {
                    query_result_names.add(k, names[i]);
                    query_result_numbers.add(k, numbers[i]);
                    k++;
                }
            }

            String[] searched_names = new String[k];
            String[] searched_numbers = new String[k];
            for (int i = 0; i < k; ++i) {
                searched_names[i] = query_result_names.get(i);
                searched_numbers[i] = query_result_numbers.get(i);
            }
            lv.setAdapter(new StableArrayAdapter(getBaseContext(), searched_names, searched_numbers, getModel(searched_numbers, searched_names)));

        }

        public void restoreListView() {
            String[] names;
            String[] numbers;
            Resources res = getResources();
            numbers = res.getStringArray(R.array.CoursesNumber);
            names = res.getStringArray(R.array.CoursesNames);
            lv.setAdapter(new StableArrayAdapter(getBaseContext(), names, numbers, getModel(numbers, names)));
        }
    }

}

