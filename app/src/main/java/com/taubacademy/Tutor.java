package com.taubacademy;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ParseClassName("Tutor")
public class Tutor extends ParseObject {

    public Tutor() {
        super("Tutor");
    }

    public String getName() {
        return getString("title");
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

    public void SetPhoneNumber(String Phone) {
        put("Phone", Phone);
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

    public Integer getRankCount() {
        return getInt("count");
    }

    public void addOneToRatingCount() {
        Integer tmp = getRankCount();
        put("count", tmp + 1);
    }

    public void addCourse(Course c) {
        addUnique("Courses", c);
    }

    public List<Course> getAllCourses() {
        return getList("Courses");
    }

    public Integer getSalary() {
        return getInt("Salary");
    }

    public void setSalary(Integer Salary) {
        put("Salary", Salary);
    }

    public String getPhone() {
        return getString("Phone");
    }

    public void setPhone(String Salary) {
        put("Phone", Salary);
    }
    public Map<String,String> getFeedbacks()
    {
        Map<String,String> feedBacks = getMap("FeedBacks");
        return feedBacks != null ? feedBacks : new HashMap<String, String>();
    }
    public Boolean setFeedback(Tutor t ,String Feedback)
    {
        if(Feedback  == null || Feedback == "" || t.equals(this))
        {
            return  false;
        }
        Map<String,String> feedBack = new HashMap<String, String>();
        feedBack.put(t.getName(),Feedback);
        addUnique("FeedBacks", feedBack);
        saveInBackground();
        return true;
    }
    public Boolean setRating(Tutor t ,Integer Rating)
    {
        if(t.equals(this))
        {
            return  false;
        }
        Integer OverallRating = getRating();
        Map<String,String> getAllRatings = getMap("Ratings");
        if(getAllRatings == null)
        {
            getAllRatings = new HashMap<String, String>();
        }
        OverallRating *= getAllRatings.size() == 0 ? 1 : getAllRatings.size();
        try {
            OverallRating -= Integer.parseInt(getAllRatings.get(t.getName()));
        }catch(Exception c)
        {

        }
        OverallRating += Rating;
        getAllRatings.put(t.getName(),Rating.toString());
        put("Ratings",getAllRatings);
        saveInBackground();
        return true;
    }
}
