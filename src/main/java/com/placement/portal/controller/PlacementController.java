package com.placement.portal.controller;

import com.placement.portal.dto.ApiResponse;
import com.placement.portal.dto.DriveApplicationRequest;
import com.placement.portal.entity.CompanyDrive;
import com.placement.portal.entity.DriveApplication;
import com.placement.portal.entity.Student;
import com.placement.portal.service.PlacementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller exposing Placement Portal API endpoints.
 */
@RestController
@RequestMapping("/api/placement")
@CrossOrigin(origins = "*")
public class PlacementController {

    private final PlacementService placementService;

    public PlacementController(PlacementService placementService) {
        this.placementService = placementService;
    }

    /**
     * POST /api/placement/students
     * Register a new student.
     */
    @PostMapping("/students")
    public ResponseEntity<ApiResponse<Student>> registerStudent(@Valid @RequestBody Student student) {
        Student createdStudent = placementService.registerStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Student registered successfully", createdStudent));
    }

    /**
     * GET /api/placement/students
     * Retrieve all registered students.
     */
    @GetMapping("/students")
    public ResponseEntity<ApiResponse<List<Student>>> getAllStudents() {
        List<Student> students = placementService.getAllStudents();
        return ResponseEntity.ok(ApiResponse.success("All students fetched successfully", students));
    }

    /**
     * POST /api/placement/drives
     * Create a new company placement drive.
     */
    @PostMapping("/drives")
    public ResponseEntity<ApiResponse<CompanyDrive>> createDrive(@Valid @RequestBody CompanyDrive drive) {
        CompanyDrive createdDrive = placementService.createDrive(drive);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Company drive created successfully", createdDrive));
    }

    /**
     * GET /api/placement/drives
     * Retrieve all company drives.
     */
    @GetMapping("/drives")
    public ResponseEntity<ApiResponse<List<CompanyDrive>>> getAllDrives() {
        List<CompanyDrive> drives = placementService.getAllDrives();
        return ResponseEntity.ok(ApiResponse.success("All drives fetched successfully", drives));
    }

    /**
     * GET /api/placement/eligible
     * Calculate and filter eligible candidates dynamically.
     *
     * @param minCgpa Minimum CGPA required (default: 0.0)
     * @param maxBacklogs Maximum allowable active backlogs (default: 10)
     * @param branches Optional list or comma-separated branch codes (e.g. ?branches=CSE,ECE)
     */
    @GetMapping("/eligible")
    public ResponseEntity<ApiResponse<List<Student>>> getEligibleCandidates(
            @RequestParam(name = "minCgpa", required = false, defaultValue = "0.0") Double minCgpa,
            @RequestParam(name = "maxBacklogs", required = false, defaultValue = "10") Integer maxBacklogs,
            @RequestParam(name = "branches", required = false) List<String> branches) {

        List<Student> eligibleCandidates = placementService.findEligibleCandidates(minCgpa, maxBacklogs, branches);
        return ResponseEntity.ok(
                ApiResponse.success("Eligible candidates retrieved successfully (" + eligibleCandidates.size() + " found)", eligibleCandidates)
        );
    }

    /**
     * POST /api/placement/apply
     * Apply a student for a specific placement drive.
     */
    @PostMapping("/apply")
    public ResponseEntity<ApiResponse<DriveApplication>> applyForDrive(@Valid @RequestBody DriveApplicationRequest request) {
        DriveApplication application = placementService.applyForDrive(request.getStudentId(), request.getDriveId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Application submitted successfully", application));
    }

    /**
     * GET /api/placement/stats
     * Dashboard summary metrics.
     */
    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDashboardStatistics() {
        Map<String, Object> stats = placementService.getDashboardStatistics();
        return ResponseEntity.ok(ApiResponse.success("Dashboard metrics retrieved", stats));
    }
}
