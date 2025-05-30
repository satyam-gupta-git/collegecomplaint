package com.college.complaint.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.college.complaint.dao.UserDAO;
import com.college.complaint.model.User;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            redirectBasedOnUserType(response, user.getUserType());
            return;
        }
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Username and password are required");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        try {
            User user = userDAO.authenticate(username, password);
            if (user != null) {
                if ("pending".equals(user.getRegistrationStatus())) {
                    request.setAttribute("errorMessage", "Your account is pending approval. Please wait for admin approval.");
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                    return;
                } else if ("rejected".equals(user.getRegistrationStatus())) {
                    request.setAttribute("errorMessage", "Your account has been rejected. Please contact the administrator.");
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                    return;
                }

                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                redirectBasedOnUserType(response, user.getUserType());
            } else {
                request.setAttribute("errorMessage", "Invalid username or password");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred during login. Please try again.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    private void redirectBasedOnUserType(HttpServletResponse response, String userType) throws IOException {
        switch (userType.toLowerCase()) {
            case "admin":
                response.sendRedirect("admin/dashboard");
                break;
            case "staff":
                response.sendRedirect("staff/dashboard");
                break;
            case "student":
                response.sendRedirect("student/dashboard");
                break;
            default:
                response.sendRedirect("login");
        }
    }
} 