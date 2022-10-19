package com.example.txtjson.questions;

import java.util.ArrayList;

public class DataBody {
    String id;
    private String type;
    private Content content;
    private ArrayList<Options> options;
    Advice advice;
    String correctAnswer;
    ArrayList<com.example.txtjson.telovadba.excerciseEnduranceWeeklyPlan> excerciseEnduranceWeeklyPlan;
    ArrayList<com.example.txtjson.telovadba.excerciseResistanceWeeklyPlan>excerciseResistanceWeeklyPlan;

    public DataBody(String id, String type, Content content, ArrayList<Options> options, Advice advice, String correctAnswer) {
        this.id = id;
        this.type = type;
        this.content = content;
        this.options = options;
        this.advice = advice;
        this.correctAnswer=correctAnswer;
    }

    public DataBody(ArrayList<com.example.txtjson.telovadba.excerciseEnduranceWeeklyPlan> excerciseEnduranceWeeklyPlan, ArrayList<com.example.txtjson.telovadba.excerciseResistanceWeeklyPlan> excerciseResistanceWeeklyPlan) {
        this.excerciseEnduranceWeeklyPlan = excerciseEnduranceWeeklyPlan;
        this.excerciseResistanceWeeklyPlan = excerciseResistanceWeeklyPlan;
    }

    @Override
    public String toString() {
        return "DataBody{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", content=" + content +
                ", options=" + options +
                ", advice=" + advice +
                '}';
    }
}
