package com.emusic.school.dtos;

import com.emusic.school.models.Client;

public class ClientReviewDTO {
    private String firstName;
    private String reviewCourse;
    private String course;

    public ClientReviewDTO() {
    }

    public ClientReviewDTO(Client client) {
        this.firstName = client.getFirstName();
        this.reviewCourse = client.getReviewCourse();
        this.course = client.getCourse();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getReviewCourse() {
        return reviewCourse;
    }

    public void setReviewCourse(String reviewCourse) {
        this.reviewCourse = reviewCourse;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
