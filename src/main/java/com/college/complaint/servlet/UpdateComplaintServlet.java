package com.college.complaint.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.college.complaint.dao.ComplaintDAO;
import com.college.complaint.model.Complaint;
import com.college.complaint.model.User;

public class UpdateComplaintServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ComplaintDAO complaintDAO;

    public void init() throws ServletException {
        complaintDAO = new ComplaintDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User currentUser = (User) session.getAttribute("user");
        if (!"staff".equals(currentUser.getUserType())) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        String complaintIdStr = request.getParameter("complaintId");
        String status = request.getParameter("status");
        String resolution = request.getParameter("resolution");

        if (complaintIdStr == null || complaintIdStr.trim().isEmpty()) {
            session.setAttribute("errorMessage", "Complaint ID is required");
            response.sendRedirect(request.getContextPath() + "/staff/dashboard");
            return;
        }

        try {
            int complaintId = Integer.parseInt(complaintIdStr);
            Complaint complaint = complaintDAO.getComplaintById(complaintId);

            // Debug logging
            System.out.println("Debug - Current User Staff Category ID: " + currentUser.getStaffCategoryId());
            System.out.println("Debug - Complaint Staff Category ID: " + complaint.getAssignedStaffCategoryId());
            System.out.println("Debug - Complaint ID: " + complaintId);
            System.out.println("Debug - Complaint Status: " + complaint.getStatus());

            if (complaint != null && complaint.getAssignedStaffCategoryId() != null) {
                if (complaint.getAssignedStaffCategoryId().intValue() == currentUser.getStaffCategoryId().intValue()) {
                    complaint.setStatus(status);
                    complaint.setResolution(resolution);
                    
                    boolean success = complaintDAO.updateComplaint(complaint);
                    if (success) {
                        session.setAttribute("successMessage", "Complaint status updated successfully");
                    } else {
                        session.setAttribute("errorMessage", "Failed to update complaint status");
                    }
                } else {
                    session.setAttribute("errorMessage", "You don't have permission to update this complaint. Your category ID: " + 
                        currentUser.getStaffCategoryId() + ", Complaint category ID: " + complaint.getAssignedStaffCategoryId());
                }
            } else {
                session.setAttribute("errorMessage", "Complaint not found or not assigned to any staff category");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("errorMessage", "Invalid complaint ID");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Error updating complaint: " + e.getMessage());
        }
        
        response.sendRedirect(request.getContextPath() + "/staff/dashboard");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User currentUser = (User) session.getAttribute("user");
        if (!"staff".equals(currentUser.getUserType())) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        String complaintIdStr = request.getParameter("id");
        if (complaintIdStr == null || complaintIdStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/staff/dashboard");
            return;
        }

        try {
            int complaintId = Integer.parseInt(complaintIdStr);
            Complaint complaint = complaintDAO.getComplaintById(complaintId);

            // Debug logging
            System.out.println("Debug - Current User Staff Category ID: " + currentUser.getStaffCategoryId());
            System.out.println("Debug - Complaint Staff Category ID: " + complaint.getAssignedStaffCategoryId());
            System.out.println("Debug - Complaint ID: " + complaintId);
            System.out.println("Debug - Complaint Status: " + complaint.getStatus());

            if (complaint != null && complaint.getAssignedStaffCategoryId() != null) {
                if (complaint.getAssignedStaffCategoryId().intValue() == currentUser.getStaffCategoryId().intValue()) {
                    request.setAttribute("complaint", complaint);
                    request.getRequestDispatcher("/staff/update-complaint.jsp").forward(request, response);
                } else {
                    session.setAttribute("errorMessage", "You don't have permission to update this complaint. Your category ID: " + 
                        currentUser.getStaffCategoryId() + ", Complaint category ID: " + complaint.getAssignedStaffCategoryId());
                    response.sendRedirect(request.getContextPath() + "/staff/dashboard");
                }
            } else {
                session.setAttribute("errorMessage", "Complaint not found or not assigned to any staff category");
                response.sendRedirect(request.getContextPath() + "/staff/dashboard");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/staff/dashboard");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error loading complaint: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/staff/dashboard");
        }
    }
} 