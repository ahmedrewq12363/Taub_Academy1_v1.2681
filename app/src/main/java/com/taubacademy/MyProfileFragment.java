package com.taubacademy;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Tutor t;

    public MyProfileFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyProfileFragment newInstance(String param1, String param2) {
        MyProfileFragment fragment = new MyProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        Tutor t = (Tutor) ParseUser.getCurrentUser().get("Tutor");
        try {
            t.fetchIfNeeded();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ((EditText) view.findViewById(R.id.name_edit)).setText(t.getName());
        ((EditText) view.findViewById(R.id.email_edit)).setText(t.getEmail());
        ((EditText) view.findViewById(R.id.phone_edit)).setText(t.getPhone());
        ((EditText)view.findViewById(R.id.salaryEditText)).setText(t.getSalary().toString());
        List<String> avalibale= t.getAvailableTime() == null ? new ArrayList<String>() : t.getAvailableTime();
        for(String str: avalibale) {
            StringTokenizer tokens = new StringTokenizer(str, " ");
            String day = tokens.nextToken();
            StringTokenizer times = new StringTokenizer(tokens.nextToken(),"-");
            String start=times.nextToken();
            String end = times.nextToken();
            TextView view_start;
            TextView view_end;
            CheckBox check;
            int day_n = 0;
            if(day.equals("Sunday")) day_n=1;
            if(day.equals("Monday")) day_n=2;
            if(day.equals("Tuesday")) day_n=3;
            if(day.equals("Wednesday")) day_n=4;
            if(day.equals("Thursday")) day_n=5;
            if(day.equals("Friday")) day_n=6;
            if(day.equals("Saturday")) day_n=7;
            switch (day_n){
                case 1:
                    view_start = (TextView) view.findViewById(R.id.Sunday_start_time);
                    view_end = (TextView) view.findViewById(R.id.Sunday_end_time);
                    check = (CheckBox)view.findViewById(R.id.Sunday_checkbox);
                    check.setChecked(true);
                    view_start.setText(start);
                    view_end.setText(end);
                    Log.w("alaa", "Sunday " + view_start.getText() + "-" + view_end.getText());
                    break;
                case 2:
                    view_start = (TextView) view.findViewById(R.id.Monday_start_time);
                    view_end = (TextView) view.findViewById(R.id.Monday_end_time);
                    check = (CheckBox)view.findViewById(R.id.Monday_checkbox);
                    check.setChecked(true);
                    view_start.setText(start);
                    view_end.setText(end);
                    Log.w("alaa", "Monday " + view_start.getText() + "-" + view_end.getText());
                    break;
                case 3:
                    view_start = (TextView) view.findViewById(R.id.Tuesday_start_time);
                    view_end = (TextView) view.findViewById(R.id.Tuesday_end_time);
                    check = (CheckBox)view.findViewById(R.id.Tuesday_checkbox);
                    check.setChecked(true);
                    view_start.setText(start);
                    view_end.setText(end);
                    Log.w("alaa", "Sunday " + view_start.getText() + "-" + view_end.getText());
                    break;
                case 4:
                    view_start = (TextView) view.findViewById(R.id.Wednesday_start_time);
                    view_end = (TextView) view.findViewById(R.id.Wednesday_end_time);
                    check = (CheckBox)view.findViewById(R.id.Wednesday_checkbox);
                    check.setChecked(true);
                    view_start.setText(start);
                    view_end.setText(end);
                    Log.w("alaa", "Sunday " + view_start.getText() + "-" + view_end.getText());
                    break;
                case 5:
                    view_start = (TextView) view.findViewById(R.id.Thursday_start_time);
                    view_end = (TextView) view.findViewById(R.id.Thursday_end_time);
                    check = (CheckBox)view.findViewById(R.id.Thursday_checkbox);
                    check.setChecked(true);
                    view_start.setText(start);
                    view_end.setText(end);
                    Log.w("alaa", "Thursday " + view_start.getText() + "-" + view_end.getText());
                    break;
                case 6:
                    view_start = (TextView) view.findViewById(R.id.Friday_start_time);
                    view_end = (TextView) view.findViewById(R.id.Friday_end_time);
                    check = (CheckBox)view.findViewById(R.id.Friday_checkbox);
                    check.setChecked(true);
                    view_start.setText(start);
                    view_end.setText(end);
                    Log.w("alaa", "Friday " + view_start.getText() + "-" + view_end.getText());
                    break;
                case 7:
                    view_start = (TextView) view.findViewById(R.id.Saturday_start_time);
                    view_end = (TextView) view.findViewById(R.id.Saturday_end_time);
                    check = (CheckBox)view.findViewById(R.id.Saturday_checkbox);
                    check.setChecked(true);
                    view_start.setText(start);
                    view_end.setText(end);
                    Log.w("alaa", "Saturday " + view_start.getText() + "-" + view_end.getText());
                    break;

            }

        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        ((TimePickerFragment)newFragment).v=(TextView) v;
        newFragment.show(getFragmentManager(), "timePicker");
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {
        TextView v;

        public TimePickerFragment(){

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
            v.setText(hourOfDay+":"+minute);
        }
    }

}
