<%@ include file="/includes/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row mb-4">
    <div class="col-md-6">
        <h2>Student Dashboard</h2>
    </div>
    <div class="col-md-6 text-right">
        <a href="${pageContext.request.contextPath}/student/submit-complaint" class="btn btn-primary mr-2">
            <i class="fas fa-plus"></i> Submit New Complaint
        </a>
        <a href="${pageContext.request.contextPath}/logout" class="btn btn-danger">
            <i class="fas fa-sign-out-alt"></i> Logout
        </a>
    </div>
</div>

<c:choose>
    <c:when test="${empty complaints}">
        <div class="alert alert-info">
            <p>You haven't submitted any complaints yet. <a href="${pageContext.request.contextPath}/student/submit-complaint">Submit a complaint</a> to get started.</p>
        </div>
    </c:when>
    <c:otherwise>
        <div class="card">
            <div class="card-header">
                <h4>Your Complaints</h4>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Title</th>
                                <th>Category</th>
                                <th>Submission Date</th>
                                <th>Status</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="complaint" items="${complaints}">
                                <tr>
                                    <td>${complaint.id}</td>
                                    <td>${complaint.title}</td>
                                    <td>${complaint.category}</td>
                                    <td><fmt:formatDate value="${complaint.submissionDate}" pattern="dd-MM-yyyy HH:mm" /></td>
                                    <td>
                                        <span class="complaint-status status-${complaint.status}">
                                            ${complaint.status}
                                        </span>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/view-complaint?id=${complaint.id}" class="btn btn-info btn-sm">
                                            <i class="fas fa-eye"></i> View
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </c:otherwise>
</c:choose>

<%@ include file="/includes/footer.jsp" %> 