    package com.spring.TaskManagementBackend.pojo;
    
    
    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.Setter;
    @Entity
    @Getter
    @Setter
    @Table(name="TASKS")

    public class Task {






        @Id


        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        private String description;
        private String assignee;
        private String creator;
        private String status;
    
        // Getters and Setters
    }
