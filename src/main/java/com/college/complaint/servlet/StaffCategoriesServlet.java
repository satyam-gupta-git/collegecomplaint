package com.college.complaint.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.college.complaint.dao.StaffCategoryDAO;
import com.college.complaint.model.StaffCategory;
import com.college.complaint.model.User;

public class StaffCategoriesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StaffCategoryDAO staffCategoryDAO;

    public void init() throws ServletException {
        staffCategoryDAO = new StaffCategoryDAO();
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
            List<StaffCategory> categories = staffCategoryDAO.getAllCategories();
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/admin/staff-categories.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error loading staff categories: " + e.getMessage());
            request.getRequestDispatcher("/admin/staff-categories.jsp").forward(request, response);
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
        String categoryName = request.getParameter("categoryName");
        String categoryIdStr = request.getParameter("categoryId");

        if ("add".equals(action)) {
            if (categoryName == null || categoryName.trim().isEmpty()) {
                request.setAttribute("errorMessage", "Category name is required");
                doGet(request, response);
                return;
            }

            try {
                StaffCategory category = new StaffCategory();
                category.setCategoryName(categoryName);

                boolean success = staffCategoryDAO.createCategory(category);
                if (success) {
                    request.setAttribute("successMessage", "Category added successfully");
                } else {
                    request.setAttribute("errorMessage", "Failed to add category");
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Error adding category: " + e.getMessage());
            }
        } else if ("delete".equals(action)) {
            if (categoryIdStr == null || categoryIdStr.trim().isEmpty()) {
                request.setAttribute("errorMessage", "Category ID is required");
                doGet(request, response);
                return;
            }

            try {
                int categoryId = Integer.parseInt(categoryIdStr);
                boolean success = staffCategoryDAO.deleteCategory(categoryId);
                if (success) {
                    request.setAttribute("successMessage", "Category deleted successfully");
                } else {
                    request.setAttribute("errorMessage", "Failed to delete category");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Invalid category ID");
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Error deleting category: " + e.getMessage());
            }
        } else {
            request.setAttribute("errorMessage", "Invalid action");
        }

        doGet(request, response);
    }
} 