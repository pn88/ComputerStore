package com.pinodev.computer.model;

public class Laptop extends LaptopFactory {
    public static final String TYPE = "Laptop";

    private String language;

    public Laptop(String producer, String hardDiskType, String language, int year) {
        super(producer, hardDiskType, year);
        this.language = language;
    }

    @Override
    public String toCsv() {
        return (TYPE + ";")  ;                    }



}