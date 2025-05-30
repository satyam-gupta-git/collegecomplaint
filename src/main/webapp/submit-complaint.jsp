<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Submit Complaint - College Complaints</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .navbar {
            background-color: #343a40;
        }
        .navbar-brand, .nav-link {
            color: white !important;
        }
        .nav-link:hover {
            color: #17a2b8 !important;
        }
        .logout-btn {
            margin-left: auto;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container">
            <a class="navbar-brand" href="dashboard.jsp">College Complaints</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="dashboard.jsp">Dashboard</a>
                    </li>
                    <li class="nav-item active">
                        <a class="nav-link" href="submit-complaint.jsp">Submit Complaint</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="my-complaints.jsp">My Complaints</a>
                    </li>
                    <% if (session.getAttribute("userType").equals("admin")) { %>
                        <li class="nav-item">
                            <a class="nav-link" href="user-approval.jsp">User Approval</a>
                        </li>
                    <% } %>
                </ul>
                <div class="navbar-nav">
                    <span class="nav-item nav-link text-light">
                        Welcome, <%= session.getAttribute("username") %>
                    </span>
                    <a class="nav-link logout-btn" href="logout">Logout</a>
                </div>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <h2>Submit a New Complaint</h2>
        <form action="submit-complaint" method="post" enctype="multipart/form-data" class="mt-4">
            <div class="form-group">
                <label for="title">Title</label>
                <input type="text" class="form-control" id="title" name="title" required>
            </div>
            <div class="form-group">
                <label for="description">Description</label>
                <textarea class="form-control" id="description" name="description" rows="3" required></textarea>
            </div>
            <div class="form-group">
                <label for="category">Category</label>
                <select class="form-control" id="category" name="category" required>
                    <option value="">Select a category</option>
                    <option value="Infrastructure">Infrastructure</option>
                    <option value="Library">Library</option>
                    <option value="IT Support">IT Support</option>
                    <option value="Academic">Academic</option>
                    <option value="Administrative">Administrative</option>
                </select>
            </div>
            <div class="form-group">
                <label for="location">Location</label>
                <input type="text" class="form-control" id="location" name="location" required>
            </div>
            <div class="form-group">
                <label for="image">Image (optional)</label>
                <input type="file" class="form-control-file" id="image" name="image" accept="image/*">
            </div>
            <button type="submit" class="btn btn-primary">Submit Complaint</button>
        </form>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html> 