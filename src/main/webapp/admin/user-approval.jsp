<%@ include file="/includes/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="container mt-4">
    <div class="row mb-4">
        <div class="col-md-6">
            <h2>User Approval</h2>
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
            <h4>Pending User Approvals</h4>
        </div>
        <div class="card-body">
            <c:choose>
                <c:when test="${empty pendingUsers}">
                    <div class="alert alert-info">
                        <p>No pending users found.</p>
                    </div>
                </c:when>
                <c:otherwise>
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
                                <c:forEach var="user" items="${pendingUsers}">
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
                                            <c:if test="${user.registrationStatus == 'pending'}">
                                                <form action="${pageContext.request.contextPath}/admin/user-approval" method="post" style="display: inline;">
                                                    <input type="hidden" name="userId" value="${user.id}">
                                                    <input type="hidden" name="action" value="approve">
                                                    <button type="submit" class="btn btn-success btn-sm">
                                                        <i class="fas fa-check"></i> Approve
                                                    </button>
                                                </form>
                                                <form action="${pageContext.request.contextPath}/admin/user-approval" method="post" style="display: inline;">
                                                    <input type="hidden" name="userId" value="${user.id}">
                                                    <input type="hidden" name="action" value="reject">
                                                    <button type="submit" class="btn btn-danger btn-sm">
                                                        <i class="fas fa-times"></i> Reject
                                                    </button>
                                                </form>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<%@ include file="/includes/footer.jsp" %> 