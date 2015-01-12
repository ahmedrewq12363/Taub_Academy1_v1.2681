package com.taubacademy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseException;

public class Profile extends android.support.v4.app.Fragment{
    Tutor tutor;
    public Profile() {

    }

    public Profile(Tutor tutor) {
        this.tutor = tutor;
        try {
            this.tutor.fetchIfNeeded();
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
        TextView Phone = (TextView) profile.findViewById(R.id.Phone);
        TextView Rate = (TextView) profile.findViewById(R.id.RateThis);
        ViewPager viewPager = (ViewPager)profile.findViewById(R.id.pager);
        viewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public Fragment getItem(int position) {
                switch(position){
                    case 0: return AvailableOnFagment.newInstance(tutor);
                    case 1 : return FeedBacksRecyclerView.newInstance(tutor);
                    default: return TaughtByCourses.newInstance(
                            tutor
                    );
                }
            }

        });
        /*ParseImageView imagePro = (ParseImageView) profile.findViewById(R.id.imageView);
        ParseFile imageFile = tutor.getPhotoFile();
        if (imageFile != null) {
            imagePro.setParseFile(imageFile);
            imagePro.loadInBackground();
        }*/
        Name.setText(tutor.getName());
        Email.setText(tutor.getEmail());
        Phone.setText(tutor.getPhone());
        Rate.setText("Rate " + tutor.getName() + " :");

        return profile;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


 }



