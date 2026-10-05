package com.placement.portal.service;

import com.placement.portal.entity.CompanyDrive;
import com.placement.portal.entity.DriveApplication;
import com.placement.portal.entity.Student;

import java.util.List;
import java.util.Map;

/**
 * Service interface for placement portal business operations.
 */
public interface PlacementService {

    Student registerStudent(Student student);

    List<Student> getAllStudents();

    Student getStudentById(Long id);

    CompanyDrive createDrive(CompanyDrive drive);

    List<CompanyDrive> getAllDrives();

    CompanyDrive getDriveById(Long id);

    List<Student> findEligibleCandidates(Double minCgpa, Integer maxBacklogs, List<String> branches);

    DriveApplication applyForDrive(Long studentId, Long driveId);

    List<DriveApplication> getApplicationsForDrive(Long driveId);

    Map<String, Object> getDashboardStatistics();
}
