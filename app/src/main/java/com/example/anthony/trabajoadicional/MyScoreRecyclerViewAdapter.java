package com.example.anthony.trabajoadicional;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.text.format.DateFormat;
import com.example.anthony.trabajoadicional.Entities.Score;
import com.example.anthony.trabajoadicional.ScoreFragment.OnListFragmentInteractionListener;
import com.example.anthony.trabajoadicional.dummy.DummyContent.DummyItem;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyScoreRecyclerViewAdapter extends RecyclerView.Adapter<MyScoreRecyclerViewAdapter.ViewHolder> {

    private final List<Score> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyScoreRecyclerViewAdapter(List<Score> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_score, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = formatter.format(mValues.get(position).getDate());
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(String.valueOf(position+1));
        holder.mUsernameView.setText(mValues.get(position).getName());
        holder.mScoreView.setText(String.valueOf(mValues.get(position).getScore()));
        holder.mDateView.setText(s);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mUsernameView;
        public final TextView mScoreView;
        public final TextView mDateView;
        public Score mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mUsernameView = (TextView) view.findViewById(R.id.userName);
            mScoreView= view.findViewById(R.id.score);
            mDateView=view.findViewById(R.id.date);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mUsernameView.getText() + "'";
        }
    }
}
