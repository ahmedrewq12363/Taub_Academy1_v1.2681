package com.taubacademy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseException;

import java.util.List;

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
        View v =  inflater.inflate(R.layout.fragment_taught_by_courses, container, false);
        RecyclerView recList = (RecyclerView) v.findViewById(R.id.cardListTaughtBy);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getBaseContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        recList.setAdapter(new TaughtByAdapter(tutor.getAllCourses()));
        return v;
            }
    public class TaughtByAdapter extends RecyclerView.Adapter<TaughtByAdapter.AvailableOnFragmentHolder> {
        private List<Course> availble;

        public TaughtByAdapter(List<Course> availble) {
            this.availble = availble;
        }

        @Override
        public int getItemCount() {
            return availble == null ? 0 : availble.size();
        }

        @Override
        public void onBindViewHolder(AvailableOnFragmentHolder contactViewHolder, int i) {
            Course ci = availble.get(i);
            try {
                ci.fetchIfNeeded();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            contactViewHolder.CourseName.setText(ci.getName());
            contactViewHolder.CourseNumber.setText(ci.getCourseId().toString());
        }

        @Override
        public AvailableOnFragmentHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.taught_by, viewGroup, false);

            return new AvailableOnFragmentHolder(itemView);
        }

        class AvailableOnFragmentHolder extends RecyclerView.ViewHolder {
            protected TextView CourseName;
            protected TextView CourseNumber;
            public AvailableOnFragmentHolder(View v) {
                super(v);
                CourseName = (TextView) v.findViewById(R.id.CourseName);
                CourseNumber = (TextView) v.findViewById(R.id.CourseNum);
            }
        }
    }

}
