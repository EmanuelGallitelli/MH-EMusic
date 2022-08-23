package com.emusic.school.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String level,name;

    private int lessons,duration;
    private Double price;
    private boolean active;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="teacher_id")
    private Teacher teacher;

    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
    private Set<CourseTicket> courseTickets = new HashSet<>();
    public Course() {
    }

    public Course(String level, String name, int lessons, Double price, int duration, boolean active,Teacher teacher) {
        this.level = level;
        this.name = name;
        this.lessons = lessons;
        this.price = price;
        this.duration = duration;
        this.active = active;
        this.teacher = teacher;
    }

    public Long getId() {
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

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<CourseTicket> getCourseTickets() {
        return courseTickets;
    }
    public void setCourseTickets(Set<CourseTicket> courseTickets) {
        this.courseTickets = courseTickets;
    }


}
