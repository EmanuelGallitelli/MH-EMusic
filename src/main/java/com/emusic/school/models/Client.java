package com.emusic.school.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean isActive;
    private boolean isVerified;
    private String token;
    private String reviewCourse;
    private String course;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Ticket> tickets = new HashSet<>();

    public Client() {}

    public Client(String firstName, String lastName, String email, String password, boolean isActive,boolean isVerified) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.isVerified = isVerified;
    }

    public Long getId() {return id;}

    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public boolean isActive() {return isActive;}
    public void setActive(boolean active) {isActive = active;}

    public Set<Ticket> getTickets() {return tickets;}
    public void setTickets(Set<Ticket> tickets) {this.tickets = tickets;}

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void deleteToken(){
        this.token = "";
    }

    public String getReviewCourse() {
        return reviewCourse;
    }

    public void setReviewCourse(String reviewCurse) {
        this.reviewCourse = reviewCurse;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String curse) {
        this.course = curse;
    }
}