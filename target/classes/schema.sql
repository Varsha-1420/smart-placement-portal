-- ====================================================================
-- Smart Campus Placement & Drive Management Portal
-- Database Schema Definition (Compatible with MySQL 8.x and H2 Database)
-- ====================================================================

-- 1. Drop existing tables in reverse order of foreign key dependency
DROP TABLE IF EXISTS drive_applications;
DROP TABLE IF EXISTS company_drives;
DROP TABLE IF EXISTS students;

-- 2. Students Table
CREATE TABLE students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    roll_number VARCHAR(50) NOT NULL UNIQUE,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    branch VARCHAR(30) NOT NULL,
    cgpa DECIMAL(3, 2) NOT NULL,
    backlogs INT NOT NULL DEFAULT 0,
    phone VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. Company Drives Table
CREATE TABLE company_drives (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    company_name VARCHAR(120) NOT NULL,
    job_role VARCHAR(100) NOT NULL,
    package_lpa DECIMAL(5, 2) NOT NULL,
    min_cgpa DECIMAL(3, 2) NOT NULL,
    max_backlogs INT NOT NULL DEFAULT 0,
    allowed_branches VARCHAR(255) NOT NULL,
    drive_date DATE NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'UPCOMING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 4. Drive Applications Table
CREATE TABLE drive_applications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    drive_id BIGINT NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'APPLIED',
    application_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_app_student FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    CONSTRAINT fk_app_drive FOREIGN KEY (drive_id) REFERENCES company_drives(id) ON DELETE CASCADE,
    CONSTRAINT uk_student_drive UNIQUE (student_id, drive_id)
);
