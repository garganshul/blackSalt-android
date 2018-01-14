package com.prevoir.blacksalt.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Booking booking = mValues.get(position);
        holder.mItem = booking;
        holder.title.setText(booking.title);
        String type;
        if(booking.partyType.equals("ALA_CARTE")){
            type = "Ala Carte";
        }else {
            type = "Buffet";
        }
        type+="("+booking.people+")";
        holder.type.setText(type);
        holder.amount.setText(holder.itemView.getContext().getResources().getString(R.string.Rs)+booking.totalAmount);
        holder.date.setText(booking.date);
        if(booking.status.equals("CONFIRMED")){
            holder.bookingStatus.setImageResource(R.drawable.btn_check_buttonless_on);
        }else if(booking.status.equals("PENDING")){
            holder.bookingStatus.setImageResource(R.drawable.ic_dialog_time);
        }

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
        public final TextView date;
        public final TextView title;
        public final TextView type;
        public final TextView amount;
        public final ImageView bookingStatus;
        public Booking mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            date = view.findViewById(R.id.list_item_date);
            title = view.findViewById(R.id.list_item_title);
            type = view.findViewById(R.id.list_item_partyType_count);
            amount = view.findViewById(R.id.list_item_amount);
            bookingStatus = view.findViewById(R.id.list_item_booking_status);
        }
    }
}
