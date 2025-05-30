package com.college.complaint.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.college.complaint.dao.UserDAO;
import com.college.complaint.model.User;

public class UserApprovalServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public void init() throws ServletException {
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
            List<User> pendingUsers = userDAO.getPendingUsers();
            request.setAttribute("pendingUsers", pendingUsers);
            request.getRequestDispatcher("/admin/user-approval.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error loading pending users: " + e.getMessage());
            request.getRequestDispatcher("/admin/user-approval.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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

        String action = request.getParameter("action");
        String userIdStr = request.getParameter("userId");

        if (userIdStr == null || userIdStr.trim().isEmpty()) {
            request.setAttribute("errorMessage", "User ID is required");
            doGet(request, response);
            return;
        }

        try {
            int userId = Integer.parseInt(userIdStr);
            boolean success = false;

            if ("approve".equals(action)) {
                success = userDAO.updateUserStatus(userId, "approved");
                if (success) {
                    request.setAttribute("successMessage", "User approved successfully");
                } else {
                    request.setAttribute("errorMessage", "Failed to approve user");
                }
            } else if ("reject".equals(action)) {
                success = userDAO.updateUserStatus(userId, "rejected");
                if (success) {
                    request.setAttribute("successMessage", "User rejected successfully");
                } else {
                    request.setAttribute("errorMessage", "Failed to reject user");
                }
            } else {
                request.setAttribute("errorMessage", "Invalid action");
            }

            doGet(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid user ID");
            doGet(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error processing request: " + e.getMessage());
            doGet(request, response);
        }
    }
} 