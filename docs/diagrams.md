# Campus Event Management System Diagrams

## ER Diagram

```mermaid
erDiagram
    ADMINS ||--o{ ANNOUNCEMENTS : creates
    ADMINS ||--o{ REPORTS : generates
    COORDINATORS ||--o{ EVENTS : manages
    STUDENTS ||--o{ EVENT_REGISTRATIONS : registers
    EVENTS ||--o{ EVENT_REGISTRATIONS : has
    EVENT_REGISTRATIONS ||--o| ATTENDANCE : verifies
    EVENT_REGISTRATIONS ||--o| CERTIFICATES : earns
    COORDINATORS ||--o{ ATTENDANCE : marks
```

## Use Case Diagram

```plantuml
@startuml
left to right direction
actor Admin
actor Student
actor Coordinator
rectangle "Campus Event Management System" {
  Admin --> (Manage Events)
  Admin --> (Manage Students)
  Admin --> (Manage Coordinators)
  Admin --> (Publish Announcements)
  Admin --> (View Reports)
  Student --> (Register Account)
  Student --> (Register for Event)
  Student --> (Download QR)
  Student --> (Download Certificate)
  Coordinator --> (View Assigned Events)
  Coordinator --> (Scan QR)
  Coordinator --> (Mark Attendance)
  Coordinator --> (Generate Certificates)
}
@enduml
```

## Sequence Diagram

```mermaid
sequenceDiagram
    participant Student
    participant EventRegisterServlet
    participant RegistrationService
    participant RegistrationDAO
    participant QRCodeUtil
    participant EmailUtil
    Student->>EventRegisterServlet: Submit event registration
    EventRegisterServlet->>RegistrationService: register(studentId,eventId)
    RegistrationService->>RegistrationDAO: check duplicate and insert
    RegistrationDAO-->>RegistrationService: registrationId
    RegistrationService->>QRCodeUtil: generate QR payload and image
    RegistrationService->>RegistrationDAO: save QR details
    RegistrationService->>EmailUtil: send confirmation
    EventRegisterServlet-->>Student: Receipt page
```

## Class Diagram

```mermaid
classDiagram
    class LoginServlet
    class EventDAO
    class RegistrationService
    class AttendanceService
    class CertificateService
    class Student
    class Event
    class EventRegistration
    LoginServlet --> AuthService
    AuthService --> AuthDAO
    RegistrationService --> RegistrationDAO
    RegistrationService --> QRCodeUtil
    AttendanceService --> AttendanceDAO
    CertificateService --> PDFUtil
    EventDAO --> Event
    RegistrationDAO --> EventRegistration
```

## Database Schema Documentation

The database is normalized around role-specific users, events, registrations, attendance, certificates, announcements, reports and login audit records. Foreign keys enforce ownership and event assignment. Views provide report-friendly projections. Triggers prevent registration for unapproved or full events.
