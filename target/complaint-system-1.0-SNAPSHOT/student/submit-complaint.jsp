<%@ include file="/includes/header.jsp" %>
<%@ page import="com.college.complaint.dao.StaffCategoryDAO" %>
<%@ page import="com.college.complaint.model.StaffCategory" %>
<%@ page import="java.util.List" %>

<div class="row mb-4">
    <div class="col-md-8">
        <h2>Submit a Complaint</h2>
    </div>
    <div class="col-md-4 text-right">
        <a href="${pageContext.request.contextPath}/student/dashboard" class="btn btn-secondary">
            <i class="fas fa-arrow-left"></i> Back to Dashboard
        </a>
    </div>
</div>

<div class="card">
    <div class="card-header">
        <h4>Complaint Form</h4>
    </div>
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/student/submit-complaint" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="title" class="required">Title</label>
                <input type="text" class="form-control" id="title" name="title" placeholder="Brief title of your complaint" required>
            </div>
            
            <div class="form-group">
                <label for="category" class="required">Category</label>
                <select class="form-control" id="category" name="category" required onchange="updateStaffCategories()">
                    <option value="">Select Category</option>
                    <option value="Academic">Academic</option>
                    <option value="Infrastructure">Infrastructure</option>
                    <option value="Hostel">Hostel</option>
                    <option value="Canteen">Canteen</option>
                    <option value="Library">Library</option>
                    <option value="Sports">Sports</option>
                    <option value="Transport">Transport</option>
                    <option value="Other">Other</option>
                </select>
            </div>
            
            <div class="form-group">
                <label for="staffCategory" class="required">Assign to Staff Category</label>
                <select class="form-control" id="staffCategory" name="staffCategory" required>
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
            
            <div class="form-group">
                <label for="location" class="required">Location</label>
                <input type="text" class="form-control" id="location" name="location" placeholder="Where did this issue occur?" required>
            </div>
            
            <div class="form-group">
                <label for="description" class="required">Description</label>
                <textarea class="form-control" id="description" name="description" rows="5" placeholder="Detailed description of your complaint" required></textarea>
            </div>
            
            <div class="form-group">
                <label for="image">Supporting Image (Optional)</label>
                <input type="file" class="form-control-file" id="image" name="image" accept="image/*">
                <small class="form-text text-muted">Upload an image related to your complaint (max size: 5MB)</small>
            </div>
            
            <button type="submit" class="btn btn-primary">Submit Complaint</button>
        </form>
    </div>
</div>

<script>
function updateStaffCategories() {
    var category = document.getElementById("category").value;
    var staffCategory = document.getElementById("staffCategory");
    var options = staffCategory.getElementsByTagName("option");
    
    // Reset all options to visible
    for (var i = 0; i < options.length; i++) {
        options[i].style.display = "";
    }
    
    // Hide options that don't match the selected category
    if (category === "Infrastructure") {
        for (var i = 0; i < options.length; i++) {
            if (options[i].text !== "Infrastructure") {
                options[i].style.display = "none";
            }
        }
    } else if (category === "Library") {
        for (var i = 0; i < options.length; i++) {
            if (options[i].text !== "Library") {
                options[i].style.display = "none";
            }
        }
    } else if (category === "Academic") {
        for (var i = 0; i < options.length; i++) {
            if (options[i].text !== "Academic") {
                options[i].style.display = "none";
            }
        }
    }
    
    // Reset selection
    staffCategory.value = "";
}
</script>

<%@ include file="/includes/footer.jsp" %> 