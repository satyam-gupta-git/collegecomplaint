<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.college.model.Complaint" %>
<!DOCTYPE html>
<html>
<head>
    <title>My Complaints - College Complaints</title>
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
        .status-badge {
            font-size: 0.9em;
            padding: 0.4em 0.6em;
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
                    <li class="nav-item">
                        <a class="nav-link" href="submit-complaint.jsp">Submit Complaint</a>
                    </li>
                    <li class="nav-item active">
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
        <h2>My Complaints</h2>
        <div class="table-responsive mt-4">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Category</th>
                        <th>Location</th>
                        <th>Submission Date</th>
                        <th>Status</th>
                        <th>Resolution</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                    List<Complaint> complaints = (List<Complaint>) request.getAttribute("complaints");
                    if (complaints != null && !complaints.isEmpty()) {
                        for (Complaint complaint : complaints) {
                    %>
                        <tr>
                            <td><%= complaint.getTitle() %></td>
                            <td><%= complaint.getCategory() %></td>
                            <td><%= complaint.getLocation() %></td>
                            <td><%= complaint.getSubmissionDate() %></td>
                            <td>
                                <span class="badge badge-<%= 
                                    complaint.getStatus().equals("pending") ? "warning" : 
                                    complaint.getStatus().equals("in-progress") ? "info" : "success" 
                                %> status-badge">
                                    <%= complaint.getStatus() %>
                                </span>
                            </td>
                            <td><%= complaint.getResolution() != null ? complaint.getResolution() : "-" %></td>
                        </tr>
                    <% 
                        }
                    } else {
                    %>
                        <tr>
                            <td colspan="6" class="text-center">No complaints found</td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html> 