package com.example.txtjson;

public class test {
    private String ime;
    private String priimek;

    @Override
    public String toString() {
        return "test{" +
                "ime='" + ime + '\'' +
                ", priimek='" + priimek + '\'' +
                '}';
    }

    public test(String ime, String priimek) {
        this.ime = ime;
        this.priimek=priimek;
    }
}
