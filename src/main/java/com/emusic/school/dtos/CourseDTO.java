package com.emusic.school.dtos;

import com.emusic.school.models.Course;

public class CourseDTO {

    private long id;

    private String level, name;

    private int lessons, duration;
    private Double price;
    private boolean active;

    private TeacherDTO teacher;

    public CourseDTO() {
    }

    public CourseDTO(Course course) {
        this.id = course.getId();
        this.level = course.getLevel();
        this.name = course.getName();
        this.lessons = course.getLessons();
        this.price = course.getPrice();
        this.duration = course.getDuration();
        this.active = course.isActive();
        this.teacher = new TeacherDTO(course.getTeacher());
    }

    public long getId() {
        return id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLessons() {
        return lessons;
    }

    public void setLessons(int lessons) {
        this.lessons = lessons;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public TeacherDTO getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherDTO teacher) {
        this.teacher = teacher;
    }
}
