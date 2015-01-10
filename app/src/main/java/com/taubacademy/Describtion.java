package com.taubacademy;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseException;

import java.util.ArrayList;

/**
 * Created by ziad on 12/26/2014.
 */
public class Describtion extends Fragment implements AdapterView.OnItemClickListener {
    ListView listView;
    TextView text1;
    String[] Teachers_orig = {"Ahmed Eissa", "Taha Mahajna", "Abo Elsalamy", "Talal", "Ziad", "Abo Elrazik", "Ward", "Hosam Eissa", "Ghassan"};
    String[] Ranks_orig = {"5", "3", "0", "2", "4", "5", "2", "3", "4"};
    String[] Salary_orig = {"20", "14", "0", "4", "22", "12", "34", "55", "12"};
    Integer[] Images_orig = {R.drawable.ahmed, R.drawable.taha, R.drawable.alaa, R.drawable.alaa, R.drawable.ziad, R.drawable.aboelrazik, R.drawable.ward, R.drawable.hosa, R.drawable.ghassan};
    String[] Feedbackss = {"He Is a very good teacher", "He is so smart and recommended highly", "The best Teacher Ever"};
    String[] ByF = {"Ahmed", "Taha", "Sabry"};
    String[] Available = {"Tuesday 14:00-15:30", "Thursday 11:00-14:00", "Monday 11:00-12:00"};
    String[] Courses_name = {"Introduction To Computers Science", "Theory Of Compilation", "Software Project", "Introduction To System Programming"};
    String[] Courses_num = {"234114", "236360", "236554", "234122"};
    communicator c;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.describtion, container, false);
    }

    public void SortBy(String sortParam) {

        int k = 0;
        int i = 0;
        if (sortParam == "Salary") {
            int max = 0;
            for (k = Salary_orig.length; k > 0; k--) {
                for (i = 0; i < k; ++i) {
                    double q = Double.parseDouble(Salary_orig[i]);
                    if (q > Double.parseDouble(Salary_orig[max])) {
                        max = i;
                    }
                }
                String tmpTeacher = Teachers_orig[k - 1];
                String tmpRank = Ranks_orig[k - 1];
                String tmpSalary = Salary_orig[k - 1];
                Integer tmpImage = Images_orig[k - 1];
                Teachers_orig[k - 1] = Teachers_orig[max];
                Ranks_orig[k - 1] = Ranks_orig[max];
                Salary_orig[k - 1] = Salary_orig[max];
                Images_orig[k - 1] = Images_orig[max];
                Teachers_orig[max] = tmpTeacher;
                Ranks_orig[max] = tmpRank;
                Salary_orig[max] = tmpSalary;
                Images_orig[max] = tmpImage;
                max = 0;
            }
        } else {
            int max = 0;
            for (k = Ranks_orig.length; k > 0; k--) {
                for (i = 0; i < k; ++i) {
                    double q = Double.parseDouble(Ranks_orig[i]);
                    if (q > Double.parseDouble(Ranks_orig[max])) {
                        max = i;
                    }
                }
                String tmpTeacher = Teachers_orig[k - 1];
                String tmpRank = Ranks_orig[k - 1];
                String tmpSalary = Salary_orig[k - 1];
                Integer tmpImage = Images_orig[k - 1];
                Teachers_orig[k - 1] = Teachers_orig[max];
                Ranks_orig[k - 1] = Ranks_orig[max];
                Salary_orig[k - 1] = Salary_orig[max];
                Images_orig[k - 1] = Images_orig[max];
                Teachers_orig[max] = tmpTeacher;
                Ranks_orig[max] = tmpRank;
                Salary_orig[max] = tmpSalary;
                Images_orig[max] = tmpImage;
                max = 0;
            }
        }
        //listView.setAdapter(new mylistAdapter(getActivity().getBaseContext(), Teachers_orig, Ranks_orig, Salary_orig, Images_orig));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        text1 = (TextView) getActivity().findViewById(R.id.textView21);
        listView = (ListView) getActivity().findViewById(R.id.listView2);
        //listView.setAdapter(new mylistAdapter(getActivity().getBaseContext(), Teachers_orig, Ranks_orig, Salary_orig, Images_orig));
        listView.setOnItemClickListener(this);
        c = (communicator) getActivity();
    }

    public void changeData(String course) throws ParseException {
        if (Teachers_orig.length == 0) {
            text1.setText("No Teachers Available For This Course");
            text1.setTextColor(Color.parseColor("#0099CC"));
            return;
        }
        text1.setText("Available Teachers For : " + course);
        text1.setTextColor(Color.parseColor("#0099CC"));
        listView.setAdapter(new tutorsAdapter(getActivity().getBaseContext(), (
                Course.getTutorsOfCourse(Integer.parseInt(course)))));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        String Name_t = (String) ((TextView) view.findViewById(R.id.firstLine1)).getText();
        String Email_t = "  " + Name_t + "@gmail.com";
        String Phone_t = "  050-245-4921";
        String Rate_t = "Rate " + Name_t;
        ImageView image_t = (ImageView) view.findViewById(R.id.icon11);
        ArrayList<Course> courses = new ArrayList<Course>();
        for (int index = 0; index < Courses_name.length; index++) {
            Course c = new Course();
            c.setName(Courses_name[i]);
            c.setCourseId(Integer.parseInt(Courses_num[i]));
            courses.add(c);
        }
        c.ChangeFrag(Name_t, Email_t, Phone_t, Rate_t, image_t, Feedbackss, ByF, courses, Available);

    }

}

