package com.college.complaint.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.college.complaint.dao.ComplaintDAO;
import com.college.complaint.dao.UserDAO;
import com.college.complaint.model.Complaint;
import com.college.complaint.model.User;

public class AssignStaffServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
    private ComplaintDAO complaintDAO;

    public void init() throws ServletException {
        userDAO = new UserDAO();
        complaintDAO = new ComplaintDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (!"admin".equals(user.getUserType())) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        String complaintId = request.getParameter("id");
        if (complaintId == null || complaintId.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            return;
        }

        try {
            Complaint complaint = complaintDAO.getComplaintById(Integer.parseInt(complaintId));
            if (complaint == null) {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                return;
            }

            List<User> staffList = userDAO.getStaffByCategory(complaint.getAssignedStaffCategoryId());
            request.setAttribute("complaint", complaint);
            request.setAttribute("staffList", staffList);
            request.getRequestDispatcher("/admin/assign-staff.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error loading complaint details: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (!"admin".equals(user.getUserType())) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        String complaintId = request.getParameter("complaintId");
        String staffId = request.getParameter("staffId");

        if (complaintId == null || staffId == null || 
            complaintId.trim().isEmpty() || staffId.trim().isEmpty()) {
            request.setAttribute("error", "Invalid complaint or staff selection");
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            return;
        }

        try {
            // Get the staff member's category ID
            User staffMember = userDAO.getUserById(Integer.parseInt(staffId));
            if (staffMember == null || staffMember.getStaffCategoryId() == null) {
                request.setAttribute("error", "Invalid staff member selected");
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                return;
            }

            boolean success = complaintDAO.assignStaff(
                Integer.parseInt(complaintId), 
                staffMember.getStaffCategoryId()
            );

            if (success) {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard?success=Staff assigned successfully");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard?error=Failed to assign staff");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/dashboard?error=" + e.getMessage());
        }
    }
} 