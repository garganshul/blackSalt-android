package com.prevoir.blacksalt.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.prevoir.blacksalt.R;
import com.prevoir.blacksalt.models.Booking;
import com.prevoir.blacksalt.network.BlackSaltApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddBookingInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddBookingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddBookingFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextInputEditText partyDate,title,peopleCount,advanceAmount,totalAmount;
    private RadioGroup partyType;
    private CheckBox bookingConfirmed;
    private TextInputLayout totalAmountLayout,advanceAmountLayout;
    private AddBookingInteractionListener mListener;
    private Button submitButton;
    private String partyTypeStr;
    public AddBookingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddBookingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddBookingFragment newInstance(String param1, String param2) {
        AddBookingFragment fragment = new AddBookingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_add_booking, container, false);
        partyDate = rootView.findViewById(R.id.party_date);
        partyDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });

        title = rootView.findViewById(R.id.booking_title);
        peopleCount = rootView.findViewById(R.id.booking_people);
        partyType = rootView.findViewById(R.id.party_type);
        totalAmount = rootView.findViewById(R.id.total_amount);
        advanceAmount = rootView.findViewById(R.id.advance_amount);
        bookingConfirmed = rootView.findViewById(R.id.booking_confirmed);
        totalAmountLayout = rootView.findViewById(R.id.total_amount_layout);
        advanceAmountLayout = rootView.findViewById(R.id.advance_amount_layout);
        submitButton = rootView.findViewById(R.id.booking_submit);

        bookingConfirmed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    advanceAmountLayout.setVisibility(View.VISIBLE);
                }else{
                    advanceAmountLayout.setVisibility(View.GONE);
                }
            }
        });

        partyType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                RadioButton radioButton = rootView.findViewById(id);
                if(radioButton.getTag().equals("party_type_alaCarte")){
                    partyTypeStr = "ALA_CARTE";
                    totalAmountLayout.setVisibility(View.VISIBLE);
                    totalAmountLayout.setHint("Total amount");
                }else{
                    partyTypeStr = "BUFFET";
                    totalAmountLayout.setVisibility(View.VISIBLE);
                    totalAmountLayout.setHint("Amount per plate");
                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmitButtonPressed();
            }
        });

        return rootView;
    }

    public void onSubmitButtonPressed() {
        String titleStr = title.getText().toString();
        if(TextUtils.isEmpty(titleStr)){
            Toast.makeText(getContext(),"Please enter party title", Toast.LENGTH_LONG).show();
            title.requestFocus();
            return;
        }

        String countStr = peopleCount.getText().toString();
        if(TextUtils.isEmpty(countStr)){
            peopleCount.requestFocus();
            Toast.makeText(getContext(),"Please enter people count", Toast.LENGTH_LONG).show();
            return;
        }
        int numberOfPeople = Integer.valueOf(countStr);

        String partyDateStr = partyDate.getText().toString();
        if(TextUtils.isEmpty(partyDateStr)){
            Toast.makeText(getContext(),"Please enter party date", Toast.LENGTH_LONG).show();
            partyDate.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(partyTypeStr)){
            partyType.requestFocus();
            Toast.makeText(getContext(),"Please enter party type", Toast.LENGTH_LONG).show();
            return;
        }

        int advancedAmountValue = 0;
        if(bookingConfirmed.isChecked()){
            String advanceAmountStr = advanceAmount.getText().toString();
            if(TextUtils.isEmpty(partyDateStr)){
                partyType.requestFocus();
                Toast.makeText(getContext(),"Please enter advance amount", Toast.LENGTH_LONG).show();
                return;
            }
            advancedAmountValue = Integer.valueOf(advanceAmountStr);
        }

        int totalAmountValue = 0;
        String totalAmountStr = totalAmount.getText().toString();
        if(!TextUtils.isEmpty(totalAmountStr)){
            if(partyTypeStr.equals("ALA_CARTE")){
                totalAmountValue = Integer.valueOf(totalAmountStr);
            }else{
                totalAmountValue = Integer.valueOf(totalAmountStr) * numberOfPeople;
            }
        }

        Booking booking = new Booking();
        booking.status = bookingConfirmed.isChecked()? "CONFIRMED": "PENDING";
        booking.title = titleStr;
        booking.date = partyDateStr;
        booking.people = numberOfPeople;
        booking.partyType = partyTypeStr;
        booking.advanceAmount = advancedAmountValue;
        booking.totalAmount = totalAmountValue;
        booking.bookingDate = System.currentTimeMillis();
        BlackSaltApiClient.getBlackSaltApiService(getContext()).saveBooking(booking).enqueue(new Callback<Booking>() {
            @Override
            public void onResponse(Call<Booking> call, Response<Booking> response) {
                if(mListener != null){
                    mListener.onBookingAdded(response.body());
                }
            }

            @Override
            public void onFailure(Call<Booking> call, Throwable t) {
                System.out.println("~~~~errror"+t.getLocalizedMessage());
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddBookingInteractionListener) {
            mListener = (AddBookingInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AddBookingInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void onDateSet(int year, int month, int day) {
        if(partyDate != null){
            partyDate.setText(day+"/"+month+"/"+year);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface AddBookingInteractionListener {
        void onBookingAdded(Booking booking);
    }

}
