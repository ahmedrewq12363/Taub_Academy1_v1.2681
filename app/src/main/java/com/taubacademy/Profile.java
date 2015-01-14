package com.taubacademy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

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
        ParseImageView imagePro = (ParseImageView) profile.findViewById(R.id.imageView);
        ParseFile imageFile = tutor.getPhotoFile();
        if (imageFile != null) {
            Picasso.with(getActivity().getBaseContext()).load(imageFile.getUrl()).into(imagePro);
        }
        Name.setText(tutor.getName());
        Integer salary = tutor.getSalary() == null ? 0 : tutor.getSalary();
        ((TextView) profile.findViewById(R.id.textView2)).setText(salary.toString());
        ImageButton edit = (ImageButton) profile.findViewById(R.id.imageButtonEdit);
        ImageButton logOut = (ImageButton) profile.findViewById(R.id.imageButtonLogOut);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction Transaction = getFragmentManager().beginTransaction();
                Transaction.add(R.id.ProfileFrag, new MyProfileFragment(),null);
                Transaction.addToBackStack(null);
                Transaction.commit();
            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                FragmentTransaction Transaction = getFragmentManager().beginTransaction();
                Transaction.replace(R.id.CoursesFrag, ((MainActivity)getActivity()).CourFragment, "Courses");
                Transaction.replace(R.id.DescFrag, ((MainActivity)getActivity()).DescFragment, "Describtions");
                Transaction.commit();
            }
        });
        if((ParseUser.getCurrentUser().get("Tutor") != null) && ParseUser.getCurrentUser().get("Tutor").equals(tutor))
        {
           logOut.setVisibility(View.VISIBLE);
            edit.setVisibility(View.VISIBLE);
        }
        else
        {
            logOut.setVisibility(View.GONE);
            edit.setVisibility(View.GONE);
        }
        return profile;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


}



