<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard - College Complaints</title>
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
                    <li class="nav-item active">
                        <a class="nav-link" href="dashboard.jsp">Dashboard</a>
                    </li>
                    <li class="nav-item">
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
        <h2>Welcome to the College Complaints System</h2>
        <div class="row mt-4">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Submit a New Complaint</h5>
                        <p class="card-text">Have an issue? Submit a new complaint for quick resolution.</p>
                        <a href="submit-complaint.jsp" class="btn btn-primary">Submit Complaint</a>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">View My Complaints</h5>
                        <p class="card-text">Track the status of your submitted complaints.</p>
                        <a href="my-complaints.jsp" class="btn btn-primary">View Complaints</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html> 