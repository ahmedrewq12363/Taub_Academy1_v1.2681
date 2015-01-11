package com.taubacademy;

import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by ziad on 12/26/2014.
 */
public class Describtion extends Fragment implements AdapterView.OnItemClickListener {
    ListView listView;
    TextView text1;
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
        ArrayList<Tutor> tutors = ((tutorsAdapter)listView.getAdapter()).getTutors();
        if (sortParam == "Salary") {
            Collections.sort(tutors, new Comparator<Tutor>() {
                @Override
                public int compare(Tutor tutor, Tutor tutor2) {
                    return tutor.getSalary().compareTo(tutor2.getSalary());
                }
            });
        } else {
            Collections.sort(tutors, new Comparator<Tutor>() {
                @Override
                public int compare(Tutor tutor, Tutor tutor2) {
                    return tutor2.getRating().compareTo(tutor.getRating());
                }
            });
        }
        listView.setAdapter(new tutorsAdapter(getActivity().getBaseContext(),tutors));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        text1 = (TextView) getActivity().findViewById(R.id.textView21);
        listView = (ListView) getActivity().findViewById(R.id.listView2);
        listView.setOnItemClickListener(this);
        c = (communicator) getActivity();
    }

    public void changeData(String course) throws ParseException {
        ArrayList<Tutor> tutors =  Course.getTutorsOfCourse(Integer.parseInt(course));
        if (tutors.size() == 0) {
            text1.setText("No Teachers Available For This Course");
            text1.setTextColor(Color.parseColor("#0099CC"));
            return;
        }
        text1.setText("Available Teachers For : " + course);
        text1.setTextColor(Color.parseColor("#0099CC"));
        listView.setAdapter(new tutorsAdapter(getActivity().getBaseContext(), (tutors)));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        c.ChangeFrag(
                ((tutorsAdapter)listView.getAdapter()).getTutorAtIndex(i));

    }

}

