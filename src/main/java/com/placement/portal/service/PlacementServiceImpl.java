package com.placement.portal.service;

import com.placement.portal.entity.CompanyDrive;
import com.placement.portal.entity.DriveApplication;
import com.placement.portal.entity.Student;
import com.placement.portal.exception.DuplicateResourceException;
import com.placement.portal.exception.ResourceNotFoundException;
import com.placement.portal.repository.DriveApplicationRepository;
import com.placement.portal.repository.DriveRepository;
import com.placement.portal.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Production-ready implementation of PlacementService.
 */
@Service
@Transactional
public class PlacementServiceImpl implements PlacementService {

    private static final Logger log = LoggerFactory.getLogger(PlacementServiceImpl.class);

    private final StudentRepository studentRepository;
    private final DriveRepository driveRepository;
    private final DriveApplicationRepository applicationRepository;

    public PlacementServiceImpl(StudentRepository studentRepository,
                                DriveRepository driveRepository,
                                DriveApplicationRepository applicationRepository) {
        this.studentRepository = studentRepository;
        this.driveRepository = driveRepository;
        this.applicationRepository = applicationRepository;
    }

    @Override
    public Student registerStudent(Student student) {
        log.info("Attempting to register student with Roll No: {}", student.getRollNumber());

        // Normalize data
        student.setRollNumber(student.getRollNumber().trim().toUpperCase());
        student.setBranch(student.getBranch().trim().toUpperCase());
        student.setEmail(student.getEmail().trim().toLowerCase());

        if (studentRepository.existsByRollNumber(student.getRollNumber())) {
            throw new DuplicateResourceException("Student with roll number " + student.getRollNumber() + " already exists.");
        }
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new DuplicateResourceException("Student with email " + student.getEmail() + " already exists.");
        }

        Student saved = studentRepository.save(student);
        log.info("Successfully registered student with ID: {}", saved.getId());
        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
    }

    @Override
    public CompanyDrive createDrive(CompanyDrive drive) {
        log.info("Creating company placement drive for: {}", drive.getCompanyName());

        // Clean up allowed branches formatting (comma-separated, uppercase, no extra whitespace)
        if (drive.getAllowedBranches() != null) {
            String cleanedBranches = Arrays.stream(drive.getAllowedBranches().split(","))
                    .map(String::trim)
                    .map(String::toUpperCase)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.joining(","));
            drive.setAllowedBranches(cleanedBranches);
        }

        CompanyDrive saved = driveRepository.save(drive);
        log.info("Created placement drive with ID: {}", saved.getId());
        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyDrive> getAllDrives() {
        return driveRepository.findAllOrderByDriveDateAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyDrive getDriveById(Long id) {
        return driveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Drive not found with ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findEligibleCandidates(Double minCgpa, Integer maxBacklogs, List<String> branches) {
        // Fallbacks for optional parameters
        Double effectiveMinCgpa = (minCgpa != null) ? minCgpa : 0.0;
        Integer effectiveMaxBacklogs = (maxBacklogs != null) ? maxBacklogs : Integer.MAX_VALUE;

        List<String> cleanedBranches = null;
        boolean hasBranches = false;

        if (branches != null && !branches.isEmpty()) {
            cleanedBranches = branches.stream()
                    .filter(Objects::nonNull)
                    .flatMap(b -> Arrays.stream(b.split(",")))
                    .map(String::trim)
                    .map(String::toUpperCase)
                    .filter(s -> !s.isEmpty())
                    .distinct()
                    .collect(Collectors.toList());
            hasBranches = !cleanedBranches.isEmpty();
        }

        log.debug("Finding eligible candidates: minCgpa={}, maxBacklogs={}, branches={}, hasBranches={}",
                effectiveMinCgpa, effectiveMaxBacklogs, cleanedBranches, hasBranches);

        return studentRepository.findEligibleCandidates(
                effectiveMinCgpa,
                effectiveMaxBacklogs,
                cleanedBranches,
                hasBranches
        );
    }

    @Override
    public DriveApplication applyForDrive(Long studentId, Long driveId) {
        log.info("Applying student ID {} for drive ID {}", studentId, driveId);

        Student student = getStudentById(studentId);
        CompanyDrive drive = getDriveById(driveId);

        if (applicationRepository.existsByStudentIdAndDriveId(studentId, driveId)) {
            throw new DuplicateResourceException("Student has already applied for this company drive.");
        }

        // Validate basic eligibility
        List<String> allowedBranches = Arrays.stream(drive.getAllowedBranches().split(","))
                .map(String::trim)
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        boolean isBranchEligible = allowedBranches.contains(student.getBranch().toUpperCase());
        boolean isCgpaEligible = student.getCgpa() >= drive.getMinCgpa();
        boolean isBacklogEligible = student.getBacklogs() <= drive.getMaxBacklogs();

        if (!isBranchEligible || !isCgpaEligible || !isBacklogEligible) {
            throw new IllegalArgumentException(String.format(
                    "Student does not satisfy eligibility criteria (Required: Min CGPA %.2f, Max Backlogs %d, Branches %s. Actual: CGPA %.2f, Backlogs %d, Branch %s)",
                    drive.getMinCgpa(), drive.getMaxBacklogs(), drive.getAllowedBranches(),
                    student.getCgpa(), student.getBacklogs(), student.getBranch()
            ));
        }

        DriveApplication application = new DriveApplication(student, drive, "APPLIED");
        return applicationRepository.save(application);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DriveApplication> getApplicationsForDrive(Long driveId) {
        return applicationRepository.findByDriveId(driveId);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getDashboardStatistics() {
        Map<String, Object> stats = new HashMap<>();
        long totalStudents = studentRepository.count();
        long totalDrives = driveRepository.count();
        long totalApplications = applicationRepository.count();

        // Calculate average CGPA
        List<Student> students = studentRepository.findAll();
        double avgCgpa = students.stream()
                .mapToDouble(Student::getCgpa)
                .average()
                .orElse(0.0);

        stats.put("totalStudents", totalStudents);
        stats.put("totalDrives", totalDrives);
        stats.put("totalApplications", totalApplications);
        stats.put("averageCgpa", Math.round(avgCgpa * 100.0) / 100.0);
        return stats;
    }
}
