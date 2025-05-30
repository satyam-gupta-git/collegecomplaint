-- Create the database
CREATE DATABASE IF NOT EXISTS college_complaints;

-- Use the database
USE college_complaints;

-- Drop tables if they already exist (to avoid conflicts on reruns)
DROP TABLE IF EXISTS complaints;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS staff_categories;

-- Create staff_categories table
CREATE TABLE staff_categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(50) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create users table with staff_category_id foreign key
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    user_type ENUM('student', 'staff', 'admin') NOT NULL,
    staff_category_id INT,
    registration_status ENUM('pending', 'approved', 'rejected') DEFAULT 'pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (staff_category_id) REFERENCES staff_categories(id)
);

-- Create complaints table with references to users and staff_categories
CREATE TABLE complaints (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    title VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    category VARCHAR(50) NOT NULL,
    location VARCHAR(100) NOT NULL,
    image_path VARCHAR(255),
    assigned_staff_category_id INT,
    submission_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('pending', 'in-progress', 'resolved') DEFAULT 'pending',
    resolution TEXT,
    resolution_date TIMESTAMP NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (assigned_staff_category_id) REFERENCES staff_categories(id)
);

-- Insert sample staff categories
INSERT INTO staff_categories (category_name, description) VALUES
('Infrastructure', 'Handles physical infrastructure related complaints'),
('Library', 'Manages library related issues'),
('IT Support', 'Handles technical and IT related problems'),
('Academic', 'Deals with academic related complaints'),
('Administrative', 'Handles administrative and general issues');

-- Insert sample users
INSERT INTO users (username, password, full_name, email, user_type, staff_category_id, registration_status) VALUES
('student1', 'password', 'John Doe', 'john.doe@example.com', 'student', NULL, 'approved'),
('student2', 'password', 'Jane Smith', 'jane.smith@example.com', 'student', NULL, 'approved'),
('staff1', 'password', 'Admin User', 'admin@example.com', 'admin', NULL, 'approved'),
('infra_staff', 'password', 'Infrastructure Staff', 'infra@example.com', 'staff', 1, 'approved'),
('library_staff', 'password', 'Library Staff', 'library@example.com', 'staff', 2, 'approved');

-- Insert sample complaints
INSERT INTO complaints (user_id, title, description, category, location, assigned_staff_category_id, status) VALUES
(1, 'Broken Chair in Classroom', 'There is a broken chair in Room 101 that needs to be fixed.', 'Infrastructure', 'Room 101', 1, 'pending'),
(1, 'Poor Internet Connection', 'The Wi-Fi in the library is very slow and keeps disconnecting.', 'IT Support', 'Library', 3, 'in-progress'),
(2, 'Missing Books in Library', 'Several books listed in the catalog are missing from the shelves.', 'Library', 'Main Library', 2, 'pending');

-- Update one complaint as resolved
UPDATE complaints
SET 
    status = 'resolved',
    resolution = 'The chair has been replaced with a new one.',
    resolution_date = NOW()
WHERE id = 1;
