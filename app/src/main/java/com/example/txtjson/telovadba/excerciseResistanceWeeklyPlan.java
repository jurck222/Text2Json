package com.example.txtjson.telovadba;

import java.util.ArrayList;

public class excerciseResistanceWeeklyPlan {
    String id;
    String type;
    Description description;
    ArrayList<Photo> listOfPhotos;

    public excerciseResistanceWeeklyPlan(String id, String type, Description description, ArrayList<Photo> listOfPhotos) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.listOfPhotos = listOfPhotos;
    }
}
