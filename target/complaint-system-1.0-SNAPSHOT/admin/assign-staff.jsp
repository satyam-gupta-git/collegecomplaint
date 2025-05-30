<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Assign Staff - College Complaint Management System</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="/includes/header.jsp" %>

    <div class="container mt-4">
        <div class="row">
            <div class="col-md-12">
                <h2>Assign Staff to Complaint</h2>
                <div class="card mb-4">
                    <div class="card-body">
                        <h5 class="card-title">Complaint Details</h5>
                        <p><strong>Title:</strong> ${complaint.title}</p>
                        <p><strong>Description:</strong> ${complaint.description}</p>
                        <p><strong>Category:</strong> ${complaint.category}</p>
                        <p><strong>Status:</strong> ${complaint.status}</p>
                    </div>
                </div>

                <form action="${pageContext.request.contextPath}/admin/assign-staff" method="post">
                    <input type="hidden" name="complaintId" value="${complaint.id}">
                    <div class="form-group">
                        <label for="staffId">Select Staff Member:</label>
                        <select class="form-control" id="staffId" name="staffId" required>
                            <option value="">Choose a staff member...</option>
                            <c:forEach items="${staffList}" var="staff">
                                <option value="${staff.id}">${staff.fullName} (${staff.email})</option>
                            </c:forEach>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary">Assign Staff</button>
                    <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-secondary">Cancel</a>
                </form>
            </div>
        </div>
    </div>

    <%@ include file="/includes/footer.jsp" %>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html> 