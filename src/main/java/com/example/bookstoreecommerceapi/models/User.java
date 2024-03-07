package com.example.bookstoreecommerceapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
public class User {
    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private long id;
    private String fullName;
    @NotBlank(message = "Vui lòng nhập tên đăng nhập")
    private String username;
    @Size(min = 8,message = "Mật khẩu bao gồm ít nhất 8 kí tự")
    private String password;
    @Email(message = "Vui lòng nhập đúng định dạng email")
    private String email;
    private String role;
    @Temporal(TemporalType.DATE)
    @Past(message = "Vui lòng nhập ngày trong quá khứ")
    private Date birthday;
    private String gender;
    private String address;
    private String phone;
    @Temporal(TemporalType.DATE)
    @Past(message = "Vui lòng nhập ngày trong quá khứ")
    private Date dateRegistered;
    private String status;
    private String avatar;
}
