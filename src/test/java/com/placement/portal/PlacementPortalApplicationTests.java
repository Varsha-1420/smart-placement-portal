package com.placement.portal;

import com.placement.portal.entity.CompanyDrive;
import com.placement.portal.entity.Student;
import com.placement.portal.repository.DriveRepository;
import com.placement.portal.repository.StudentRepository;
import com.placement.portal.service.PlacementService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("h2")
class PlacementPortalApplicationTests {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DriveRepository driveRepository;

    @Autowired
    private PlacementService placementService;

    @Test
    void contextLoads() {
        assertNotNull(placementService, "PlacementService should be loaded into application context");
    }

    @Test
    void testMockDataLoaded() {
        List<Student> students = studentRepository.findAll();
        assertTrue(students.size() >= 10, "Should have at least 10 mock students from data.sql");

        List<CompanyDrive> drives = driveRepository.findAll();
        assertFalse(drives.isEmpty(), "Should have mock company drives loaded from data.sql");
    }

    @Test
    void testFindEligibleCandidates() {
        // Find students with min CGPA 8.0, 0 backlogs, in CSE or IT
        List<Student> eligible = placementService.findEligibleCandidates(8.0, 0, List.of("CSE", "IT"));

        assertNotNull(eligible);
        assertFalse(eligible.isEmpty(), "Expected eligible candidates for CGPA >= 8.0");

        for (Student s : eligible) {
            assertTrue(s.getCgpa() >= 8.0, "Student CGPA must be >= 8.0");
            assertEquals(0, s.getBacklogs(), "Student backlogs must be 0");
            assertTrue(List.of("CSE", "IT").contains(s.getBranch()), "Student branch must be in CSE, IT");
        }
    }
}
