package com.taubacademy;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
@ParseClassName("Tutor")
public class Tutor extends ParseObject{

    public Tutor() {
        super("Tutor");
    }

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("title", name);
    }

    public ParseUser getAuthor() {
        return getParseUser("author");
    }

    public void setAuthor(ParseUser user) {
        put("author", user);
    }
    public String getEmail() {
        return getString("email");
    }

    public void setEmail(String email) {
        put("email", email);
    }
    public String getPhoneNumber() {
        return getString("Phone");
    }
    public void SetPhoneNumber(String Phone)
    {
        put("Phone",Phone);
    }
    public Integer getRating() {
        return getInt("rating");
    }

    public void setRating(Integer rating) {
        put("rating", rating);
    }

    public ParseFile getPhotoFile() {
        return getParseFile("photo");
    }

    public void setPhotoFile(ParseFile file) {
        put("photo", file);
    }
    public Integer getRankCount()
    {
        return getInt("count");
    }
    public void addOneToRatingCount()
    {
        Integer tmp = getRankCount();
        put("count",tmp+1);
    }
}
