package com.college.complaint.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.college.complaint.dao.ComplaintDAO;
import com.college.complaint.model.Complaint;
import com.college.complaint.model.User;

/**
 * Servlet for handling complaint submission
 */
public class SubmitComplaintServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ComplaintDAO complaintDAO;
    private static final String UPLOAD_DIRECTORY = "uploads";
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 5; // 5MB
    private static final int MAX_MEMORY_SIZE = 1024 * 1024;
    
    @Override
    public void init() {
        complaintDAO = new ComplaintDAO();
        System.out.println("SubmitComplaintServlet initialized");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            System.out.println("No user session found, redirecting to login");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        if (!"student".equals(user.getUserType())) {
            System.out.println("User is not a student, redirecting to home");
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        
        // Forward to complaint submission form
        request.getRequestDispatcher("/student/submit-complaint.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            System.out.println("No user session found, redirecting to login");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        if (!"student".equals(user.getUserType())) {
            System.out.println("User is not a student, redirecting to home");
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        
        // Check if the request is multipart
        if (!ServletFileUpload.isMultipartContent(request)) {
            System.out.println("Form must have enctype=multipart/form-data");
            request.setAttribute("errorMessage", "Form must have enctype=multipart/form-data");
            request.getRequestDispatcher("/student/submit-complaint.jsp").forward(request, response);
            return;
        }
        
        try {
            // Create a factory for disk-based file items
            DiskFileItemFactory factory = new DiskFileItemFactory();
            
            // Set factory constraints
            factory.setSizeThreshold(MAX_MEMORY_SIZE);
            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
            
            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);
            
            // Set overall request size constraint
            upload.setSizeMax(MAX_FILE_SIZE);
            
            // Parse the request
            List<FileItem> items = upload.parseRequest(request);
            
            // Process the uploaded items
            Map<String, String> formFields = new HashMap<>();
            String imagePath = null;
            
            for (FileItem item : items) {
                if (item.isFormField()) {
                    // Process regular form field
                    formFields.put(item.getFieldName(), item.getString("UTF-8"));
                } else {
                    // Process uploaded file
                    if (item.getSize() > 0) {
                        String fileName = new File(item.getName()).getName();
                        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
                        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
                        
                        // Get the upload directory path
                        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
                        
                        // Create the upload directory if it does not exist
                        File uploadDir = new File(uploadPath);
                        if (!uploadDir.exists()) {
                            uploadDir.mkdirs();
                        }
                        
                        // Save the file
                        File file = new File(uploadPath + File.separator + uniqueFileName);
                        item.write(file);
                        
                        // Set the image path
                        imagePath = UPLOAD_DIRECTORY + "/" + uniqueFileName;
                    }
                }
            }
            
            // Validate form fields
            String title = formFields.get("title");
            String category = formFields.get("category");
            String location = formFields.get("location");
            String description = formFields.get("description");
            String staffCategoryStr = formFields.get("staffCategory");
            
            if (title == null || title.trim().isEmpty() ||
                category == null || category.trim().isEmpty() ||
                location == null || location.trim().isEmpty() ||
                description == null || description.trim().isEmpty() ||
                staffCategoryStr == null || staffCategoryStr.trim().isEmpty()) {
                
                System.out.println("Missing required fields");
                request.setAttribute("errorMessage", "All fields are required");
                request.getRequestDispatcher("/student/submit-complaint.jsp").forward(request, response);
                return;
            }
            
            // Parse staff category ID
            Integer staffCategoryId;
            try {
                staffCategoryId = Integer.parseInt(staffCategoryStr);
            } catch (NumberFormatException e) {
                System.err.println("Invalid staff category ID: " + staffCategoryStr);
                request.setAttribute("errorMessage", "Invalid staff category selected");
                request.getRequestDispatcher("/student/submit-complaint.jsp").forward(request, response);
                return;
            }
            
            // Create complaint object
            Complaint complaint = new Complaint();
            complaint.setUserId(user.getId());
            complaint.setTitle(title);
            complaint.setDescription(description);
            complaint.setCategory(category);
            complaint.setLocation(location);
            complaint.setImagePath(imagePath);
            complaint.setAssignedStaffCategoryId(staffCategoryId);
            complaint.setSubmissionDate(new Date());
            complaint.setStatus("pending");
            
            // Save complaint
            boolean success = complaintDAO.addComplaint(complaint);
            
            if (success) {
                System.out.println("Complaint submitted successfully by user: " + user.getUsername());
                request.setAttribute("successMessage", "Complaint submitted successfully");
                response.sendRedirect(request.getContextPath() + "/student/dashboard");
            } else {
                System.out.println("Failed to submit complaint for user: " + user.getUsername());
                request.setAttribute("errorMessage", "Complaint submission failed. Please try again.");
                request.getRequestDispatcher("/student/submit-complaint.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            System.err.println("Error in SubmitComplaintServlet: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            request.getRequestDispatcher("/student/submit-complaint.jsp").forward(request, response);
        }
    }
} 