package com.example.txtjson.telovadba;

import com.example.txtjson.telovadba.Description;

public class excerciseEnduranceWeeklyPlan {
    String id;
    String type;
    Description description;

    public excerciseEnduranceWeeklyPlan(String id, String type, Description description) {
        this.id = id;
        this.type = type;
        this.description = description;
    }
}
