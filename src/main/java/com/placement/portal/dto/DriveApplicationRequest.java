package com.placement.portal.dto;

import jakarta.validation.constraints.NotNull;

public class DriveApplicationRequest {

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Drive ID is required")
    private Long driveId;

    public DriveApplicationRequest() {
    }

    public DriveApplicationRequest(Long studentId, Long driveId) {
        this.studentId = studentId;
        this.driveId = driveId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getDriveId() {
        return driveId;
    }

    public void setDriveId(Long driveId) {
        this.driveId = driveId;
    }
}
