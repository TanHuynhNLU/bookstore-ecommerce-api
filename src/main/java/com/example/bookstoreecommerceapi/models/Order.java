package com.example.bookstoreecommerceapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", fetch = FetchType.EAGER)
    private List<TimeLineEntry> timeLineEntries;
    private Date dateCreated;
    private int shippingTax;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String note;
    @Transient
    private long totalPrice;

    public String getDateCreated() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        return sdf.format(dateCreated);
    }

    public Date getDateCreatedOrdinal() {
        return this.dateCreated;
    }

    public long getTotalPrice() {
        if(orderDetails==null) return 0;
        for (OrderDetail orderDetail : orderDetails) {
            totalPrice += orderDetail.getTotalPrice();
        }
        totalPrice += shippingTax;
        return totalPrice;
    }
}
