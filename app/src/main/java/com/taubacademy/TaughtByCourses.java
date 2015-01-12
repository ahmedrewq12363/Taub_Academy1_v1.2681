package com.taubacademy;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TaughtByCourses.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TaughtByCourses#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaughtByCourses extends Fragment {
    Tutor tutor;
    public static TaughtByCourses newInstance(Tutor tutor) {
        TaughtByCourses fragment = new TaughtByCourses(tutor);
        return fragment;
    }

    public TaughtByCourses(Tutor tutor) {
        this.tutor = tutor;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_taught_by_courses, container, false);
    }


}
