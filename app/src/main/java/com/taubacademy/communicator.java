package com.taubacademy;

import com.parse.ParseException;

/**
 * Created by ziad on 12/26/2014.
 */
public interface communicator {
    public void respond(String i) throws ParseException;

    public void ChangeFrag(Tutor t);
        }
