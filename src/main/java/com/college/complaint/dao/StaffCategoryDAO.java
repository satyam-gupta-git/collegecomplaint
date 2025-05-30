package com.college.complaint.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.college.complaint.model.StaffCategory;
import com.college.complaint.util.DatabaseUtil;

public class StaffCategoryDAO {
    
    public List<StaffCategory> getAllCategories() throws SQLException {
        List<StaffCategory> categories = new ArrayList<>();
        String sql = "SELECT * FROM staff_categories ORDER BY category_name";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                StaffCategory category = new StaffCategory(
                    rs.getInt("id"),
                    rs.getString("category_name"),
                    rs.getString("description"),
                    rs.getString("created_at")
                );
                categories.add(category);
            }
        }
        return categories;
    }
    
    public StaffCategory getCategoryById(int id) throws SQLException {
        String sql = "SELECT * FROM staff_categories WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new StaffCategory(
                    rs.getInt("id"),
                    rs.getString("category_name"),
                    rs.getString("description"),
                    rs.getString("created_at")
                );
            }
        }
        return null;
    }
    
    public boolean createCategory(StaffCategory category) throws SQLException {
        String sql = "INSERT INTO staff_categories (category_name, description) VALUES (?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, category.getCategoryName());
            stmt.setString(2, category.getDescription());
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean updateCategory(StaffCategory category) throws SQLException {
        String sql = "UPDATE staff_categories SET category_name = ?, description = ? WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, category.getCategoryName());
            stmt.setString(2, category.getDescription());
            stmt.setInt(3, category.getId());
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean deleteCategory(int id) throws SQLException {
        String sql = "DELETE FROM staff_categories WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
} 