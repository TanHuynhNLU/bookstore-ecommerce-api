package com.example.bookstoreecommerceapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetail {
    @Id
    @SequenceGenerator(name = "orderDetail_sequence", sequenceName = "orderDetail_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderDetail_sequence")
    private long id;
    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @JsonIgnore
    private Order order;
    private int quantity;
    @Transient
    private long totalPrice;

    public long getTotalPrice() {
        if(book==null) return 0;
        totalPrice = book.getPrice() * quantity;
        return totalPrice;
    }
}
