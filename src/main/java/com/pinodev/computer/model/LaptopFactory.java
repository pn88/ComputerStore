package com.pinodev.computer.model;

import java.io.Serializable;
import java.time.Year;
import java.util.Objects;

public abstract class LaptopFactory implements Serializable, Comparable<LaptopFactory>, CsvConvertible {
    private String title;
    private String publisher;
    private Year year;

    LaptopFactory(String title, String publisher, int year) {
        this.title = title;
        this.publisher = publisher;
        this.year = Year.of(year);
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return title + ", " + publisher + ", " + year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LaptopFactory that = (LaptopFactory) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(publisher, that.publisher) &&
                Objects.equals(year, that.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, publisher, year);
    }

    @Override
    public int compareTo(LaptopFactory p) {
        return title.compareToIgnoreCase(p.title);
    }
}