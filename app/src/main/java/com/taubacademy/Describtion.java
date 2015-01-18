package com.taubacademy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class
        Describtion extends Fragment implements AdapterView.OnItemClickListener {
    ListView listView;
    TextView text1;
    View root;
    com.taubacademy.communicator communicator;
    String course;
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        communicator =(com.taubacademy.communicator)activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null)
        course=getArguments().getString("COURSE");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root= inflater.inflate(R.layout.describtion, container, false);
        initViews();
        if(course!=null) try {
            changeData(course);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return root;

    }

    private void initViews(){
        text1=(TextView) root.findViewById(R.id.textView21);
        listView = (ListView) root.findViewById(R.id.listView2);
        listView.setOnItemClickListener(this);
    }
    public void SortBy(String sortParam) {
        if (listView.getAdapter() == null) {
            return;
        }
        ArrayList<Tutor> tutors = ((tutorsAdapter) listView.getAdapter()).getTutors();
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
        listView.setAdapter(new tutorsAdapter(getActivity().getBaseContext(), tutors));
    }



    public void changeData(final String course) throws ParseException {
       new AsyncTask<Void,ArrayList<Tutor>,ArrayList<Tutor>>()
       {
           ProgressDialog progressDialog = new ProgressDialog(getActivity());
           @Override
           protected void onPreExecute() {
               super.onPreExecute();
               progressDialog.setMessage("loading tutors for "+course);
               progressDialog.show();
           }

           @Override
           protected ArrayList<Tutor> doInBackground(Void... voids) {
               ArrayList<Tutor> tutors = null;
               try {
                   tutors = Course.getTutorsOfCourse(Integer.parseInt(course));
               } catch (ParseException e) {
                   e.printStackTrace();
               }


               for(Tutor t : tutors)
               {
                   t.fetchInBackground();
               }

               return tutors;
           }

           @Override
           protected void onPostExecute(ArrayList<Tutor> tutors) {
               super.onPostExecute(tutors);
               if (tutors.size() == 0) {
                   listView.setAdapter(new tutorsAdapter(getActivity().getBaseContext(), new ArrayList<Tutor>()));
                   text1.setText("No Teachers Available");

               }
               text1.setText("Available Teachers For : " + course);
               listView.setAdapter(new tutorsAdapter(getActivity().getBaseContext(), (tutors)));
               progressDialog.dismiss();
           }
       }.execute();

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        communicator.ChangeFrag(
                ((tutorsAdapter) listView.getAdapter()).getTutorAtIndex(i));

    }

}

