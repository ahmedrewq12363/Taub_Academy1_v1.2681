package com.taubacademy;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahmed on 1/9/2015.
 */
@ParseClassName("Course")
public class Course extends ParseObject{
    public static ArrayList<Tutor> getTutorsOfCourse(Integer CourseId) throws ParseException {
        ParseQuery query = new ParseQuery("Course");
        ArrayList<String> strings = new ArrayList<String>();
        strings.add("tutors");
        query.include("tutors");
        query.whereEqualTo("CourseId",CourseId);
        List<Course> courses = query.find();
        ArrayList<Tutor> tutors = new ArrayList<Tutor>();
        for(Course t : courses)
        {
            if(t.getList("tutors") == null)
            {
                break;
            }
            for(Object ax : t.getList("tutors"))
            {
                tutors.add((Tutor)ax);
            }
        }
        return tutors;
    }
    public Course()
    {

    }
    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("title", name);
    }
    public Integer getCourseId() {
        return getInt("CourseId");
    }

    public void setCourseId(Integer courseId) {
        put("CourseId", courseId);
    }
    public java.util.List<Tutor> getAllTutorsOfThisCourse()
    {
        return getList("tutors");
    }
    public void addTutorToList(Tutor tutor)
    {
        addUnique("tutors", tutor);
    }
}
