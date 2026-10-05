package com.placement.portal.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing an application of a student to a company drive.
 */
@Entity
@Table(name = "drive_applications", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_id", "drive_id"})
})
public class DriveApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "drive_id", nullable = false)
    private CompanyDrive drive;

    @Column(nullable = false, length = 30)
    private String status = "APPLIED";

    @Column(name = "application_date", insertable = false, updatable = false)
    private LocalDateTime applicationDate;

    public DriveApplication() {
    }

    public DriveApplication(Student student, CompanyDrive drive, String status) {
        this.student = student;
        this.drive = drive;
        this.status = status != null ? status : "APPLIED";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public CompanyDrive getDrive() {
        return drive;
    }

    public void setDrive(CompanyDrive drive) {
        this.drive = drive;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDateTime applicationDate) {
        this.applicationDate = applicationDate;
    }
}
