package com.taubacademy;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AddFeedBackDialog extends DialogFragment {
    private Tutor tutor;
    public AddFeedBackDialog(Tutor tutor)
    {
        super();
        this.tutor = tutor;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_feed_back_dialog, container,
                false);
        getDialog().setTitle("DialogFragment Tutorial");
        // Do something else
        return rootView;
    }
    @Override
    public void show(FragmentManager manager,String FeedBack)
    {
        super.show(manager,FeedBack);
    }
}