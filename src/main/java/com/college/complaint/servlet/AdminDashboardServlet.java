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

public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ComplaintDAO complaintDAO;
    private UserDAO userDAO;

    public void init() throws ServletException {
        complaintDAO = new ComplaintDAO();
        userDAO = new UserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User currentUser = (User) session.getAttribute("user");
        if (!"admin".equals(currentUser.getUserType())) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        try {
            // Get all complaints and pending users
            List<Complaint> complaints = complaintDAO.getAllComplaints();
            List<User> pendingUsers = userDAO.getPendingUsers();
            
            // Get system statistics
            int totalUsers = userDAO.getTotalUsers();
            int totalComplaints = complaintDAO.getTotalComplaints();
            int pendingApprovals = userDAO.getPendingApprovalsCount();
            int activeStaff = userDAO.getActiveStaffCount();
            
            // Set attributes for the JSP
            request.setAttribute("complaints", complaints);
            request.setAttribute("pendingUsers", pendingUsers);
            request.setAttribute("totalUsers", totalUsers);
            request.setAttribute("totalComplaints", totalComplaints);
            request.setAttribute("pendingApprovals", pendingApprovals);
            request.setAttribute("activeStaff", activeStaff);
            
            // Forward to the dashboard JSP
            request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error loading dashboard data: " + e.getMessage());
            request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
        }
    }
} 