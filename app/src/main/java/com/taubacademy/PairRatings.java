package com.taubacademy;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;


@ParseClassName("PairRatings")
public class PairRatings extends ParseObject{
    private Tutor first;
    private String second;
    public PairRatings()
    {

    }
    public PairRatings(Tutor first, String second) {
        super("PairRatings");
        this.first = first;
        this.second = second;
    }
    public void update() {
        put("first", first);
        put("second", second);
        saveInBackground();
    }
    public int hashCode() {
        int hashFirst = first != null ? first.hashCode() : 0;
        return hashFirst;
    }

    public boolean equals(Object other) {
        if (other instanceof PairRatings) {
            PairRatings otherPair = (PairRatings) other;
            return
                    ((  this.first == otherPair.first ||
                            ( this.first != null && otherPair.first != null &&
                                    this.first.equals(otherPair.first))));
        }

        return false;
    }

    public java.lang.String toString()
    {
        return "{" + first + "}";
    }

    public Tutor getFirst() {
        Tutor t  = (Tutor)get("first");
        try {
            t.fetchIfNeeded();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return t;
    }

    public void setFirst(Tutor first) {
        this.first = first;
    }

    public String getSecond() {
        try {
            fetchIfNeeded();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getString("second");
    }

    public void setSecond(String second) {
        this.second = second;
    }
}
