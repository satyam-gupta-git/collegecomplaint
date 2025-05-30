<%@ include file="/includes/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="container mt-4">
    <div class="row mb-4">
        <div class="col-md-6">
            <h2>Admin Dashboard</h2>
        </div>
        <div class="col-md-6 text-right">
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-danger">
                <i class="fas fa-sign-out-alt"></i> Logout
            </a>
        </div>
    </div>

    <div class="row">
        <div class="col-md-4 mb-4">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">User Management</h5>
                    <p class="card-text">Manage user registrations and approvals</p>
                    <div class="btn-group">
                        <a href="${pageContext.request.contextPath}/admin/manage-users" class="btn btn-primary">Manage Users</a>
                        <a href="${pageContext.request.contextPath}/admin/user-approval" class="btn btn-warning">Pending Approvals</a>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="col-md-4 mb-4">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Staff Categories</h5>
                    <p class="card-text">Manage staff categories and assignments</p>
                    <a href="${pageContext.request.contextPath}/admin/staff-categories" class="btn btn-primary">Manage Categories</a>
                </div>
            </div>
        </div>
        
        <div class="col-md-4 mb-4">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Complaints Overview</h5>
                    <p class="card-text">View and manage all complaints</p>
                    <a href="${pageContext.request.contextPath}/admin/complaints" class="btn btn-primary">View Complaints</a>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <div class="card">
                <div class="card-header">
                    <h4>System Statistics</h4>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-3">
                            <div class="card bg-primary text-white">
                                <div class="card-body text-center">
                                    <h5>Total Users</h5>
                                    <h3>${totalUsers}</h3>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="card bg-success text-white">
                                <div class="card-body text-center">
                                    <h5>Total Complaints</h5>
                                    <h3>${totalComplaints}</h3>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="card bg-warning text-dark">
                                <div class="card-body text-center">
                                    <h5>Pending Approvals</h5>
                                    <h3>${pendingApprovals}</h3>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="card bg-info text-white">
                                <div class="card-body text-center">
                                    <h5>Active Staff</h5>
                                    <h3>${activeStaff}</h3>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/includes/footer.jsp" %> 