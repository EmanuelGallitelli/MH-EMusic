package com.emusic.school.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private Double totalPrice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    @OneToMany(mappedBy = "ticket", fetch = FetchType.EAGER)
    private Set<PurchaseOrder> purchaseOrder = new HashSet<>();

    @OneToMany(mappedBy = "ticket", fetch = FetchType.EAGER)
    private Set<CourseTicket> courseTickets = new HashSet<>();


    public Ticket() {}

    public Ticket(double totalPrice, Client client) {
        this.totalPrice = totalPrice;
        this.client = client;
    }

    public Long getId() {return id;}

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Client getClient() {return client;}
    public void setClient(Client client) {this.client = client;}

    public Set<PurchaseOrder> getPurchaseOrder() {return purchaseOrder;}
    public void setPurchaseOrder(Set<PurchaseOrder> purchaseOrder) {this.purchaseOrder = purchaseOrder;}

    public Set<CourseTicket> getCourseTickets() {
        return courseTickets;
    }
    public void setCourseTickets(Set<CourseTicket> courseTickets) {
        this.courseTickets = courseTickets;

    }
}
