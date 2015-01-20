package com.taubacademy;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.parse.ParseUser;

public class AddFeedBackDialog extends DialogFragment {
    private Tutor tutor;

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public AddFeedBackDialog() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_feed_back_dialog, container,
                false);
        getDialog().setTitle("Add Feedback to " + tutor.getName());
        ImageButton button = (ImageButton) rootView.findViewById(R.id.imageButtonDialog);
        final EditText edit = (EditText) rootView.findViewById(R.id.AddFeedBackDialogText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((ParseUser.getCurrentUser().get("Tutor") != null)) {
                    Tutor t = (Tutor) ParseUser.getCurrentUser().get("Tutor");
                    String FeedBack = edit.getText().toString();
                    if (FeedBack.equals("")) {
                        Toast.makeText(getActivity().getBaseContext(), "Please Enter valid Feedback", Toast.LENGTH_SHORT).show();
                        return;

                    }
                    tutor.setFeedback(t, FeedBack);
                    AdapterFeedBack pager = ((AdapterFeedBack) ((FragmentPagerAdapter) ((Profile) getParentFragment()).viewPager.getAdapter()));
                    if(((FeedBacksRecyclerView) pager.fragment[1]) != null){
                        ((FeedBacksRecyclerView) pager.fragment[1]).Refresh();
                    }
                    getDialog().dismiss();
                } else {
                    Toast.makeText(getActivity().getBaseContext(), "Please LogIn First", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rootView;
    }

    @Override
    public void show(FragmentManager manager, String FeedBack) {
        super.show(manager, FeedBack);
    }
}