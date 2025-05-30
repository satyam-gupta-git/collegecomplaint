<%@ include file="/includes/header.jsp" %>
<%@ page import="com.college.complaint.dao.StaffCategoryDAO" %>
<%@ page import="com.college.complaint.model.StaffCategory" %>
<%@ page import="java.util.List" %>

<div class="row">
    <div class="col-md-6 offset-md-3">
        <div class="card register-form">
            <div class="card-header">
                <h3 class="text-center">Register</h3>
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/register" method="post" id="registerForm" onsubmit="return validateForm()">
                    <div class="form-group">
                        <label for="username" class="required">Username</label>
                        <input type="text" class="form-control" id="username" name="username" required 
                               pattern="[a-zA-Z0-9_]{3,20}" title="Username must be 3-20 characters long and can only contain letters, numbers, and underscores">
                        <small class="form-text text-muted">3-20 characters, letters, numbers, and underscores only</small>
                    </div>
                    <div class="form-group">
                        <label for="password" class="required">Password</label>
                        <input type="password" class="form-control" id="password" name="password" required 
                               pattern=".{6,}" title="Password must be at least 6 characters long">
                        <small class="form-text text-muted">At least 6 characters long</small>
                    </div>
                    <div class="form-group">
                        <label for="confirmPassword" class="required">Confirm Password</label>
                        <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
                        <small class="form-text text-muted">Must match the password above</small>
                    </div>
                    <div class="form-group">
                        <label for="fullName" class="required">Full Name</label>
                        <input type="text" class="form-control" id="fullName" name="fullName" required>
                    </div>
                    <div class="form-group">
                        <label for="email" class="required">Email</label>
                        <input type="email" class="form-control" id="email" name="email" required>
                    </div>
                    <div class="form-group">
                        <label for="userType" class="required">User Type</label>
                        <select class="form-control" id="userType" name="userType" required onchange="toggleStaffCategory()">
                            <option value="">Select User Type</option>
                            <option value="student">Student</option>
                            <option value="staff">Staff</option>
                        </select>
                    </div>
                    <div class="form-group" id="staffCategoryGroup" style="display: none;">
                        <label for="staffCategory" class="required">Staff Category</label>
                        <select class="form-control" id="staffCategory" name="staffCategory">
                            <option value="">Select Staff Category</option>
                            <%
                            StaffCategoryDAO staffCategoryDAO = new StaffCategoryDAO();
                            try {
                                List<StaffCategory> categories = staffCategoryDAO.getAllCategories();
                                for (StaffCategory category : categories) {
                            %>
                                <option value="<%= category.getId() %>"><%= category.getCategoryName() %></option>
                            <%
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            %>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary btn-block">Register</button>
                </form>
                <div class="text-center mt-3">
                    <p>Already have an account? <a href="${pageContext.request.contextPath}/login">Login here</a></p>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
function validateForm() {
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirmPassword").value;
    var userType = document.getElementById("userType").value;
    var staffCategory = document.getElementById("staffCategory").value;
    
    if (password !== confirmPassword) {
        alert("Passwords do not match!");
        return false;
    }
    
    if (userType === "staff" && !staffCategory) {
        alert("Please select a staff category!");
        return false;
    }
    
    return true;
}

function toggleStaffCategory() {
    var userType = document.getElementById("userType").value;
    var staffCategoryGroup = document.getElementById("staffCategoryGroup");
    var staffCategory = document.getElementById("staffCategory");
    
    if (userType === "staff") {
        staffCategoryGroup.style.display = "block";
        staffCategory.required = true;
    } else {
        staffCategoryGroup.style.display = "none";
        staffCategory.required = false;
    }
}

// Real-time password match validation
document.getElementById("confirmPassword").addEventListener("input", function() {
    var password = document.getElementById("password").value;
    if (this.value !== password) {
        this.setCustomValidity("Passwords do not match");
    } else {
        this.setCustomValidity("");
    }
});
</script>

<%@ include file="/includes/footer.jsp" %> 