package com.example.bookstoreecommerceapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contact {
    @Id
    @SequenceGenerator(name = "contact_sequence", sequenceName = "contact_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_sequence")
    private long id;
    @NotBlank(message = "Vui lòng nhập tên")
    private String name;
    @Email(message = "Vui lòng nhập email")
    private String email;
    @NotBlank(message = "Vui lòng nhập tiêu đề")
    private String title;
    @NotBlank(message = "Vui lòng nhập nội dung")
    private String content;
    private String reply;
    private Date createdDate;
    private Date repliedDate;
    private String status;
}
