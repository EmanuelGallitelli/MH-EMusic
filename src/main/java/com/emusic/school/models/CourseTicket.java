package com.emusic.school.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class CourseTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="ticket_id")
    private Ticket ticket;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="course_id")
    private Course course;

    public CourseTicket() {
    }

    public CourseTicket(Ticket ticket, Course course) {
        this.ticket = ticket;
        this.course = course;
    }

    public long getId() {
        return id;
    }


    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
