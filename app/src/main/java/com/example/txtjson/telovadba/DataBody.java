package com.example.txtjson.telovadba;



import java.util.ArrayList;

public class DataBody {
    public DataBody(ArrayList<com.example.txtjson.telovadba.excerciseEnduranceWeeklyPlan> excerciseEnduranceWeeklyPlan, ArrayList<com.example.txtjson.telovadba.excerciseResistanceWeeklyPlan> excerciseResistanceWeeklyPlan) {
        this.excerciseEnduranceWeeklyPlan = excerciseEnduranceWeeklyPlan;
        this.excerciseResistanceWeeklyPlan = excerciseResistanceWeeklyPlan;
    }

    ArrayList<com.example.txtjson.telovadba.excerciseEnduranceWeeklyPlan> excerciseEnduranceWeeklyPlan;
    ArrayList<com.example.txtjson.telovadba.excerciseResistanceWeeklyPlan>excerciseResistanceWeeklyPlan;
}
