package com.taubacademy;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Ahmed on 1/9/2015.
 */
@ParseClassName("Course")
public class Course extends ParseObject{
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
        addUnique("tutors",tutor);
    }
}
