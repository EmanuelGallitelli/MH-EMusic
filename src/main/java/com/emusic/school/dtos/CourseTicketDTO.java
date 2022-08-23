package com.emusic.school.dtos;

import com.emusic.school.models.CourseTicket;

public class CourseTicketDTO {

    private long id;

    private CourseDTO course;

    public CourseTicketDTO() {
    }

    public CourseTicketDTO(CourseTicket courseTicket) {
        this.id = courseTicket.getId();
        this.course = new CourseDTO(courseTicket.getCourse());
    }

    public long getId() {
        return id;
    }


    public CourseDTO getCourse() {
        return course;
    }

    public void setCourse(CourseDTO course) {
        this.course = course;
    }
}
