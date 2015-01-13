package com.taubacademy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;

public class Profile extends android.support.v4.app.Fragment {
    Tutor tutor;
    private View profile;
    private PagerSlidingTabStrip pagerSlidingTabStrip;

    public Profile() {
        super();
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
    public void onResume() {
        super.onResume();
        ViewPager viewPager = (ViewPager) profile.findViewById(R.id.pager);
        pagerSlidingTabStrip = (PagerSlidingTabStrip) profile.findViewById(R.id.Tabs);
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "Available On";
                    case 1:
                        return "Feedbacks";
                    default:
                        return "Relevant Courses";
                }
            }

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return AvailableOnFagment.newInstance(tutor);
                    case 1:
                        return FeedBacksRecyclerView.newInstance(tutor);
                    default:
                        return TaughtByCourses.newInstance(tutor
                        );
                }
            }

        });
        pagerSlidingTabStrip.setViewPager(viewPager);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        profile = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView Name = (TextView) profile.findViewById(R.id.NameOnPRo);
//        ViewPager viewPager = (ViewPager)profile.findViewById(R.id.pager);
//        viewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
//            @Override
//            public int getCount() {
//                return 3;
//            }
//
//            @Override
//            public Fragment getItem(int position) {
//                switch(position){
//                    case 0: return AvailableOnFagment.newInstance(tutor);
//                    case 1 : return FeedBacksRecyclerView.newInstance(tutor);
//                    default: return TaughtByCourses.newInstance(
//                            tutor
//                    );
//                }
//            }
//
//        });
        ParseImageView imagePro = (ParseImageView) profile.findViewById(R.id.imageView);
        ParseFile imageFile = tutor.getPhotoFile();
        if (imageFile != null) {
            imagePro.setParseFile(imageFile);
            imagePro.loadInBackground();
        }
        Name.setText(tutor.getName());
        Integer salary = tutor.getSalary() == null ? 0 : tutor.getSalary();
        ((TextView) profile.findViewById(R.id.textView2)).setText(salary.toString());
        return profile;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


}



