<%@ include file="/includes/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row mb-4">
    <div class="col-md-8">
        <h2>Complaint Details</h2>
    </div>
    <div class="col-md-4 text-right">
        <c:choose>
            <c:when test="${sessionScope.user.userType eq 'student'}">
                <a href="${pageContext.request.contextPath}/student/dashboard" class="btn btn-secondary">
                    <i class="fas fa-arrow-left"></i> Back to Dashboard
                </a>
            </c:when>
            <c:when test="${sessionScope.user.userType eq 'staff'}">
                <a href="${pageContext.request.contextPath}/staff/dashboard" class="btn btn-secondary">
                    <i class="fas fa-arrow-left"></i> Back to Dashboard
                </a>
            </c:when>
        </c:choose>
    </div>
</div>

<c:if test="${empty complaint}">
    <div class="alert alert-danger">
        <p>Complaint not found.</p>
    </div>
</c:if>

<c:if test="${not empty complaint}">
    <div class="card mb-4">
        <div class="card-header">
            <h4>${complaint.title}</h4>
        </div>
        <div class="card-body">
            <div class="row">
                <div class="col-md-6">
                    <p><strong>ID:</strong> ${complaint.id}</p>
                    <p><strong>Submitted By:</strong> ${submitter.fullName}</p>
                    <p><strong>Category:</strong> ${complaint.category}</p>
                    <p><strong>Location:</strong> ${complaint.location}</p>
                    <p><strong>Submission Date:</strong> <fmt:formatDate value="${complaint.submissionDate}" pattern="dd-MM-yyyy HH:mm" /></p>
                    <p><strong>Status:</strong> 
                        <span class="complaint-status status-${complaint.status}">
                            ${complaint.status}
                        </span>
                    </p>
                </div>
                <div class="col-md-6">
                    <c:if test="${not empty complaint.imagePath}">
                        <img src="${pageContext.request.contextPath}/${complaint.imagePath}" alt="Complaint Image" class="img-fluid complaint-image">
                    </c:if>
                </div>
            </div>
            
            <div class="mt-4">
                <h5>Description:</h5>
                <p>${complaint.description}</p>
            </div>
            
            <c:if test="${not empty complaint.resolution}">
                <div class="mt-4">
                    <h5>Resolution:</h5>
                    <p>${complaint.resolution}</p>
                    <c:if test="${not empty complaint.resolutionDate}">
                        <p><small>Resolved on: <fmt:formatDate value="${complaint.resolutionDate}" pattern="dd-MM-yyyy HH:mm" /></small></p>
                    </c:if>
                </div>
            </c:if>
            
            <c:if test="${sessionScope.user.userType eq 'staff'}">
                <div class="mt-4">
                    <a href="${pageContext.request.contextPath}/staff/update-complaint?id=${complaint.id}" class="btn btn-primary">
                        <i class="fas fa-edit"></i> Update Status
                    </a>
                </div>
            </c:if>
        </div>
    </div>
</c:if>

<%@ include file="/includes/footer.jsp" %> 