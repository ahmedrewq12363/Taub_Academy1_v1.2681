package com.taubacademy;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Profile extends Fragment {
    Tutor tutor;

    public Profile() {

    }

    public Profile(Tutor tutor) {
        this.tutor = tutor;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View profile = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView Name = (TextView) profile.findViewById(R.id.NameOnPRo);
        TextView Email = (TextView) profile.findViewById(R.id.Email);
        ListView Avail = (ListView) profile.findViewById(R.id.ListAvail);
        ListView Taught = (ListView) profile.findViewById(R.id.Courses);
        ListView Feeds = (ListView) profile.findViewById(R.id.Feedbacks);
        TextView Phone = (TextView) profile.findViewById(R.id.Phone);
        TextView Rate = (TextView) profile.findViewById(R.id.RateThis);
        ImageView imagePro = (ImageView) profile.findViewById(R.id.imageView);
        ImageView imageEmail = (ImageView) profile.findViewById(R.id.EmailImage);
        Name.setText(tutor.getName());
        Email.setText(tutor.getEmail());
        Phone.setText(tutor.getPhone());
        Rate.setText(tutor.getRating().toString());
//        imagePro.setImageDrawable(image_t.getDrawable());
        //Avail.setAdapter(new AvailableAdapter(getActivity().getBaseContext(),Available));
        Taught.setAdapter(new TaughtByAdapter(getActivity().getBaseContext(), tutor.getAllCourses()));
        Feeds.setAdapter(new FeedBacksAdapter(getActivity().getBaseContext(), tutor.get));
        Taught.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });
        Avail.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });
        Feeds.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        return profile;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


}
