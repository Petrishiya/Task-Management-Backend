package com.spring.TaskManagementBackend.controller;


import com.spring.TaskManagementBackend.pojo.Task;
import com.spring.TaskManagementBackend.service.TaskService;
import com.spring.TaskManagementBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin("http://localhost:4200")
public class TaskController {

    @Autowired
    private TaskService taskService;
    private UserService userService;

    @GetMapping("/getTasks")
    public List<Task> getAllTasks() {

        System.out.println("Tasks");
        return taskService.getAllTasks();
    }

    @PostMapping("/createTask")
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        try {
            Task createdTask = taskService.createTask(task);
            return new ResponseEntity<>(createdTask, HttpStatus.CREATED);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task task) {
        task.setId(id);
        return taskService.updateTask(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id)
    {
        taskService.deleteTask(id);
    }


    @GetMapping("/assignees")
    public List<String> getAssignees() {
        System.out.println("Assignees" + taskService.getAssignees());
        return taskService.getAssignees();
    }

    @GetMapping("/creators")
    public List<String> getCreators() {
        return taskService.getCreators();
    }

    @GetMapping("/statuses")
    public List<String> getStatuses() {
        return taskService.getStatuses();
    }



}
