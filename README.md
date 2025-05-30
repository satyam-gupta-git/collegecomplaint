# College Complaint Management System

A web application for managing college complaints, where students can submit complaints with images, staff can resolve them, and an admin panel manages all users and complaints.

---

## Features

- **User authentication** (Student, Staff, and Admin login)
- **Student Features**:
  - Submit complaints with details and supporting images
  - View submitted complaints and their current status
- **Staff Features**:
  - View complaints relevant to their category
  - Update complaint status (Pending, In-Progress, Resolved)
  - Provide resolution details
- **Admin Panel**:
  - Manage all student and staff accounts
  - View and monitor all complaints
  - Perform administrative tasks such as adding/removing users and reviewing complaint statistics

---

## Technologies Used

- Java Servlets and JSP
- MySQL (via **XAMPP**)
- Bootstrap 4 for UI
- Apache Tomcat Server
- JSTL for JSP templating
- Apache Commons FileUpload for image upload

---

## Setup Instructions

### Prerequisites

- JDK 11 or higher
- Apache Tomcat 9.0 or higher
- [XAMPP](https://www.apachefriends.org/index.html) with MySQL server
- Maven

---

### Database Setup

1. Install and start **XAMPP**, then start **Apache** and **MySQL** from the XAMPP Control Panel.
2. Open **phpMyAdmin** (http://localhost/phpmyadmin).
3. Create a new database (e.g., `college_complaints`).
4. Import the `database.sql` file to set up the tables:
   - Click on your new database
   - Go to the **Import** tab
   - Select the `database.sql` file and import

---

### Application Setup

1. Clone or download the project repository.
2. Update database connection settings in:

   ```
   src/main/java/com/college/complaint/util/DatabaseUtil.java
   ```

   Replace with your XAMPP MySQL credentials, e.g.:

   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/college_complaints";
   private static final String USER = "root";
   private static final String PASSWORD = "";
   ```

3. Build the project using Maven:

   ```bash
   mvn clean package
   ```

4. Deploy the generated WAR file:
   - Copy `target/complaint-system-1.0-SNAPSHOT.war` to `xampp/tomcat/webapps/`
   - Start **Apache Tomcat** via XAMPP (`xampp-control.exe` → Tomcat → Start)

---

### Accessing the Application

- Open your browser and go to:
  ```
  http://localhost:8080/complaint-system/
  ```

#### Default Login Credentials

- **Student**:  
  `Username: student1`  
  `Password: password`

- **Staff**:  
  `Username: staff1`  
  `Password: password`

- **Admin**:  
  `Username: admin`  
  `Password: admin123`  
  (Update this according to your database if needed)

---

## Directory Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/college/complaint/
│   │       ├── model/        # Model classes
│   │       ├── dao/          # Data Access Objects
│   │       ├── servlet/      # Servlet controllers
│   │       └── util/         # Utility classes (Database, FileUpload, etc.)
│   └── webapp/
│       ├── css/              # Stylesheets
│       ├── includes/         # Common JSP includes (header, footer, etc.)
│       ├── admin/            # Admin dashboard and controls
│       ├── student/          # Student portal pages
│       ├── staff/            # Staff dashboard and complaint management
│       └── WEB-INF/          # Web configuration (web.xml)
```

---
