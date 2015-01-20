package com.taubacademy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AvailableOnFagment extends Fragment {

    public static AvailableOnFagment newInstance(Tutor tutor) {
        AvailableOnFagment fragment = new AvailableOnFagment();
        fragment.setTutor(tutor);
        return fragment;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    private Tutor tutor;
    public AvailableOnFagment() {

        //((RecyclerView))
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_available_on_fagment, container, false);
        RecyclerView recList = (RecyclerView) v.findViewById(R.id.cardListAvail);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getBaseContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        recList.setAdapter(new AvailableOnAdapter(tutor.getAvailableTime()));
        return v;
    }
    public class AvailableOnAdapter extends RecyclerView.Adapter<AvailableOnAdapter.AvailableOnFragmentHolder> {
        private List<String> availble;

        public AvailableOnAdapter(List<String> availble) {
            this.availble = availble;
        }

        @Override
        public int getItemCount() {
            return availble == null ? 0 : availble.size();
        }

        @Override
        public void onBindViewHolder(AvailableOnFragmentHolder contactViewHolder, int i) {
            String ci = availble.get(i);
            contactViewHolder.AvaialbleOn.setText(ci);
        }

        @Override
        public AvailableOnFragmentHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.avail, viewGroup, false);

            return new AvailableOnFragmentHolder(itemView);
        }

        class AvailableOnFragmentHolder extends RecyclerView.ViewHolder {
            protected TextView AvaialbleOn;

            public AvailableOnFragmentHolder(View v) {
                super(v);
                AvaialbleOn = (TextView) v.findViewById(R.id.AvailId);
            }
        }
    }

}
