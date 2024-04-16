// package com.backend.online_qwiz.secuirity.auth;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// import com.backend.online_qwiz.model.Role;
// import com.backend.online_qwiz.model.User;
// import com.backend.online_qwiz.repository.UserRepository;

// @Service
// public class UserService {

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private PasswordEncoder passwordEncoder;

//     public User register(RegisterRequest request) {
//         // Create a new user
//         User user = User.builder()
//             .firstName(request.getFirstName())
//             .lastName(request.getLastName())
//             .email(request.getEmail())
//             .password(passwordEncoder.encode(request.getPassword()))
//             .role(Role.STUDENT)
//             .build();

//         return userRepository.save(user);
//     }
// }
