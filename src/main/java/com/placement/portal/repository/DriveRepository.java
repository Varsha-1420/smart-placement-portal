package com.placement.portal.repository;

import com.placement.portal.entity.CompanyDrive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriveRepository extends JpaRepository<CompanyDrive, Long> {

    List<CompanyDrive> findByStatusOrderByDriveDateAsc(String status);

    @Query("SELECT d FROM CompanyDrive d ORDER BY d.driveDate ASC")
    List<CompanyDrive> findAllOrderByDriveDateAsc();
}
