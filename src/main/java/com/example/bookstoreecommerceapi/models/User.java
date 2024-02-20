package com.example.bookstoreecommerceapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private long id;
    private String fullName;
    @NotBlank(message = "Vui lòng nhập tên đăng nhập")
    private String username;
    @Min(value = 8,message = "Mật khẩu bao gồm ít nhất 8 kí tự")
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
    private String avatar;
}
