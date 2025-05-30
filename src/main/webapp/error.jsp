<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Error - College Complaints</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .error-container {
            text-align: center;
            padding: 50px 20px;
        }
        .error-code {
            font-size: 72px;
            font-weight: bold;
            color: #dc3545;
        }
        .error-message {
            font-size: 24px;
            margin: 20px 0;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="error-container">
            <div class="error-code">
                <%= request.getAttribute("javax.servlet.error.status_code") != null ? 
                    request.getAttribute("javax.servlet.error.status_code") : "500" %>
            </div>
            <div class="error-message">
                <%= request.getAttribute("javax.servlet.error.message") != null ? 
                    request.getAttribute("javax.servlet.error.message") : "An error occurred" %>
            </div>
            <a href="${pageContext.request.contextPath}/" class="btn btn-primary">Return to Home</a>
        </div>
    </div>
</body>
</html> 