package com.spring.TaskManagementBackend.service;

import com.spring.TaskManagementBackend.exception.DuplicateDataException;
import com.spring.TaskManagementBackend.pojo.User;
import com.spring.TaskManagementBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        // Check if the email is already taken
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateDataException("Email already exists!");
        }

        // Check if the mobile number is already taken
        if (userRepository.findByMobileno(user.getMobileno()).isPresent()) {
            throw new DuplicateDataException("Mobile number already exists!");
        }

        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateDataException("Email or Mobile Number already exists!");
        }
    }

    public List<String> getAssigneesAndCreators() {
        return userRepository.findAll().stream()
                .filter(user -> user.getStatus() == User.Status.ACTIVE)
                .map(User::getName)
                .collect(Collectors.toList());
    }

    public User updateUserStatus(Long userId, User.Status status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + userId));
        user.setStatus(status);
        return userRepository.save(user);
    }

    public ResponseEntity<User> updateUsername(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(updatedUser.getName()); // Only updating the name
                    User savedUser = userRepository.save(user);
                    return ResponseEntity.ok(savedUser);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
