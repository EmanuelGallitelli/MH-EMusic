package com.emusic.school.dtos;

import com.emusic.school.models.Ticket;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TicketDTO {
    private long id;
    private double totalPrice;
    private Set<PurchaseOrderDTO> purchaseOrder = new HashSet<>();

    private Set<CourseTicketDTO> courseTickets = new HashSet<>();

    public TicketDTO() {
    }

    public TicketDTO(Ticket ticket) {
        this.id = ticket.getId();
        this.totalPrice = ticket.getTotalPrice();
        this.purchaseOrder = ticket.getPurchaseOrder().stream().map(PurchaseOrderDTO::new).collect(Collectors.toSet());
        this.courseTickets = ticket.getCourseTickets().stream().map(courseTicket -> new CourseTicketDTO(courseTicket)).collect(Collectors.toSet());
    }

    public long getId() {
        return id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Set<PurchaseOrderDTO> getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(Set<PurchaseOrderDTO> purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public Set<CourseTicketDTO> getCourseTickets() {
        return courseTickets;
    }
    public void setCourseTickets(Set<CourseTicketDTO> courseTickets) {
        this.courseTickets = courseTickets;
    }
}
