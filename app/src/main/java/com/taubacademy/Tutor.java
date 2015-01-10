package com.taubacademy;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

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
    public List<Pair> getFeedbacks()
    {
        List<Pair> feedBacks = getList("FeedBacks");
        if(feedBacks == null)
        {
            return new ArrayList<Pair>();
        }
        return feedBacks;
    }
    public Boolean setFeedback(Tutor t ,String Feedback)
    {
        if(Feedback  == null || Feedback == "" || t.equals(this))
        {
            return  false;
        }
        Pair pair = null;
        ParseQuery q = ParseQuery.getQuery("Pair");
        try {
            pair = (Pair) q.whereEqualTo("first",t).find().get(0);
        } catch (Exception e) {
            pair = new Pair(t,Feedback);
            pair.update();
        }

        addUnique("FeedBacks", pair);
        saveInBackground();
        return true;
    }
    public Boolean setRating(Tutor t ,Integer Rating) {
        if(t.equals(this))
        {
            return  false;
        }
        Integer OverallRating = getRating();
        List<PairRatings> getAllRatings = getList("Ratings");
        if(getAllRatings == null)
        {
            getAllRatings = new ArrayList<PairRatings>();
        }
        OverallRating *= getAllRatings.size() == 0 ? 1 : getAllRatings.size();
        PairRatings pair = null;
        ParseQuery q = ParseQuery.getQuery("PairRatings");
        try {
            pair = (PairRatings) q.whereEqualTo("first",t).find().get(0);
        } catch (Exception e) {
            pair = new PairRatings(t,Rating.toString());
            pair.update();
        }

        Boolean flag =false;
        for(PairRatings p : getAllRatings)
            {
                if(p.equals(pair))
                {
                    OverallRating -= Integer.parseInt(p.getSecond());
                    flag = true;
                    break;
                }
            }
        if(flag == false)
        {
            getAllRatings.add(pair);
        }
        OverallRating += Rating;
        OverallRating /= getAllRatings.size();
        setRating(OverallRating);
        put("Ratings", getAllRatings);
        saveInBackground();
        return true;
    }
    public void addAvilableTimes(String AvailableTimes)
    {
        addUnique("AvailableTime",AvailableTimes);
    }
    public List<String> getAvailableTime()
    {
        return getList("AvailableTime");
    }
    public static void updateAlTutorials()
    {
        ParseQuery query = ParseQuery.getQuery("Tutor");
        List<Tutor> tutors = null;
        try {
            tutors = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for(int i=0;i<tutors.size()-1;i++)
        {
            tutors.get(i).setFeedback(tutors.get(i+1),"Ziad is Fragment and LogIn Monster");
            tutors.get(i).setRating(tutors.get(i+1),4);
        }
        for(Tutor t : tutors)
        {
            t.saveInBackground();
        }
    }
}
