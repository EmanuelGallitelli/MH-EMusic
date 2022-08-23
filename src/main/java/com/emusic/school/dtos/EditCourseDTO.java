package com.emusic.school.dtos;

public class EditCourseDTO {
    private long id;
    private String name;
    private String level;
    private int lessons;
    private double price;
    private int duration;
    private long teacherId;

    public EditCourseDTO() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLevel() {
        return level;
    }

    public int getLessons() {
        return lessons;
    }

    public double getPrice() {
        return price;
    }

    public int getDuration() {
        return duration;
    }

    public long getTeacherId() {
        return teacherId;
    }
}
