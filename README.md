# Campus Event Management System

Complete Eclipse Dynamic Web Project for a campus event portal using JSP, Jakarta Servlets 6.0, JDBC, MySQL, MVC, DAO pattern, QR codes, PDF certificates, BCrypt, and Jakarta Mail.

## Technology Stack

- Java JDK 21
- Eclipse IDE Dynamic Web Project
- Apache Tomcat 11
- JSP, Servlets, HTML5, CSS3, JavaScript
- JDBC with MySQL Connector/J 9.7.0
- MySQL database
- BCrypt password hashing
- ZXing QR Code Library
- OpenPDF
- Jakarta Mail

No Maven, Gradle, Spring, Hibernate, JPA, Bootstrap, Tailwind, React, or Angular are used.

## Software Requirements

Install:

- JDK 21
- Eclipse IDE for Enterprise Java and Web Developers
- Apache Tomcat 11
- MySQL Server 8.x
- MySQL Workbench or MySQL CLI

Required JAR files must be copied into `WebContent/WEB-INF/lib`:

- `mysql-connector-j-9.7.0.jar`
- `openpdf.jar`
- `core.jar` and `javase.jar` from ZXing
- `jbcrypt.jar`
- `jakarta.mail.jar`
- Jakarta Activation JAR if your Jakarta Mail distribution requires it

## JDK 21 Installation

Install JDK 21, set `JAVA_HOME`, and ensure `java -version` reports version 21. In Eclipse, open `Window > Preferences > Java > Installed JREs` and add/select JDK 21.

## Apache Tomcat 11 Configuration

In Eclipse:

1. Open `Window > Preferences > Server > Runtime Environments`.
2. Add `Apache Tomcat v11.0`.
3. Select the Tomcat 11 installation directory.
4. Ensure the runtime uses JDK 21.

## Import Project into Eclipse

1. Open Eclipse.
2. Select `File > Import > Existing Projects into Workspace`.
3. Browse to this folder.
4. Select `CampusEventManagementSystem`.
5. Finish import.

If Eclipse does not detect it as a Dynamic Web Project, create a new Dynamic Web Project with the same name, then copy `src`, `WebContent`, `.project`, `.classpath`, and `.settings`.

## Configure Build Path

Right-click project:

1. `Build Path > Configure Build Path`.
2. Confirm JDK 21 is selected.
3. Confirm Tomcat 11 runtime is selected.
4. Add all JARs from `WebContent/WEB-INF/lib`.

## Database Setup

1. Open MySQL Workbench or CLI.
2. Run [database/event_management.sql](database/event_management.sql).
3. Update DB credentials in [DBConnection.java](src/com/campus/util/DBConnection.java).

Default connection:

```java
jdbc:mysql://localhost:3306/event_management
user: root
password: root
```

## Default Credentials

The sample SQL uses BCrypt hashes for the password `password`.

- Admin: `admin@college.edu` / `password`
- Coordinator: `meera@college.edu` / `password`
- Student: `ananya@student.edu` / `password`

To create new hashes after adding `jbcrypt.jar`, run:

```bash
java com.campus.util.PasswordHashTool YourPassword
```

Then update the `password_hash` values in MySQL.

## Run Project

1. Right-click project.
2. Select `Run As > Run on Server`.
3. Choose Apache Tomcat 11.
4. Open `http://localhost:8080/CampusEventManagementSystem/`.

## Folder Structure

```text
src/com/campus/controller     Servlets
src/com/campus/dao            JDBC DAO classes
src/com/campus/model          JavaBeans and model classes
src/com/campus/service        Business services
src/com/campus/util           DB, BCrypt, QR, PDF, mail utilities
src/com/campus/filter         Authentication and authorization filters
WebContent/WEB-INF/views      JSP views
WebContent/assets/css         CSS
WebContent/assets/js          JavaScript
WebContent/assets/images      Images
WebContent/qr                 Generated QR images
WebContent/certificates       Generated PDF certificates
database                      SQL script
docs                          Diagrams and schema notes
```

## Main Features

- Role-based login for admin, student, and coordinator
- Session-based authentication and protected pages
- Student registration, profile editing, event browsing, registration cancellation
- QR code generation after registration
- Browser webcam QR scanning through native JavaScript `BarcodeDetector`
- Duplicate attendance prevention
- OpenPDF certificate generation
- Admin dashboards, reports, announcements, event approval
- Coordinator participant, attendance, and certificate workflows
- Server-side validation plus JavaScript validation

## Screenshots Placeholders

Add screenshots in `docs/screenshots` after first deployment:

- Landing page
- Admin dashboard
- Student event search
- Coordinator QR scanner
- Certificate PDF

## Common Eclipse Errors

- `jakarta.servlet cannot be resolved`: Add Tomcat 11 runtime to the project.
- `com.mysql.cj.jdbc.Driver not found`: Copy MySQL Connector/J 9.7.0 to `WEB-INF/lib`.
- `BCrypt cannot be resolved`: Copy `jbcrypt.jar` to `WEB-INF/lib`.
- `com.lowagie.text cannot be resolved`: Copy OpenPDF JAR to `WEB-INF/lib`.
- ZXing classes not found: Copy both ZXing `core.jar` and `javase.jar`.

## Tomcat Errors

- Port already in use: Change Tomcat HTTP port in `Servers > server.xml`.
- 404 on project root: Confirm context root is `CampusEventManagementSystem`.
- Webcam QR scanning does not start: Use Chrome or Edge with camera permission enabled.

## JDBC Errors

- Access denied: Update username/password in `DBConnection.java`.
- Unknown database: Run the SQL script first.
- Public key retrieval error: The JDBC URL already includes `allowPublicKeyRetrieval=true`.

## Troubleshooting Guide

- Clean project: `Project > Clean`.
- Re-add Tomcat runtime if Servlet API errors remain.
- Confirm JAR filenames in `.classpath` match actual files.
- Restart MySQL and Tomcat after credential or database changes.
- Check Tomcat console for full stack traces.

## Documentation

See [docs/diagrams.md](docs/diagrams.md) for:

- ER diagram in Mermaid
- Use case diagram in PlantUML
- Sequence diagram
- Class diagram
- Database schema notes

## Future Enhancements

- Email OTP password reset
- Certificate verification public page
- CSV export for reports
- Event feedback module
- Admin theme customization
- Offline QR scan queue

## License

This mini-project is provided for academic learning and demonstration.
