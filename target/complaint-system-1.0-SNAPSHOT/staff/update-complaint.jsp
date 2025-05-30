<%@ include file="/includes/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row mb-4">
    <div class="col-md-8">
        <h2>Update Complaint Status</h2>
    </div>
    <div class="col-md-4 text-right">
        <a href="${pageContext.request.contextPath}/staff/dashboard" class="btn btn-secondary">
            <i class="fas fa-arrow-left"></i> Back to Dashboard
        </a>
    </div>
</div>

<c:if test="${empty complaint}">
    <div class="alert alert-danger">
        <p>Complaint not found.</p>
    </div>
</c:if>

<c:if test="${not empty complaint}">
    <div class="row">
        <div class="col-md-6">
            <div class="card mb-4">
                <div class="card-header">
                    <h4>Complaint Details</h4>
                </div>
                <div class="card-body">
                    <p><strong>ID:</strong> ${complaint.id}</p>
                    <p><strong>Title:</strong> ${complaint.title}</p>
                    <p><strong>Category:</strong> ${complaint.category}</p>
                    <p><strong>Location:</strong> ${complaint.location}</p>
                    <p><strong>Submission Date:</strong> <fmt:formatDate value="${complaint.submissionDate}" pattern="dd-MM-yyyy HH:mm" /></p>
                    <p><strong>Current Status:</strong> 
                        <span class="complaint-status status-${complaint.status}">
                            ${complaint.status}
                        </span>
                    </p>
                    <p><strong>Description:</strong> ${complaint.description}</p>
                    
                    <c:if test="${not empty complaint.imagePath}">
                        <p><strong>Attached Image:</strong></p>
                        <img src="${pageContext.request.contextPath}/${complaint.imagePath}" alt="Complaint Image" class="img-fluid complaint-image">
                    </c:if>
                </div>
            </div>
        </div>
        
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">
                    <h4>Update Status</h4>
                </div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/staff/update-complaint" method="post">
                        <input type="hidden" name="id" value="${complaint.id}">
                        
                        <div class="form-group">
                            <label for="status" class="required">Status</label>
                            <select class="form-control" id="status" name="status" required>
                                <option value="">Select Status</option>
                                <option value="pending" ${complaint.status eq 'pending' ? 'selected' : ''}>Pending</option>
                                <option value="in-progress" ${complaint.status eq 'in-progress' ? 'selected' : ''}>In Progress</option>
                                <option value="resolved" ${complaint.status eq 'resolved' ? 'selected' : ''}>Resolved</option>
                            </select>
                        </div>
                        
                        <div class="form-group">
                            <label for="resolution">Resolution/Comments</label>
                            <textarea class="form-control" id="resolution" name="resolution" rows="5" placeholder="Provide resolution details or comments">${complaint.resolution}</textarea>
                            <small class="form-text text-muted">Required when marking as resolved</small>
                        </div>
                        
                        <button type="submit" class="btn btn-primary">Update Status</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</c:if>

<%@ include file="/includes/footer.jsp" %> 