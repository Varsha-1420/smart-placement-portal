-- ====================================================================
-- Smart Campus Placement & Drive Management Portal
-- Initial Mock Data (Realistic Student profiles & Company Drives)
-- ====================================================================

-- 1. Insert 14 Sample Students across CSE, ECE, and IT branches
INSERT INTO students (roll_number, full_name, email, branch, cgpa, backlogs, phone) VALUES
('21CS001', 'Aarav Sharma',     'aarav.sharma@campus.edu',     'CSE', 9.20, 0, '+91-9876543210'),
('21CS014', 'Priya Patel',      'priya.patel@campus.edu',      'CSE', 8.85, 0, '+91-9876543211'),
('21IT005', 'Rohan Mehta',      'rohan.mehta@campus.edu',      'IT',  8.40, 0, '+91-9876543212'),
('21EC021', 'Ananya Deshmukh',  'ananya.d@campus.edu',         'ECE', 8.10, 0, '+91-9876543213'),
('21CS032', 'Vikram Singh',     'vikram.singh@campus.edu',     'CSE', 7.90, 1, '+91-9876543214'),
('21IT019', 'Neha Gupta',       'neha.gupta@campus.edu',       'IT',  7.75, 0, '+91-9876543215'),
('21EC008', 'Karthik Raja',     'karthik.raja@campus.edu',     'ECE', 7.45, 1, '+91-9876543216'),
('21CS045', 'Sneha Kulkarni',   'sneha.k@campus.edu',          'CSE', 7.10, 0, '+91-9876543217'),
('21IT031', 'Aditya Verma',     'aditya.verma@campus.edu',     'IT',  6.95, 2, '+91-9876543218'),
('21EC042', 'Meera Nambiar',    'meera.n@campus.edu',          'ECE', 6.60, 0, '+91-9876543219'),
('21CS060', 'Devendra Rao',     'devendra.rao@campus.edu',     'CSE', 6.25, 1, '+91-9876543220'),
('21IT048', 'Ishita Joshi',     'ishita.joshi@campus.edu',     'IT',  5.90, 2, '+91-9876543221'),
('21EC055', 'Rahul Nair',       'rahul.nair@campus.edu',       'ECE', 5.50, 2, '+91-9876543222'),
('21CS072', 'Tanvi Bhatia',     'tanvi.bhatia@campus.edu',     'CSE', 8.65, 0, '+91-9876543223');

-- 2. Insert Sample Company Placement Drives
INSERT INTO company_drives (company_name, job_role, package_lpa, min_cgpa, max_backlogs, allowed_branches, drive_date, status) VALUES
('Google', 'Software Development Engineer I', 32.50, 8.50, 0, 'CSE,IT', '2026-11-15', 'UPCOMING'),
('Microsoft', 'Cloud Solutions Associate', 24.00, 8.00, 0, 'CSE,ECE,IT', '2026-11-20', 'UPCOMING'),
('Qualcomm', 'Hardware / Embedded Systems Engineer', 18.50, 7.50, 0, 'ECE,CSE', '2026-11-25', 'UPCOMING'),
('Infosys', 'Specialist Programmer', 9.50, 6.50, 1, 'CSE,ECE,IT', '2026-12-01', 'UPCOMING'),
('TCS', 'Systems Engineer (Ninja & Digital)', 7.00, 6.00, 2, 'CSE,ECE,IT', '2026-12-10', 'UPCOMING');

-- 3. Insert Sample Initial Drive Applications
INSERT INTO drive_applications (student_id, drive_id, status) VALUES
(1, 1, 'SHORTLISTED'),
(2, 1, 'APPLIED'),
(1, 2, 'SHORTLISTED'),
(3, 2, 'APPLIED'),
(4, 2, 'APPLIED'),
(5, 4, 'APPLIED'),
(6, 4, 'APPLIED');
