package com.emusic.school.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashMap;

@Entity
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="ticket_id")
    private Ticket ticket;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="merch_id")
    private Merch merch;

    private Integer quantity;

    public PurchaseOrder() {}

    public PurchaseOrder(Ticket ticket, Merch merch, Integer quantity) {
        this.ticket = ticket;
        this.merch = merch;
        this.quantity = quantity;
    }

    public Long getId() {return id;}

    public Ticket getTicket() {return ticket;}
    public void setTicket(Ticket ticket) {this.ticket = ticket;}

    public Merch getMerch() {return merch;}
    public void setMerch(Merch merch) {this.merch = merch;}

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
