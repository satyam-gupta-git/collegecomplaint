<%@ include file="/includes/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="container mt-4">
    <div class="row mb-4">
        <div class="col-md-6">
            <h2>Manage Users</h2>
        </div>
        <div class="col-md-6 text-right">
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-secondary">
                <i class="fas fa-arrow-left"></i> Back to Dashboard
            </a>
        </div>
    </div>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">
            ${errorMessage}
        </div>
    </c:if>

    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">
            ${successMessage}
        </div>
    </c:if>

    <div class="card">
        <div class="card-header">
            <h4>User List</h4>
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Username</th>
                            <th>Full Name</th>
                            <th>Email</th>
                            <th>User Type</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="user" items="${users}">
                            <tr>
                                <td>${user.username}</td>
                                <td>${user.fullName}</td>
                                <td>${user.email}</td>
                                <td>${user.userType}</td>
                                <td>
                                    <span class="badge badge-${user.registrationStatus == 'pending' ? 'warning' : user.registrationStatus == 'approved' ? 'success' : 'danger'}">
                                        ${user.registrationStatus}
                                    </span>
                                </td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/admin/manage-users" method="post" style="display: inline;">
                                        <input type="hidden" name="userId" value="${user.id}">
                                        <input type="hidden" name="action" value="delete">
                                        <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this user?')">
                                            <i class="fas fa-trash"></i> Delete
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<%@ include file="/includes/footer.jsp" %> 