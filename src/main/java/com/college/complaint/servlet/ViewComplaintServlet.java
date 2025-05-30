package com.college.complaint.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.college.complaint.dao.ComplaintDAO;
import com.college.complaint.dao.UserDAO;
import com.college.complaint.model.Complaint;
import com.college.complaint.model.User;

public class ViewComplaintServlet extends HttpServlet {
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

        String complaintIdStr = request.getParameter("id");
        if (complaintIdStr == null || complaintIdStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        try {
            int complaintId = Integer.parseInt(complaintIdStr);
            Complaint complaint = complaintDAO.getComplaintById(complaintId);
            
            if (complaint != null) {
                User currentUser = (User) session.getAttribute("user");
                String userType = currentUser.getUserType();
                
                // Check if user has permission to view this complaint
                if ("admin".equals(userType) || 
                    ("staff".equals(userType) && complaint.getAssignedStaffCategoryId() != null && 
                     complaint.getAssignedStaffCategoryId().equals(currentUser.getStaffCategoryId())) ||
                    ("student".equals(userType) && complaint.getUserId() == currentUser.getId())) {
                    
                    // Get the submitter's information
                    User submitter = userDAO.getUserById(complaint.getUserId());
                    
                    request.setAttribute("complaint", complaint);
                    request.setAttribute("submitter", submitter);
                    request.getRequestDispatcher("/view-complaint.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/");
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error loading complaint: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
} 