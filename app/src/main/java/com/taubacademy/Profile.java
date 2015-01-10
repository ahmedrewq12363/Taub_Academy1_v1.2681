package com.taubacademy;

import android.app.Activity;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Profile extends Fragment {
static    String Name_t;
static    String Email_t;
static    String Phone_t;
static    String Rate_t;
static    ImageView image_t;
static    String[] Available;
static    String[] Feedbackss;
static    String[] ByF;
static    List<Course> courses_t;

    public Profile()
    {

    }
    public Profile(String Name , String Email , String Phone , String Rate , ImageView image , String[] Avail , String[] Feeds, String[] by,List<Course> courses)
    {
        Name_t=Name;
        Email_t=Email;
        Phone_t=Phone;
        Rate_t=Rate;
        image_t=image;
        Available=Avail;
        Feedbackss=Feeds;
        ByF=by;
        courses_t=courses;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View profile =  inflater.inflate(R.layout.fragment_profile, container, false);
        TextView Name = (TextView) profile.findViewById(R.id.NameOnPRo);
        TextView Email = (TextView) profile.findViewById(R.id.Email);
        ListView Avail = (ListView) profile.findViewById(R.id.ListAvail);
        ListView Taught = (ListView) profile.findViewById(R.id.Courses);
        ListView Feeds = (ListView) profile.findViewById(R.id.Feedbacks);
        TextView Phone= (TextView) profile.findViewById(R.id.Phone);
        TextView Rate = (TextView) profile.findViewById(R.id.RateThis);
        ImageView imagePro = (ImageView) profile.findViewById(R.id.imageView);
        Name.setText(Name_t);
        Email.setText(Email_t);
        Phone.setText(Phone_t);
        Rate.setText(Rate_t);
        imagePro.setImageDrawable(image_t.getDrawable());
        Avail.setAdapter(new AvailableAdapter(getActivity().getBaseContext(),Available));
        Taught.setAdapter(new TaughtByAdapter(getActivity().getBaseContext(),courses_t));
        Feeds.setAdapter(new FeedBacksAdapter(getActivity().getBaseContext(), Feedbackss,ByF));
        return profile;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


}
