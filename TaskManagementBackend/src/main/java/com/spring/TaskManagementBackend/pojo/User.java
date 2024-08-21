package com.spring.TaskManagementBackend.pojo;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

@Table(name="USERS",uniqueConstraints =
        @UniqueConstraint(columnNames = {"email","mobileno"}))

public class User {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(unique = true,nullable = false)
    private String email;
    @Column(unique = true,nullable = false)
    private Long mobileno;


    @Enumerated(EnumType.STRING)
    @JsonProperty("status")
    private Status status;
    public enum Status {
        ACTIVE,
        INACTIVE
    }

    public User() {
        this.status = Status.ACTIVE;
    }
    // Getters and Setters
}
