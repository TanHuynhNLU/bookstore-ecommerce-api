package com.example.bookstoreecommerceapi.services.impl;

import com.example.bookstoreecommerceapi.configs.JwtService;
import com.example.bookstoreecommerceapi.dto.*;
import com.example.bookstoreecommerceapi.exceptions.IncorrectPasswordException;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private MyUserDetailService myUserDetailService;

    @Override
    public ResponseObject addNewUser(User newUser) throws UserAlreadyExistsException {
        Optional<User> userOptional = userRepository.findByUsername(newUser.getUsername());

        if (userOptional.isPresent()) {
            throw new UserAlreadyExistsException("Tài khoản đã tồn tại");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getCredentials() instanceof String)) {
            newUser.setRole("USER");
        } else {
            String token = (String) authentication.getCredentials();
            if (token.isEmpty()) newUser.setRole("USER");
            else {
                String username = jwtService.extractUsername(token);
                Optional<User> userRequestOptional = userRepository.findByUsername(username);
                if (userRequestOptional.isPresent() && userRequestOptional.get().getRole().equals("USER")) {
                    newUser.setRole("USER");
                }
            }
        }
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setDateRegistered(new Date());
        newUser.setStatus("Kích hoạt");
        User savedUser = userRepository.save(newUser);
        return new ResponseObject(HttpStatus.CREATED, "Thêm tài khoản thành công", savedUser);

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
    public PaginationResponse getUsersByUsernameContaining(String username, int page, int size, String sort) {
        Sort.Direction direction = sort.startsWith("-") ? Sort.Direction.DESC : Sort.Direction.ASC;
        String property = direction == Sort.Direction.DESC ? sort.substring(1) : sort;
        Pageable pageable = PageRequest.of(page, size, direction, property);
        Page<User> userPage = userRepository.findByUsernameContainingIgnoreCase(username, pageable);
        PaginationResponse paginationResponse = new PaginationResponse(userPage.getTotalElements(), userPage.getContent(), userPage.getTotalPages(), userPage.getNumber());
        return paginationResponse;
    }

    @Override
    public ResponseObject isUsernameExists(String username) {
        boolean isExists = userRepository.existsByUsername(username);
        ResponseObject responseObject;
        if (isExists) {
            responseObject = new ResponseObject(HttpStatus.OK, "Tên đăng nhập đã tồn tại", null);
        } else {
            responseObject = new ResponseObject(HttpStatus.NOT_FOUND, "Tên đăng nhập không tồn tại", null);
        }
        return responseObject;
    }

    @Override
    public ResponseObject login(LoginForm loginForm) {
        Authentication authentication = authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));
        if (authentication.isAuthenticated()) {
            User user = userRepository.findByUsername(loginForm.getUsername()).get();
            String token = jwtService.generateToken(myUserDetailService.loadUserByUsername(loginForm.getUsername()));
            UserLogin userLogin = UserLogin.builder()
                    .user(user)
                    .token(token)
                    .build();
            return new ResponseObject(HttpStatus.OK, "Thành công", userLogin);
        } else {
            return new ResponseObject(HttpStatus.NOT_FOUND, "Sai tên đăng nhập hoặc mật khẩu", null);
        }

    }

    @Override
    public ResponseObject updateUser(long id, User user) throws UserNotFoundException {
        Optional<User> optionalUserDB = userRepository.findById(id);
        if (!optionalUserDB.isPresent()) throw new UserNotFoundException("Tài khoản không tồn tại");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDB = optionalUserDB.get();
        if (authentication != null && (authentication.getCredentials() instanceof String)) {
            String token = (String) authentication.getCredentials();
            if (!token.isEmpty()) {
                String username = jwtService.extractUsername(token);
                Optional<User> userRequestOptional = userRepository.findByUsername(username);
                if (userRequestOptional.isPresent() && userRequestOptional.get().getRole().equals("ADMIN")) {
                    userDB.setRole(user.getRole());
                }
            }
        }
        userDB.setAvatar(user.getAvatar());
        userDB.setBirthday(user.getBirthday());
        userDB.setAddress(user.getAddress());
        userDB.setEmail(user.getEmail());
        userDB.setGender(user.getGender());
        userDB.setFullName(user.getFullName());
        userDB.setPhone(user.getPhone());
        userDB.setStatus(user.getStatus());
        User savedUser = userRepository.save(userDB);
        return new ResponseObject(HttpStatus.OK, "Cập nhật tài khoản thành công", savedUser);
    }

    @Override
    public ResponseObject changePassword(long id, ChangePasswordRequest request) throws UserNotFoundException, IncorrectPasswordException {
        Optional<User> optionalUserDB = userRepository.findById(id);
        if (!optionalUserDB.isPresent()) {
            throw new UserNotFoundException("Tài khoản không tồn tại");
        }
        User userDB = optionalUserDB.get();
        if (!passwordEncoder.matches(request.getOldPassword(), userDB.getPassword())) {
            throw new IncorrectPasswordException("Mật khẩu không đúng");
        }
        userDB.setPassword(passwordEncoder.encode(request.getNewPassword()));
        User savedUser = userRepository.save(userDB);
        return new ResponseObject(HttpStatus.OK, "Thay đổi mật khẩu thành công", savedUser);
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
