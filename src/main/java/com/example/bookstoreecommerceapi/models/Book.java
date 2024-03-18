package com.example.bookstoreecommerceapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @SequenceGenerator(name = "book_sequence", sequenceName = "book_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_sequence")
    private long id;
    private String name;
    private String genre;
    @Min(value = 0, message = "Số lượng phải là số dương")
    private int stock;
    @Min(value = 0, message = "Giá bán phải là số dương")
    private long price;
    private long sales;
    private String author;
    private String publisher;
    private int numberOfPage;
    @Min(value = 0, message = "Năm xuất bản phải là số dương")
    private int published;
    private String status;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
    private String image;
}
