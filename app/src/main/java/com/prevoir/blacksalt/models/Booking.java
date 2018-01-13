package com.prevoir.blacksalt.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by anshul.garg on 14/01/18.
 */

public class Booking {
    @SerializedName("title")
    public String title;

    @SerializedName("date")
    public String date;

    @SerializedName("booked")
    public boolean booked;

    @SerializedName("people")
    public int people;

    @SerializedName("timestamp")
    public long timestamp;
}
