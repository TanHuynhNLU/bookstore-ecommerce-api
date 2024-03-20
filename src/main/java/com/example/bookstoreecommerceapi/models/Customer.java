package com.example.bookstoreecommerceapi.models;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    private String name;
    @NotBlank(message = "Vui lòng nhập số điện thoại")
    @Email(message = "Vui lòng nhập đúng định dạng email")
    private String email;
    @NotBlank(message = "Vui lòng nhập số điện thoại")
    @Min(value = 10, message = "Vui lòng nhập đủ 10 chữ số")
    @Max(value = 10, message = "Vui lòng nhập đủ 10 chữ số")
    private String phone;
    private String shippingAddress;
    private String payment;
    private String avatar;
}
