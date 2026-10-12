DROP DATABASE IF EXISTS event_management;
CREATE DATABASE event_management CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE event_management;

CREATE TABLE admins (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(120) NOT NULL UNIQUE,
  password_hash VARCHAR(100) NOT NULL,
  active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE students (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  usn VARCHAR(30) NOT NULL UNIQUE,
  email VARCHAR(120) NOT NULL UNIQUE,
  phone VARCHAR(15) NOT NULL,
  department VARCHAR(80) NOT NULL,
  semester INT NOT NULL,
  password_hash VARCHAR(100) NOT NULL,
  active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT chk_student_semester CHECK (semester BETWEEN 1 AND 8)
);

CREATE TABLE coordinators (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(120) NOT NULL UNIQUE,
  phone VARCHAR(15) NOT NULL,
  department VARCHAR(80) NOT NULL,
  password_hash VARCHAR(100) NOT NULL,
  active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE events (
  id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(160) NOT NULL,
  description TEXT NOT NULL,
  category VARCHAR(80) NOT NULL,
  venue VARCHAR(120) NOT NULL,
  event_date DATE NOT NULL,
  start_time VARCHAR(20) NOT NULL,
  end_time VARCHAR(20) NOT NULL,
  capacity INT NOT NULL,
  coordinator_id INT NOT NULL,
  status ENUM('PENDING','APPROVED','REJECTED') NOT NULL DEFAULT 'PENDING',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_events_coordinator FOREIGN KEY (coordinator_id) REFERENCES coordinators(id),
  CONSTRAINT chk_event_capacity CHECK (capacity > 0)
);

CREATE TABLE event_registrations (
  id INT AUTO_INCREMENT PRIMARY KEY,
  student_id INT NOT NULL,
  event_id INT NOT NULL,
  status ENUM('REGISTERED','CANCELLED') NOT NULL DEFAULT 'REGISTERED',
  qr_code VARCHAR(500),
  qr_image_path VARCHAR(255),
  registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_reg_student FOREIGN KEY (student_id) REFERENCES students(id),
  CONSTRAINT fk_reg_event FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE,
  CONSTRAINT uq_active_registration UNIQUE (student_id, event_id, status)
);

CREATE TABLE attendance (
  id INT AUTO_INCREMENT PRIMARY KEY,
  registration_id INT NOT NULL UNIQUE,
  student_id INT NOT NULL,
  event_id INT NOT NULL,
  marked_by INT NOT NULL,
  marked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_att_registration FOREIGN KEY (registration_id) REFERENCES event_registrations(id) ON DELETE CASCADE,
  CONSTRAINT fk_att_student FOREIGN KEY (student_id) REFERENCES students(id),
  CONSTRAINT fk_att_event FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE,
  CONSTRAINT fk_att_coordinator FOREIGN KEY (marked_by) REFERENCES coordinators(id)
);

CREATE TABLE certificates (
  id INT AUTO_INCREMENT PRIMARY KEY,
  registration_id INT NOT NULL UNIQUE,
  verification_code VARCHAR(80) NOT NULL UNIQUE,
  pdf_path VARCHAR(255) NOT NULL,
  generated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_cert_registration FOREIGN KEY (registration_id) REFERENCES event_registrations(id) ON DELETE CASCADE
);

CREATE TABLE announcements (
  id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(150) NOT NULL,
  message TEXT NOT NULL,
  audience ENUM('ALL','STUDENT','COORDINATOR') NOT NULL DEFAULT 'ALL',
  created_by INT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_announcement_admin FOREIGN KEY (created_by) REFERENCES admins(id)
);

CREATE TABLE reports (
  id INT AUTO_INCREMENT PRIMARY KEY,
  report_type VARCHAR(60) NOT NULL,
  generated_by INT NOT NULL,
  generated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  parameters VARCHAR(255),
  CONSTRAINT fk_reports_admin FOREIGN KEY (generated_by) REFERENCES admins(id)
);

CREATE TABLE login_logs (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  role ENUM('ADMIN','STUDENT','COORDINATOR') NOT NULL,
  ip_address VARCHAR(50),
  success BOOLEAN NOT NULL,
  logged_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_events_date_status ON events(event_date, status);
CREATE INDEX idx_events_category ON events(category);
CREATE INDEX idx_reg_event ON event_registrations(event_id);
CREATE INDEX idx_att_event ON attendance(event_id);
CREATE INDEX idx_announcements_audience ON announcements(audience);
CREATE INDEX idx_login_role_time ON login_logs(role, logged_at);

CREATE VIEW v_registration_report AS
SELECT r.id registration_id, e.title event_title, s.name student_name, s.usn, r.status, r.registered_at
FROM event_registrations r
JOIN events e ON e.id = r.event_id
JOIN students s ON s.id = r.student_id;

CREATE VIEW v_attendance_report AS
SELECT a.id attendance_id, e.title event_title, s.name student_name, s.usn, a.marked_at, c.name marked_by
FROM attendance a
JOIN events e ON e.id = a.event_id
JOIN students s ON s.id = a.student_id
JOIN coordinators c ON c.id = a.marked_by;

DELIMITER //
CREATE TRIGGER trg_prevent_over_capacity
BEFORE INSERT ON event_registrations
FOR EACH ROW
BEGIN
  DECLARE current_count INT;
  DECLARE event_capacity INT;
  SELECT COUNT(*) INTO current_count FROM event_registrations WHERE event_id = NEW.event_id AND status = 'REGISTERED';
  SELECT capacity INTO event_capacity FROM events WHERE id = NEW.event_id;
  IF current_count >= event_capacity THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Event capacity is full';
  END IF;
END//

CREATE TRIGGER trg_prevent_unapproved_registration
BEFORE INSERT ON event_registrations
FOR EACH ROW
BEGIN
  DECLARE event_status VARCHAR(20);
  SELECT status INTO event_status FROM events WHERE id = NEW.event_id;
  IF event_status <> 'APPROVED' THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Registration is allowed only for approved events';
  END IF;
END//
DELIMITER ;

-- BCrypt sample hash below is for the password: password
-- For production, run PasswordHashTool and replace these hashes.
INSERT INTO admins(name,email,password_hash,active) VALUES
('System Admin','admin@college.edu','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',1);

INSERT INTO coordinators(name,email,phone,department,password_hash,active) VALUES
('Dr. Meera Rao','meera@college.edu','9876543210','Computer Science','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',1),
('Prof. Arjun Shetty','arjun@college.edu','9876543211','Electronics','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',1);

INSERT INTO students(name,usn,email,phone,department,semester,password_hash,active) VALUES
('Ananya Kumar','1AB22CS001','ananya@student.edu','9876543220','Computer Science',5,'$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',1),
('Rahul Nair','1AB22EC014','rahul@student.edu','9876543221','Electronics',5,'$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',1);

INSERT INTO events(title,description,category,venue,event_date,start_time,end_time,capacity,coordinator_id,status) VALUES
('AI Workshop','Hands-on workshop on practical AI tools and responsible usage.','Technical','Seminar Hall A','2026-08-12','10:00 AM','04:00 PM',120,1,'APPROVED'),
('Cultural Fest Auditions','Auditions for dance, music, theatre and literary teams.','Cultural','Auditorium','2026-08-20','09:30 AM','03:30 PM',200,2,'APPROVED'),
('Hackathon 2026','Twenty-four hour campus innovation challenge.','Technical','Innovation Lab','2026-09-05','09:00 AM','09:00 AM',80,1,'PENDING');

INSERT INTO event_registrations(student_id,event_id,status,qr_code,qr_image_path) VALUES
(1,1,'REGISTERED','REG:1|STU:1|EVT:1|UUID:sample-uuid-1','qr/sample-registration-1.png'),
(2,2,'REGISTERED','REG:2|STU:2|EVT:2|UUID:sample-uuid-2','qr/sample-registration-2.png');

INSERT INTO attendance(registration_id,student_id,event_id,marked_by) VALUES
(1,1,1,1);

INSERT INTO announcements(title,message,audience,created_by) VALUES
('Welcome to Campus Events','All event registrations and certificates are now managed in this portal.','ALL',1),
('Carry QR Code','Students must carry the downloaded QR code for attendance verification.','STUDENT',1);
