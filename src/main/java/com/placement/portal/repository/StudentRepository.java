package com.placement.portal.repository;

import com.placement.portal.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByRollNumber(String rollNumber);

    Optional<Student> findByEmail(String email);

    boolean existsByRollNumber(String rollNumber);

    boolean existsByEmail(String email);

    /**
     * Custom JPA Query filtering candidates by minimum CGPA, maximum backlogs, and branch list.
     * Uses SQL IN logic and handles branch selection seamlessly across H2 and MySQL.
     *
     * @param minCgpa Minimum CGPA required
     * @param maxBacklogs Maximum allowable active backlogs
     * @param branches Collection of eligible branch acronyms (e.g. ['CSE', 'ECE'])
     * @param hasBranches Flag indicating if branch filtering is active
     * @return List of eligible candidates sorted by CGPA descending
     */
    @Query("SELECT s FROM Student s " +
           "WHERE s.cgpa >= :minCgpa " +
           "AND s.backlogs <= :maxBacklogs " +
           "AND (:hasBranches = false OR s.branch IN :branches) " +
           "ORDER BY s.cgpa DESC, s.backlogs ASC")
    List<Student> findEligibleCandidates(
            @Param("minCgpa") Double minCgpa,
            @Param("maxBacklogs") Integer maxBacklogs,
            @Param("branches") Collection<String> branches,
            @Param("hasBranches") boolean hasBranches
    );

    /**
     * Native SQL Query variant supporting MySQL FIND_IN_SET logic with comma-separated branch string.
     *
     * @param minCgpa Minimum CGPA required
     * @param maxBacklogs Maximum allowable active backlogs
     * @param branchesCsv Comma-separated branches (e.g. 'CSE,ECE,IT')
     * @return List of eligible candidates
     */
    @Query(value = "SELECT * FROM students s " +
                   "WHERE s.cgpa >= :minCgpa " +
                   "AND s.backlogs <= :maxBacklogs " +
                   "AND (:branchesCsv IS NULL OR :branchesCsv = '' OR FIND_IN_SET(s.branch, :branchesCsv) > 0) " +
                   "ORDER BY s.cgpa DESC, s.backlogs ASC",
           nativeQuery = true)
    List<Student> findEligibleCandidatesNative(
            @Param("minCgpa") Double minCgpa,
            @Param("maxBacklogs") Integer maxBacklogs,
            @Param("branchesCsv") String branchesCsv
    );
}
