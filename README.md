# College Complaint Management System

A web application for managing college complaints, where students can submit complaints with images and staff can manage and resolve them.

## Features

- User authentication (Student and Staff login)
- Student features:
  - Submit complaints with details and supporting images
  - View submitted complaints and their status
- Staff features:
  - View all complaints
  - Update complaint status (pending, in-progress, resolved)
  - Provide resolution details

## Technologies Used

- Java Servlets and JSP
- MySQL Database
- Bootstrap 4 for UI
- Apache Tomcat Server
- JSTL for JSP templating
- Apache Commons FileUpload for image upload

## Setup Instructions

### Prerequisites

- JDK 11 or higher
- Apache Tomcat 9.0 or higher
- MySQL 5.7 or higher
- Maven

### Database Setup

1. Install and start MySQL server
2. Create the database and tables using the provided SQL script:
   ```
   mysql -u root -p < database.sql
   ```

### Application Setup

1. Clone the repository
2. Update the database connection settings in `src/main/java/com/college/complaint/util/DatabaseUtil.java` if needed
3. Build the project using Maven:
   ```
   mvn clean package
   ```
4. Deploy the generated WAR file to Tomcat:
   - Copy the WAR file from `target/complaint-system-1.0-SNAPSHOT.war` to Tomcat's `webapps` directory
   - Start Tomcat server

### Accessing the Application

1. Open a web browser and navigate to `http://localhost:8080/complaint-system/`
2. Use the following credentials to log in:
   - Student: username: `student1`, password: `password`
   - Staff: username: `staff1`, password: `password`

## Directory Structure

- `src/main/java/com/college/complaint/model/` - Model classes
- `src/main/java/com/college/complaint/dao/` - Data Access Objects
- `src/main/java/com/college/complaint/servlet/` - Servlet controllers
- `src/main/java/com/college/complaint/util/` - Utility classes
- `src/main/webapp/` - JSP pages and web resources
- `src/main/webapp/WEB-INF/` - Web configuration
- `src/main/webapp/css/` - CSS stylesheets
- `src/main/webapp/includes/` - Common JSP includes

## License

This project is licensed under the MIT License. # collegecomplaint
