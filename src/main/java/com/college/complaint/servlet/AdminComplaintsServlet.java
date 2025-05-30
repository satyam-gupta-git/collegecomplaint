package com.college.complaint.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.college.complaint.dao.ComplaintDAO;
import com.college.complaint.model.Complaint;
import com.college.complaint.model.User;

/**
 * Servlet for handling admin complaints view
 */
public class AdminComplaintsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ComplaintDAO complaintDAO;
    
    public void init() throws ServletException {
        complaintDAO = new ComplaintDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is logged in and is an admin
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        if (!"admin".equals(user.getUserType())) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        
        try {
            // Get all complaints
            List<Complaint> complaints = complaintDAO.getAllComplaints();
            request.setAttribute("complaints", complaints);
            
            // Forward to admin complaints page
            request.getRequestDispatcher("/admin/complaints.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error loading complaints: " + e.getMessage());
            request.getRequestDispatcher("/admin/complaints.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is logged in and is an admin
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        if (!"admin".equals(user.getUserType())) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        
        String action = request.getParameter("action");
        
        try {
            if ("update".equals(action)) {
                int complaintId = Integer.parseInt(request.getParameter("complaintId"));
                String status = request.getParameter("status");
                String resolution = request.getParameter("resolution");
                
                complaintDAO.updateComplaintStatus(complaintId, status, resolution);
                request.setAttribute("successMessage", "Complaint updated successfully");
            }
            
            // Redirect back to admin complaints page
            response.sendRedirect(request.getContextPath() + "/admin/complaints");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error updating complaint: " + e.getMessage());
            request.getRequestDispatcher("/admin/complaints.jsp").forward(request, response);
        }
    }
} 