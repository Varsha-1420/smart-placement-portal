package com.placement.portal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entity representing a company campus placement drive.
 */
@Entity
@Table(name = "company_drives")
public class CompanyDrive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Company name is mandatory")
    @Size(max = 120, message = "Company name must not exceed 120 characters")
    @Column(name = "company_name", nullable = false, length = 120)
    private String companyName;

    @NotBlank(message = "Job role is mandatory")
    @Size(max = 100, message = "Job role must not exceed 100 characters")
    @Column(name = "job_role", nullable = false, length = 100)
    private String jobRole;

    @NotNull(message = "Package (LPA) is mandatory")
    @DecimalMin(value = "0.5", inclusive = true, message = "Package must be at least 0.5 LPA")
    @Column(name = "package_lpa", nullable = false)
    private Double packageLpa;

    @NotNull(message = "Minimum CGPA criteria is mandatory")
    @DecimalMin(value = "0.0", inclusive = true, message = "Minimum CGPA cannot be negative")
    @DecimalMax(value = "10.0", inclusive = true, message = "Minimum CGPA cannot exceed 10.0")
    @Column(name = "min_cgpa", nullable = false)
    private Double minCgpa;

    @NotNull(message = "Maximum backlogs criteria is mandatory")
    @Min(value = 0, message = "Maximum backlogs cannot be negative")
    @Column(name = "max_backlogs", nullable = false)
    private Integer maxBacklogs = 0;

    @NotBlank(message = "Allowed branches list is mandatory (e.g. 'CSE,ECE,IT')")
    @Column(name = "allowed_branches", nullable = false)
    private String allowedBranches;

    @NotNull(message = "Drive date is mandatory")
    @Column(name = "drive_date", nullable = false)
    private LocalDate driveDate;

    @Column(nullable = false, length = 30)
    private String status = "UPCOMING";

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    // Default Constructor
    public CompanyDrive() {
    }

    // Parameterized Constructor
    public CompanyDrive(String companyName, String jobRole, Double packageLpa, Double minCgpa, Integer maxBacklogs, String allowedBranches, LocalDate driveDate, String status) {
        this.companyName = companyName;
        this.jobRole = jobRole;
        this.packageLpa = packageLpa;
        this.minCgpa = minCgpa;
        this.maxBacklogs = maxBacklogs != null ? maxBacklogs : 0;
        this.allowedBranches = allowedBranches;
        this.driveDate = driveDate;
        this.status = status != null ? status : "UPCOMING";
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    public Double getPackageLpa() {
        return packageLpa;
    }

    public void setPackageLpa(Double packageLpa) {
        this.packageLpa = packageLpa;
    }

    public Double getMinCgpa() {
        return minCgpa;
    }

    public void setMinCgpa(Double minCgpa) {
        this.minCgpa = minCgpa;
    }

    public Integer getMaxBacklogs() {
        return maxBacklogs;
    }

    public void setMaxBacklogs(Integer maxBacklogs) {
        this.maxBacklogs = maxBacklogs;
    }

    public String getAllowedBranches() {
        return allowedBranches;
    }

    public void setAllowedBranches(String allowedBranches) {
        this.allowedBranches = allowedBranches;
    }

    public LocalDate getDriveDate() {
        return driveDate;
    }

    public void setDriveDate(LocalDate driveDate) {
        this.driveDate = driveDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "CompanyDrive{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", jobRole='" + jobRole + '\'' +
                ", packageLpa=" + packageLpa +
                ", minCgpa=" + minCgpa +
                ", maxBacklogs=" + maxBacklogs +
                ", allowedBranches='" + allowedBranches + '\'' +
                ", driveDate=" + driveDate +
                ", status='" + status + '\'' +
                '}';
    }
}
