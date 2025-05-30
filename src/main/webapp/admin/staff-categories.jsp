<%@ include file="/includes/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="container mt-4">
    <div class="row mb-4">
        <div class="col-md-6">
            <h2>Staff Categories Management</h2>
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

    <div class="card mb-4">
        <div class="card-header">
            <h4>Add New Category</h4>
        </div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/admin/staff-categories" method="post">
                <input type="hidden" name="action" value="add">
                <div class="form-group">
                    <label for="categoryName">Category Name</label>
                    <input type="text" class="form-control" id="categoryName" name="categoryName" required>
                </div>
                <div class="form-group">
                    <label for="description">Description</label>
                    <textarea class="form-control" id="description" name="description" rows="3"></textarea>
                </div>
                <button type="submit" class="btn btn-primary">Add Category</button>
            </form>
        </div>
    </div>

    <div class="card">
        <div class="card-header">
            <h4>Existing Categories</h4>
        </div>
        <div class="card-body">
            <c:choose>
                <c:when test="${empty categories}">
                    <div class="alert alert-info">
                        <p>No staff categories found. Please add a new category.</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="table-responsive">
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>Description</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="category" items="${categories}">
                                    <tr>
                                        <td>${category.id}</td>
                                        <td>${category.categoryName}</td>
                                        <td>${category.description}</td>
                                        <td>
                                            <button type="button" class="btn btn-sm btn-primary" data-toggle="modal" data-target="#editModal${category.id}">
                                                <i class="fas fa-edit"></i> Edit
                                            </button>
                                            <form action="${pageContext.request.contextPath}/admin/staff-categories" method="post" style="display: inline;">
                                                <input type="hidden" name="action" value="delete">
                                                <input type="hidden" name="categoryId" value="${category.id}">
                                                <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure you want to delete this category?')">
                                                    <i class="fas fa-trash"></i> Delete
                                                </button>
                                            </form>
                                        </td>
                                    </tr>
                                    
                                    <!-- Edit Modal -->
                                    <div class="modal fade" id="editModal${category.id}" tabindex="-1" role="dialog" aria-labelledby="editModalLabel${category.id}" aria-hidden="true">
                                        <div class="modal-dialog" role="document">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="editModalLabel${category.id}">Edit Category</h5>
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                </div>
                                                <form action="${pageContext.request.contextPath}/admin/staff-categories" method="post">
                                                    <div class="modal-body">
                                                        <input type="hidden" name="action" value="update">
                                                        <input type="hidden" name="categoryId" value="${category.id}">
                                                        <div class="form-group">
                                                            <label for="editName${category.id}">Category Name</label>
                                                            <input type="text" class="form-control" id="editName${category.id}" name="categoryName" value="${category.categoryName}" required>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="editDescription${category.id}">Description</label>
                                                            <textarea class="form-control" id="editDescription${category.id}" name="description" rows="3">${category.description}</textarea>
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                                        <button type="submit" class="btn btn-primary">Save Changes</button>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
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