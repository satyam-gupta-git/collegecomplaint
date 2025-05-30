<%@ include file="/includes/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="container mt-4">
    <div class="row mb-4">
        <div class="col-md-6">
            <h2>Complaints Management</h2>
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
            <h4>All Complaints</h4>
        </div>
        <div class="card-body">
            <c:choose>
                <c:when test="${empty complaints}">
                    <div class="alert alert-info">
                        <p>No complaints found in the system.</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="table-responsive">
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Title</th>
                                    <th>Category</th>
                                    <th>Status</th>
                                    <th>Submission Date</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="complaint" items="${complaints}">
                                    <tr>
                                        <td>${complaint.id}</td>
                                        <td>${complaint.title}</td>
                                        <td>${complaint.category}</td>
                                        <td>
                                            <span class="badge badge-${complaint.status == 'pending' ? 'warning' : complaint.status == 'in-progress' ? 'info' : 'success'}">
                                                ${complaint.status}
                                            </span>
                                        </td>
                                        <td><fmt:formatDate value="${complaint.submissionDate}" pattern="dd-MM-yyyy HH:mm" /></td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/view-complaint?id=${complaint.id}" class="btn btn-sm btn-info">
                                                <i class="fas fa-eye"></i> View
                                            </a>
                                            <button type="button" class="btn btn-sm btn-primary" data-toggle="modal" data-target="#updateModal${complaint.id}">
                                                <i class="fas fa-edit"></i> Update
                                            </button>
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

<!-- Update Modals -->
<c:forEach var="complaint" items="${complaints}">
    <div class="modal fade" id="updateModal${complaint.id}" tabindex="-1" role="dialog" aria-labelledby="updateModalLabel${complaint.id}" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="updateModalLabel${complaint.id}">Update Complaint Status</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <form action="${pageContext.request.contextPath}/admin/complaints" method="post">
                    <div class="modal-body">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="complaintId" value="${complaint.id}">
                        <div class="form-group">
                            <label for="status${complaint.id}">Status</label>
                            <select class="form-control" id="status${complaint.id}" name="status" required>
                                <option value="pending" ${complaint.status == 'pending' ? 'selected' : ''}>Pending</option>
                                <option value="in-progress" ${complaint.status == 'in-progress' ? 'selected' : ''}>In Progress</option>
                                <option value="resolved" ${complaint.status == 'resolved' ? 'selected' : ''}>Resolved</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="resolution${complaint.id}">Resolution</label>
                            <textarea class="form-control" id="resolution${complaint.id}" name="resolution" rows="3">${complaint.resolution}</textarea>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary">Update Status</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</c:forEach>

<%@ include file="/includes/footer.jsp" %> 