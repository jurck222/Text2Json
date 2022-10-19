package com.example.txtjson.questions;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Data {
    private String data_type;

    ArrayList<DataBody>data_body;



    public Data(String data_type, ArrayList<DataBody> data_body) {
        this.data_type = data_type;
        this.data_body = data_body;
    }



    @Override
    public String toString() {
        return "Data{" +
                "data_type='" + data_type + '\'' +
                ", data_body=" + data_body +
                '}';
    }
}
