package com.placement.portal.repository;

import com.placement.portal.entity.DriveApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriveApplicationRepository extends JpaRepository<DriveApplication, Long> {

    List<DriveApplication> findByDriveId(Long driveId);

    List<DriveApplication> findByStudentId(Long studentId);

    Optional<DriveApplication> findByStudentIdAndDriveId(Long studentId, Long driveId);

    boolean existsByStudentIdAndDriveId(Long studentId, Long driveId);

    long countByDriveId(Long driveId);
}
