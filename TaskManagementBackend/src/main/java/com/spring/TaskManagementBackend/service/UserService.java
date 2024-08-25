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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        validateUser(user);
        return userRepository.save(user);
    }

    public ResponseEntity<User> updateUsernameAndMobile(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    validateUserForUpdate(user, updatedUser);
                    user.setName(updatedUser.getName());
                    user.setMobileno(updatedUser.getMobileno());
                    User savedUser = userRepository.save(user);
                    return ResponseEntity.ok(savedUser);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private void validateUser(User user) {
        Optional<User> existingUserWithEmail = userRepository.findByEmail(user.getEmail());
        Optional<User> existingUserWithMobile = userRepository.findByMobileno(user.getMobileno());

        if (existingUserWithEmail.isPresent()) {
            throw new DuplicateDataException("Email already exists");
        }
        if (existingUserWithMobile.isPresent()) {
            throw new DuplicateDataException("Mobile number already exists");
        }
    }

    private void validateUserForUpdate(User existingUser, User updatedUser) {
        // Ensure email uniqueness
        if (!existingUser.getEmail().equals(updatedUser.getEmail())) {
            Optional<User> existingUserWithEmail = userRepository.findByEmail(updatedUser.getEmail());
            if (existingUserWithEmail.isPresent()) {
                throw new DuplicateDataException("Email already exists");
            }
        }

        // Ensure mobile number uniqueness
        if (!existingUser.getMobileno().equals(updatedUser.getMobileno())) {
            Optional<User> existingUserWithMobile = userRepository.findByMobileno(updatedUser.getMobileno());
            if (existingUserWithMobile.isPresent()) {
                throw new DuplicateDataException("Mobile number already exists");
            }
        }
    }

    public User updateUserStatus(Long userId, User.Status status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + userId));
        user.setStatus(status);
        return userRepository.save(user);
    }

    public List<String> getAssigneesAndCreators() {
        return userRepository.findAll().stream()
                .filter(user -> user.getStatus() == User.Status.ACTIVE)
                .map(User::getName)
                .collect(Collectors.toList());
    }
}
