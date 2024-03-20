package com.example.bookstoreecommerceapi.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="orders")
public class Order {
    @Id
    @SequenceGenerator(name = "order_sequence", sequenceName = "order_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_sequence")
    private long id;
    @Embedded
    private Customer customer;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderDetail> orderDetails;
    private String status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<TimeLineEntry> timeLineEntries;
}
