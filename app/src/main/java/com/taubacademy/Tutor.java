package com.taubacademy;

import android.util.Log;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.model.GraphUser;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ParseClassName("Tutor")
public class Tutor extends ParseObject {

    public Tutor() {
    }

    public static void updateAlTutorials() {
        ParseQuery query = ParseQuery.getQuery("Tutor");
        List<Tutor> tutors = null;
        try {
            tutors = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < tutors.size() - 2; i++) {
            tutors.get(i).setFeedback(tutors.get(i + 1), "Ziad is Fragment and LogIn Monster");
            tutors.get(i).setFeedback(tutors.get(i + 2), "very recommended");
            tutors.get(i).setAvailableTime(Arrays.asList("Tuesday 9:00-11:00", "Sunday 19:00-20:00", "Friday 7:00-8:00"));
            tutors.get(i).setRating(tutors.get(i + 1), 4);
        }
        for (Tutor t : tutors) {
            t.saveInBackground();
        }
    }

    static public void createNewTutor() {
        final Tutor t = new Tutor();
        t.setEmail("N\\A");
        t.setPhone("N\\A");
        t.setName("N\\A");
        t.setRating(0);
        t.setSalary(0);
        t.setAuthor(ParseUser.getCurrentUser());

        try {
            t.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ParseUser.getCurrentUser().put("Tutor", t);
        Request request = Request.newMeRequest(ParseFacebookUtils.getSession(), new Request.GraphUserCallback() {
            @Override
            public void onCompleted(GraphUser user, Response response) {
                if (user != null) {
                    final GraphUser currentUser = user;
                    try {
                        // Populate the JSON object
                        try {
                            t.setName(user.getFirstName() + " " + user.getLastName());
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                        if (user.getProperty("email") != null) {
                            t.setEmail((String) user.getProperty("email"));
                        }
                        t.put("UserId", currentUser.getId());
                        t.saveInBackground();

                    } catch (Exception e) {
                        Log.e("Facebook parsing data", "Error parsing returned user data.");
                    }

                } else if (response.getError() != null) {
                }
            }
        });
        request.executeAndWait();
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

    public List<Pair> getFeedbacks() {
        List<Pair> feedBacks = getList("FeedBacks");
        if (feedBacks == null) {
            return new ArrayList<Pair>();
        }
        return feedBacks;
    }

    public Boolean setFeedback(Tutor t, String Feedback) {
        if (Feedback == null || Feedback == "" || t.equals(this)) {
            return false;
        }
        Pair pair = new Pair(t, Feedback);
        pair.update();
        add("FeedBacks", pair);
        saveInBackground();
        pair.saveInBackground();
        return true;
    }

    public Boolean setRating(Tutor t, Integer Rating) {
        if (t.equals(this)) {
            return false;
        }
        Integer OverallRating = getRating();
        List<PairRatings> getAllRatings = getList("Ratings");
        if (getAllRatings == null) {
            getAllRatings = new ArrayList<PairRatings>();
        }
        OverallRating *= getAllRatings.size() == 0 ? 1 : getAllRatings.size();
        PairRatings pair = null;
        ParseQuery q = ParseQuery.getQuery("PairRatings");
        try {
            pair = (PairRatings) q.whereEqualTo("first", t).find().get(0);
        } catch (Exception e) {

        }

        for (PairRatings p : getAllRatings) {
            try {
                p.fetchIfNeeded();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (p.getFirst().equals(t)) {
                return false;
            }
        }

        pair = new PairRatings(t, Rating.toString());
        pair.update();
        getAllRatings.add(pair);
        OverallRating += Rating;
        OverallRating /= getAllRatings.size();
        setRating(OverallRating);
        put("Ratings", getAllRatings);
        saveInBackground();
        return true;
    }

    public void addAvilableTimes(String AvailableTimes) {
        addUnique("AvailableTime", AvailableTimes);
    }

    public List<String> getAvailableTime() {
        return getList("AvailableTime");
    }

    public void setAvailableTime(List<String> AvailableTimes) {
        put("AvailableTime", AvailableTimes);

    }
}
