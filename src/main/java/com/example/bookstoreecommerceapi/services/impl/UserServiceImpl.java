package com.example.bookstoreecommerceapi.services.impl;

import com.example.bookstoreecommerceapi.dto.PaginationResponse;
import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.exceptions.UserAlreadyExistsException;
import com.example.bookstoreecommerceapi.exceptions.UserNotFoundException;
import com.example.bookstoreecommerceapi.models.User;
import com.example.bookstoreecommerceapi.repositories.UserRepository;
import com.example.bookstoreecommerceapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseObject addNewUser(User newUser) throws UserAlreadyExistsException {
        Optional<User> userOptional = userRepository.findByUsername(newUser.getUsername());
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistsException("Tài khoản đã tồn tại");
        } else {
            userRepository.save(newUser);
            return new ResponseObject(HttpStatus.CREATED, "Thêm tài khoản thành công", newUser);
        }
    }

    @Override
    public ResponseObject getAllUsers() {
        List<User> users = userRepository.findAll();
        return new ResponseObject(HttpStatus.OK, "Thành công", users);
    }

    @Override
    public ResponseObject getUserById(long id) throws UserNotFoundException {
        Optional<User> userDB = userRepository.findById(id);
        if (userDB.isPresent()) {
            return new ResponseObject(HttpStatus.OK, "Thành công", userDB.get());
        } else {
            throw new UserNotFoundException("Tài khoản không tồn tại");
        }
    }

    @Override
    public PaginationResponse getAllUsersPaginationAndSorting(int page, int size, String sort) {
        Sort.Direction direction = sort.startsWith("-") ? Sort.Direction.DESC : Sort.Direction.ASC;
        String property = direction == Sort.Direction.DESC ? sort.substring(1) : sort;
        Pageable pageable = PageRequest.of(page, size, direction, property);
        Page<User> userPage = userRepository.findAll(pageable);
        PaginationResponse paginationResponse =
                new PaginationResponse(userPage.getTotalElements(), userPage.getContent(), userPage.getTotalPages(), userPage.getNumber());
        return paginationResponse;
    }

    @Override
    public PaginationResponse getUsersByUsernameContaining(String username, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<User> userPage = userRepository.findByUsernameContainingIgnoreCase(username,pageable);
        PaginationResponse paginationResponse = new PaginationResponse(userPage.getTotalElements(),userPage.getContent(),userPage.getTotalPages(),userPage.getNumber());
        return paginationResponse;
    }

    @Override
    public ResponseObject isUsernameExists(String username) {
        boolean isExists = userRepository.existsByUsername(username);
        ResponseObject responseObject = new ResponseObject();
        if (isExists) {
            responseObject = new ResponseObject(HttpStatus.OK, "Tên đăng nhập đã tồn tại", null);
        } else {
            responseObject = new ResponseObject(HttpStatus.NOT_FOUND, "Tên đăng nhập không tồn tại", null);
        }
        return responseObject;
    }

    @Override
    public ResponseObject updateUser(long id, User user) throws UserNotFoundException {
        Optional<User> optionalUserDB = userRepository.findById(id);
        if (!optionalUserDB.isPresent()) throw new UserNotFoundException("Tài khoản không tồn tại");
        User userDB = optionalUserDB.get();
        userDB.setAvatar(user.getAvatar());
        userDB.setBirthday(user.getBirthday());
        userDB.setAddress(user.getAddress());
        userDB.setEmail(user.getEmail());
        userDB.setGender(user.getGender());
        userDB.setRole(user.getRole());
        userDB.setFullName(user.getFullName());
        userDB.setPhone(user.getPhone());
        return new ResponseObject(HttpStatus.OK, "Cập nhật tài khoản thành công", userRepository.save(userDB));
    }

    @Override
    public ResponseObject deleteUser(long id) throws UserNotFoundException {
        boolean isExists = userRepository.existsById(id);
        if (!isExists) {
            throw new UserNotFoundException("Tài khoản không tồn tại");
        }
        userRepository.deleteById(id);
        return new ResponseObject(HttpStatus.OK, "Xóa tài khoản thành công", null);
    }

}
