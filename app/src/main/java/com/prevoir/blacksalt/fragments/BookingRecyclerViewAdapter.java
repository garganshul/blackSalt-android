package com.prevoir.blacksalt.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prevoir.blacksalt.R;
import com.prevoir.blacksalt.fragments.BookingListFragment.OnBookingListFragmentInteractionListener;
import com.prevoir.blacksalt.fragments.dummy.DummyContent.DummyItem;
import com.prevoir.blacksalt.models.Booking;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnBookingListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class BookingRecyclerViewAdapter extends RecyclerView.Adapter<BookingRecyclerViewAdapter.ViewHolder> {

    private List<Booking> mValues;
    private final BookingListFragment.OnBookingListFragmentInteractionListener mListener;

    public BookingRecyclerViewAdapter(BookingListFragment.OnBookingListFragmentInteractionListener listener) {
        mValues = new ArrayList<>();
        mListener = listener;
    }

    public void setData(List<Booking> items) {
        this.mValues = items;
    }

    public void addData(Booking item) {
        this.mValues.add(item);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position)._id);
        holder.mContentView.setText(mValues.get(position).title);

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
        public final TextView mContentView;
        public Booking mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.id);
            mContentView = view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
