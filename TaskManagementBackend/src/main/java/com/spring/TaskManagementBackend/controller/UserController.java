package com.spring.TaskManagementBackend.controller;

import com.spring.TaskManagementBackend.exception.DuplicateDataException;
import com.spring.TaskManagementBackend.pojo.User;
import com.spring.TaskManagementBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers()
    {
        return userService.getAllUsers();
    }

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (DuplicateDataException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/getAssigneesAndCreators")
    public List<String> getAssigneesAndCreators() {
        return userService.getAssigneesAndCreators();
    }

    @PutMapping("/updateStatus/{userId}")
    public ResponseEntity<User> updateUserStatus(@PathVariable Long userId, @RequestBody Map<String, User.Status> request) {
        String status = request.get("status").toString();
        User updatedUser = userService.updateUserStatus(userId, User.Status.valueOf(status));
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/updateUsername/{id}")
    public ResponseEntity<User> updateUsername(@PathVariable Long id, @RequestBody User updatedUser) {
        return userService.updateUsername(id, updatedUser);
    }
}
