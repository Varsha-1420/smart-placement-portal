package com.placement.portal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * Entity representing a student in the campus placement portal.
 */
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Roll number is mandatory")
    @Size(max = 50, message = "Roll number must not exceed 50 characters")
    @Column(name = "roll_number", nullable = false, unique = true, length = 50)
    private String rollNumber;

    @NotBlank(message = "Full name is mandatory")
    @Size(max = 100, message = "Full name must not exceed 100 characters")
    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @NotBlank(message = "Branch is mandatory")
    @Column(nullable = false, length = 30)
    private String branch;

    @NotNull(message = "CGPA is mandatory")
    @DecimalMin(value = "0.0", inclusive = true, message = "CGPA must be at least 0.0")
    @DecimalMax(value = "10.0", inclusive = true, message = "CGPA cannot exceed 10.0")
    @Column(nullable = false)
    private Double cgpa;

    @NotNull(message = "Backlog count is mandatory")
    @Min(value = 0, message = "Backlogs cannot be negative")
    @Column(nullable = false)
    private Integer backlogs = 0;

    @Column(length = 20)
    private String phone;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    // Default Constructor
    public Student() {
    }

    // Parameterized Constructor
    public Student(String rollNumber, String fullName, String email, String branch, Double cgpa, Integer backlogs, String phone) {
        this.rollNumber = rollNumber;
        this.fullName = fullName;
        this.email = email;
        this.branch = branch;
        this.cgpa = cgpa;
        this.backlogs = backlogs != null ? backlogs : 0;
        this.phone = phone;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Double getCgpa() {
        return cgpa;
    }

    public void setCgpa(Double cgpa) {
        this.cgpa = cgpa;
    }

    public Integer getBacklogs() {
        return backlogs;
    }

    public void setBacklogs(Integer backlogs) {
        this.backlogs = backlogs;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", rollNumber='" + rollNumber + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", branch='" + branch + '\'' +
                ", cgpa=" + cgpa +
                ", backlogs=" + backlogs +
                '}';
    }
}
