<%@ include file="/includes/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="container mt-4">
    <div class="row mb-4">
        <div class="col-md-6">
            <h2>Staff Dashboard - Complaint Management</h2>
        </div>
        <div class="col-md-6 text-right">
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-danger">
                <i class="fas fa-sign-out-alt"></i> Logout
            </a>
        </div>
    </div>

    <!-- Display error message if any -->
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${errorMessage}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:if>

    <!-- Display success message if any -->
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            ${successMessage}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:if>

    <!-- Statistics Cards -->
    <div class="row mb-4">
        <div class="col-md-4">
            <div class="card bg-warning text-white">
                <div class="card-body text-center">
                    <h5 class="card-title">Pending Complaints</h5>
                    <h2>
                        <c:set var="pendingCount" value="0" />
                        <c:forEach var="complaint" items="${complaints}">
                            <c:if test="${complaint.status eq 'pending'}">
                                <c:set var="pendingCount" value="${pendingCount + 1}" />
                            </c:if>
                        </c:forEach>
                        ${pendingCount}
                    </h2>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card bg-info text-white">
                <div class="card-body text-center">
                    <h5 class="card-title">In Progress</h5>
                    <h2>
                        <c:set var="inProgressCount" value="0" />
                        <c:forEach var="complaint" items="${complaints}">
                            <c:if test="${complaint.status eq 'in-progress'}">
                                <c:set var="inProgressCount" value="${inProgressCount + 1}" />
                            </c:if>
                        </c:forEach>
                        ${inProgressCount}
                    </h2>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card bg-success text-white">
                <div class="card-body text-center">
                    <h5 class="card-title">Resolved</h5>
                    <h2>
                        <c:set var="resolvedCount" value="0" />
                        <c:forEach var="complaint" items="${complaints}">
                            <c:if test="${complaint.status eq 'resolved'}">
                                <c:set var="resolvedCount" value="${resolvedCount + 1}" />
                            </c:if>
                        </c:forEach>
                        ${resolvedCount}
                    </h2>
                </div>
            </div>
        </div>
    </div>

    <!-- Complaints Table -->
    <div class="card">
        <div class="card-header">
            <h4>Assigned Complaints</h4>
        </div>
        <div class="card-body">
            <c:choose>
                <c:when test="${empty complaints}">
                    <div class="alert alert-info">
                        <p>No complaints have been assigned to you yet.</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="table-responsive">
                        <table class="table table-striped table-hover">
                            <thead class="thead-dark">
                                <tr>
                                    <th>ID</th>
                                    <th>Title</th>
                                    <th>Category</th>
                                    <th>Submission Date</th>
                                    <th>Status</th>
                                    <th>Actions</th>
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
                                            <span class="badge badge-${complaint.status eq 'pending' ? 'warning' : 
                                                                      complaint.status eq 'in-progress' ? 'info' : 
                                                                      'success'}">
                                                ${complaint.status}
                                            </span>
                                        </td>
                                        <td>
                                            <button type="button" class="btn btn-info btn-sm" data-toggle="modal" data-target="#viewModal${complaint.id}" title="View Details">
                                                <i class="fas fa-eye"></i>
                                            </button>
                                            <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#updateModal${complaint.id}" title="Update Status">
                                                <i class="fas fa-edit"></i>
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

<!-- View and Update Modals -->
<c:forEach var="complaint" items="${complaints}">
    <!-- View Modal -->
    <div class="modal fade" id="viewModal${complaint.id}" tabindex="-1" role="dialog" aria-labelledby="viewModalLabel${complaint.id}" aria-hidden="true">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="viewModalLabel${complaint.id}">Complaint Details</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-6">
                            <p><strong>ID:</strong> ${complaint.id}</p>
                            <p><strong>Title:</strong> ${complaint.title}</p>
                            <p><strong>Category:</strong> ${complaint.category}</p>
                            <p><strong>Location:</strong> ${complaint.location}</p>
                            <p><strong>Submission Date:</strong> <fmt:formatDate value="${complaint.submissionDate}" pattern="dd-MM-yyyy HH:mm" /></p>
                            <p><strong>Status:</strong> 
                                <span class="badge badge-${complaint.status eq 'pending' ? 'warning' : 
                                                          complaint.status eq 'in-progress' ? 'info' : 
                                                          'success'}">
                                    ${complaint.status}
                                </span>
                            </p>
                        </div>
                        <div class="col-md-6">
                            <p><strong>Description:</strong></p>
                            <p>${complaint.description}</p>
                            <c:if test="${not empty complaint.imagePath}">
                                <p><strong>Attached Image:</strong></p>
                                <img src="${pageContext.request.contextPath}/${complaint.imagePath}" alt="Complaint Image" class="img-fluid">
                            </c:if>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Update Modal -->
    <div class="modal fade" id="updateModal${complaint.id}" tabindex="-1" role="dialog" aria-labelledby="updateModalLabel${complaint.id}" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="updateModalLabel${complaint.id}">Update Complaint Status</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <form action="${pageContext.request.contextPath}/staff/update-complaint" method="post">
                    <div class="modal-body">
                        <input type="hidden" name="complaintId" value="${complaint.id}">
                        <div class="form-group">
                            <label for="status${complaint.id}">Status</label>
                            <select class="form-control" id="status${complaint.id}" name="status" required>
                                <option value="pending" ${complaint.status eq 'pending' ? 'selected' : ''}>Pending</option>
                                <option value="in-progress" ${complaint.status eq 'in-progress' ? 'selected' : ''}>In Progress</option>
                                <option value="resolved" ${complaint.status eq 'resolved' ? 'selected' : ''}>Resolved</option>
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

<style>
    .card {
        box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
        margin-bottom: 1.5rem;
    }
    
    .card-header {
        background-color: #f8f9fa;
        border-bottom: 1px solid rgba(0, 0, 0, 0.125);
    }
    
    .table th {
        background-color: #f8f9fa;
    }
    
    .badge {
        padding: 0.5em 0.75em;
        font-size: 0.875em;
    }
    
    .btn-sm {
        padding: 0.25rem 0.5rem;
        font-size: 0.875rem;
        margin-right: 0.25rem;
    }
</style>

<%@ include file="/includes/footer.jsp" %> 