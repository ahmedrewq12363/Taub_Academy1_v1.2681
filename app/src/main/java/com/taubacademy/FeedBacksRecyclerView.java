package com.taubacademy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.List;

public class FeedBacksRecyclerView extends Fragment {
    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    private Tutor tutor;

    public FeedBacksRecyclerView() {
        // Required empty public constructor

    }

    public static FeedBacksRecyclerView newInstance(Tutor tutor) {
        FeedBacksRecyclerView fragment = new FeedBacksRecyclerView();
        fragment.setTutor(tutor);
        return fragment;
    }

    public void Refresh() {
        RecyclerView recList = (RecyclerView) getView().findViewById(R.id.cardList);
        recList.setAdapter(new FeedBackAdapter(tutor.getFeedbacks()));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_feed_backs_recycler_view, container, false);
        RecyclerView recList = (RecyclerView) v.findViewById(R.id.cardList);
        FloatingActionButton fap = (FloatingActionButton) v.findViewById(R.id.fab);
        fap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddFeedBackDialog dialog = new AddFeedBackDialog();
                dialog.setTutor(tutor);
                dialog.show(getFragmentManager(), "Feedback");
            }
        });
        fap.attachToRecyclerView(recList);
        if ((ParseUser.getCurrentUser().get("Tutor") != null) && ParseUser.getCurrentUser().get("Tutor").equals(tutor)) {
            fap.setVisibility(View.GONE);
        } else {
            fap.setVisibility(View.VISIBLE);
        }
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getBaseContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        recList.setAdapter(new FeedBackAdapter(tutor.getFeedbacks()));
        return v;
    }

    public class FeedBackAdapter extends RecyclerView.Adapter<FeedBackAdapter.AvailableOnFragmentHolder> {
        private List<Pair> availble;

        public FeedBackAdapter(List<Pair> availble) {
            this.availble = availble;
        }

        @Override
        public int getItemCount() {
            return availble.size();
        }

        @Override
        public void onBindViewHolder(AvailableOnFragmentHolder contactViewHolder, int i) {
            Pair ci = availble.get(i);
            try {
                ci.fetchIfNeeded();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            contactViewHolder.By.setText(ci.getFirst().getName());
            contactViewHolder.Feedback.setText(ci.getSecond());
        }

        @Override
        public AvailableOnFragmentHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.feed, viewGroup, false);

            return new AvailableOnFragmentHolder(itemView);
        }

        class AvailableOnFragmentHolder extends RecyclerView.ViewHolder {
            protected TextView Feedback;
            protected TextView By;

            public AvailableOnFragmentHolder(View v) {
                super(v);
                Feedback = (TextView) v.findViewById(R.id.Feedback);
                By = (TextView) v.findViewById(R.id.By);
            }
        }
    }

}
