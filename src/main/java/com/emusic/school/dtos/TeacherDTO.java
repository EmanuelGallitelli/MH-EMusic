package com.emusic.school.dtos;

import com.emusic.school.models.Course;
import com.emusic.school.models.Teacher;

import java.util.HashSet;
import java.util.Set;

public class TeacherDTO {
    private Long id;

    private String firstName, lastName, email, subject;


    public TeacherDTO() {
    }
    public TeacherDTO(Teacher teacher) {
        this.id = teacher.getId();
        this.firstName = teacher.getFirstName();
        this.lastName = teacher.getLastName();
        this.email = teacher.getEmail();
        this.subject = teacher.getSubject();
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {return subject;}

    public void setSubject(String subject) {this.subject = subject;}
}
