package com.example.ic_13;
/*
In Class Assignment 13
IC-13
Nicholas Osaka and Alexandria Benedict (Group 22)
 */

import java.util.List;

public class Locations {
    List<Location> points;
    String title;

    public Locations(List<Location> points, String title) {
        this.points = points;
        this.title = title;
    }

    public List<Location> getPoints() {
        return points;
    }

    public void setPoints(List<Location> points) {
        this.points = points;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Locations{" +
                "points=" + points +
                ", title='" + title + '\'' +
                '}';
    }
}
