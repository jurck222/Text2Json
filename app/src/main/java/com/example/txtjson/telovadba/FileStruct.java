package com.example.txtjson.telovadba;

import com.example.txtjson.telovadba.Data;

public class FileStruct {
    private String timestamp;
    private String title;
    Data data;

    public FileStruct(String timestamp, String title, Data data) {
        this.timestamp = timestamp;
        this.title = title;
        this.data = data;
    }

    @Override
    public String toString() {
        return "FileStruct{" +
                "timestamp='" + timestamp + '\'' +
                ", title='" + title + '\'' +
                ", data=" + data +
                '}';
    }
}
