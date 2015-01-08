package com.taubacademy;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import java.lang.ref.WeakReference;
import java.util.ArrayList;

import server.Tutor;

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
    String[] ByF = {"Ahmed","Taha", "Sabry"};
    String[] Available = {"Tuesday 14:00-15:30" , "Thursday 11:00-14:00","Monday 11:00-12:00"};
    String[] Courses_name = {"Introduction To Computers Science","Theory Of Compilation","Software Project","Introduction To System Programming"};
    String[] Courses_num= {"234114","236360","236554","234122"};
    communicator c;

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
        listView.setAdapter(new mylistAdapter(getActivity().getBaseContext(), Teachers_orig, Ranks_orig, Salary_orig, Images_orig));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        text1 = (TextView) getActivity().findViewById(R.id.textView21);
        listView = (ListView) getActivity().findViewById(R.id.listView2);
        listView.setAdapter(new mylistAdapter(getActivity().getBaseContext(), Teachers_orig, Ranks_orig, Salary_orig, Images_orig));
        listView.setOnItemClickListener(this);
        c = (communicator) getActivity();
    }
    public void changeData(ArrayList<Tutor> tutors)
    {
        if (Teachers_orig.length == 0) {
            text1.setText("No Teachers Available For This Course");
            text1.setTextColor(Color.parseColor("#0099CC"));
            return;
        }
        text1.setText("Available Teachers For : " + "234114");
        text1.setTextColor(Color.parseColor("#0099CC"));
        String names [] = {tutors.get(0).name};
        String Ranks [] = {tutors.get(0).Rating.toString()};
        String salaries [] = {tutors.get(0).SalaryPerHour.toString()};
        listView.setAdapter(new mylistAdapter(getActivity().getBaseContext(), names, Ranks, salaries, Images_orig));
    }
    public void changeData(String Course) {
        if (Teachers_orig.length == 0) {
            text1.setText("No Teachers Available For This Course");
            text1.setTextColor(Color.parseColor("#0099CC"));
            return;
        }
        text1.setText("Available Teachers For : " + Course);
        text1.setTextColor(Color.parseColor("#0099CC"));
        /*String [] names = {tutors.get(0).name};
        String [] ranks = {tutors.get(0).Rating.toString()};
        String [] salaries = {tutors.get(0).SalaryPerHour.toString()};
        listView.setAdapter(new mylistAdapter(getActivity().getBaseContext(), names, ranks, salaries, Images_orig));*/

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        getActivity().setContentView(R.layout.profile);

        TextView Name = (TextView) getActivity().findViewById(R.id.NameOnPRo);
        TextView Email = (TextView) getActivity().findViewById(R.id.Email);
        ListView Avail = (ListView) getActivity().findViewById(R.id.ListAvail);
        ListView Taught = (ListView) getActivity().findViewById(R.id.Courses);
        ListView Feeds = (ListView) getActivity().findViewById(R.id.Feedbacks);
        TextView Phone= (TextView) getActivity().findViewById(R.id.Phone);
        TextView Rate = (TextView) getActivity().findViewById(R.id.RateThis);
        ImageView imagePro = (ImageView) getActivity().findViewById(R.id.imageView);

        String Name_t = (String) ((TextView)view.findViewById(R.id.firstLine1)).getText();
        String Email_t = "  " +Name_t+"@gmail.com";
        String Phone_t = "  050-245-4921";
        String Rate_t = "Rate "+ Name_t;
        ImageView image_t = (ImageView) view.findViewById(R.id.icon11);

        Name.setText(Name_t);
        Email.setText(Email_t);
        Phone.setText(Phone_t);
        Rate.setText(Rate_t);
        imagePro.setImageDrawable(image_t.getDrawable());
        Avail.setAdapter(new myListViewAvailable(getActivity().getBaseContext(),Available));
        Taught.setAdapter(new mylistAdapterTaughtBy(getActivity().getBaseContext(), Courses_name,Courses_num));
        Feeds.setAdapter(new mylistAdapterFeedbacks(getActivity().getBaseContext(), Feedbackss,ByF));



    }
    class mylistAdapterTaughtBy extends BaseAdapter  {
        String[] Courses;
        String[] Names;
        int global_items;
        WeakReference<Context> contextWeakReference;
        mylistAdapterTaughtBy(Context c, String[] courses_names, String[] courses_nums){
            contextWeakReference = new WeakReference<Context>(c);
            Courses=courses_nums;
            Names=courses_names;
            global_items = Names.length;
        }
        @Override
        public int getCount() {
            return global_items;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewH = null;
            if(view == null){
//                view is null
                LayoutInflater inflater = (LayoutInflater) contextWeakReference.get().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.taught_by,viewGroup,false);
                viewH = new ViewHolder();
                viewH.firstLine = (TextView)view.findViewById(R.id.CourseName);
                viewH.secondLine=(TextView)view.findViewById(R.id.CourseNum);
                viewH.firstLine.setTextColor(Color.parseColor("#0099CC"));
                viewH.secondLine.setTextColor(Color.parseColor("#0099CC"));
                view.setTag(viewH);
            }else {
                //view != null
                viewH = (ViewHolder) view.getTag();
            }
            //set curr view (the suitable indexes in the arrays).
            viewH.firstLine.setText(Names[i%Names.length]);
            viewH.secondLine.setText(Courses[i%(Courses.length)]);
            return view;
        }


        class ViewHolder {
            TextView firstLine;
            TextView secondLine;
//            ImageView icon;
            int position;
        }
    }


    class mylistAdapterFeedbacks extends BaseAdapter  {
        String[] Feed;
        String[] By;
        int global_items;
        WeakReference<Context> contextWeakReference;
        mylistAdapterFeedbacks(Context c, String[] Feeds, String[] Bys){
            contextWeakReference = new WeakReference<Context>(c);
            Feed=Feeds;
            By=Bys;
            global_items = Feeds.length;
        }
        @Override
        public int getCount() {
            return global_items;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewH = null;
            if(view == null){
//                view is null
                LayoutInflater inflater = (LayoutInflater) contextWeakReference.get().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.feed,viewGroup,false);
                viewH = new ViewHolder();
                viewH.firstLine = (TextView)view.findViewById(R.id.Feedback);
                viewH.secondLine=(TextView)view.findViewById(R.id.By);
                viewH.firstLine.setTextColor(Color.parseColor("#0099CC"));
                viewH.secondLine.setTextColor(Color.parseColor("#0099CC"));
                view.setTag(viewH);
            }else {
                //view != null
                viewH = (ViewHolder) view.getTag();
            }
            //set curr view (the suitable indexes in the arrays).
            viewH.firstLine.setText(Feed[i%Feed.length]);
            viewH.secondLine.setText("By " +By[i%(By.length)]);
            return view;
        }


        class ViewHolder {
            TextView firstLine;
            TextView secondLine;
            int position;
        }
    }

    static class mylistAdapter extends BaseAdapter {
        String[] Teachers_t;
        String[] Ranks_t;
        String[] Salary_t;
        Integer[] Images_t;
        int global_items;
        WeakReference<Context> contextWeakReference;

        mylistAdapter(Context c, String[] Teachers, String[] Ranks, String[] Salary, Integer[] Images) {
            contextWeakReference = new WeakReference<Context>(c);
            Teachers_t = Teachers;
            Ranks_t = Ranks;
            Salary_t = Salary;
            global_items = Teachers_t.length;
            Images_t = Images;

        }

        @Override
        public int getCount() {
            return global_items;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewH = null;
            if (view == null) {
//                view is null
                LayoutInflater inflater = (LayoutInflater) contextWeakReference.get().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.teacher_item, viewGroup, false);
                viewH = new ViewHolder();
                viewH.firstLine = (TextView) view.findViewById(R.id.firstLine1);
                viewH.secondLine = (TextView) view.findViewById(R.id.secondLine1);
                viewH.thirdLine = (TextView) view.findViewById(R.id.thirdLine);
                viewH.firstLine.setTextColor(Color.parseColor("#0099CC"));
                viewH.secondLine.setTextColor(Color.parseColor("#0099CC"));
                viewH.thirdLine.setTextColor(Color.parseColor("#0099CC"));
                viewH.icon = (ImageView) view.findViewById(R.id.icon11);
                view.setTag(viewH);
            } else {
                //view != null
                viewH = (ViewHolder) view.getTag();
            }
            //set curr view (the suitable indexes in the arrays).
            viewH.firstLine.setText(Teachers_t[i % (Teachers_t.length)]);
            viewH.secondLine.setText("Rank : " + Ranks_t[i % (Ranks_t.length)] + "/5");
            viewH.thirdLine.setText("Salary : " + Salary_t[i % (Salary_t.length)] + "nis");
            viewH.icon.setImageResource(Images_t[i % Images_t.length]);
            viewH.firstLine.setTextColor(Color.parseColor("#0099CC"));
            viewH.secondLine.setTextColor(Color.parseColor("#0099CC"));
            viewH.thirdLine.setTextColor(Color.parseColor("#0099CC"));
            return view;
        }

        class ViewHolder {
            TextView firstLine;
            TextView secondLine;
            TextView thirdLine;
            ImageView icon;
            int position;
        }
    }


    class myListViewAvailable extends BaseAdapter  {
        String[] Avail;
        int global_items;
        WeakReference<Context> contextWeakReference;
        public myListViewAvailable(Context baseContext, String[] available) {
            contextWeakReference = new WeakReference<Context>(baseContext);
            Avail=available;
            global_items = Avail.length;
        }

        @Override
        public int getCount() {
            return global_items;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewH = null;
            if(view == null){
//                view is null
                LayoutInflater inflater = (LayoutInflater) contextWeakReference.get().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.avail,viewGroup,false);
                viewH = new ViewHolder();
                viewH.firstLine = (TextView)view.findViewById(R.id.AvailId);
                viewH.firstLine.setTextColor(Color.parseColor("#0099CC"));
//                viewH.icon = (ImageView) view.findViewById(R.id.icon11);
                view.setTag(viewH);
            }else {
                //view != null
                viewH = (ViewHolder) view.getTag();
            }
            //set curr view (the suitable indexes in the arrays).
            viewH.firstLine.setText(Avail[i%Avail.length]);
            return view;
        }


        class ViewHolder {
            TextView firstLine;
            int position;
        }
    }
}

