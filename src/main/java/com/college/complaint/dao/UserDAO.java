package com.college.complaint.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.college.complaint.model.User;
import com.college.complaint.util.DatabaseUtil;

/**
 * Data Access Object for User entity
 */
public class UserDAO {
    
    /**
     * Authenticate a user
     * @param username Username
     * @param password Password
     * @return User object if authentication successful, null otherwise
     */
    public User authenticate(String username, String password) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User user = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setUserType(rs.getString("user_type"));
                user.setRegistrationStatus(rs.getString("registration_status"));
                user.setStaffCategoryId(rs.getObject("staff_category_id", Integer.class));
                user.setCreatedAt(rs.getTimestamp("created_at"));
                System.out.println("User found: " + user.getUsername() + ", Type: " + user.getUserType() + ", Status: " + user.getRegistrationStatus());
            } else {
                System.out.println("No user found with username: " + username);
            }
        } catch (SQLException e) {
            System.err.println("Error authenticating user: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) DatabaseUtil.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        return user;
    }
    
    /**
     * Get all pending users
     * @return List of pending users
     * @throws SQLException if database error occurs
     */
    public List<User> getPendingUsers() throws SQLException {
        List<User> pendingUsers = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE registration_status = 'pending' ORDER BY created_at DESC";
        
        System.out.println("Fetching pending users...");
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            System.out.println("Query executed successfully");
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setUserType(rs.getString("user_type"));
                user.setStaffCategoryId(rs.getObject("staff_category_id", Integer.class));
                user.setRegistrationStatus(rs.getString("registration_status"));
                user.setCreatedAt(rs.getTimestamp("created_at"));
                pendingUsers.add(user);
                System.out.println("Found pending user: " + user.getUsername() + ", Type: " + user.getUserType());
            }
            System.out.println("Total pending users found: " + pendingUsers.size());
        } catch (SQLException e) {
            System.err.println("Error fetching pending users: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return pendingUsers;
    }
    
    /**
     * Update user registration status
     * @param userId User ID
     * @param status New status ("approved" or "rejected")
     * @return true if update successful, false otherwise
     * @throws SQLException if database error occurs
     */
    public boolean updateUserStatus(int userId, String status) throws SQLException {
        String sql = "UPDATE users SET registration_status = ? WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            stmt.setInt(2, userId);
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    /**
     * Register a new user
     * @param user User to register
     * @return true if registration successful, false otherwise
     */
    public boolean registerUser(User user) {
        if (!user.isValid()) {
            System.err.println("Invalid user data");
            return false;
        }
        
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;
        
        try {
            conn = DatabaseUtil.getConnection();
            
            // First check if username exists
            if (usernameExists(user.getUsername())) {
                System.out.println("Username already exists: " + user.getUsername());
                return false;
            }
            
            // Insert new user
            String sql = "INSERT INTO users (username, password, full_name, email, user_type, staff_category_id, registration_status) VALUES (?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getFullName());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getUserType());
            stmt.setObject(6, user.getStaffCategoryId());
            stmt.setString(7, "pending");
            
            int rowsAffected = stmt.executeUpdate();
            success = rowsAffected > 0;
            
            if (success) {
                System.out.println("User registered successfully: " + user.getUsername());
            } else {
                System.out.println("Failed to register user: " + user.getUsername());
            }
        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) DatabaseUtil.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        return success;
    }
    
    /**
     * Get user by ID
     * @param userId User ID
     * @return User object if found, null otherwise
     */
    public User getUserById(int userId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User user = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            String sql = "SELECT * FROM users WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setUserType(rs.getString("user_type"));
                user.setCreatedAt(rs.getTimestamp("created_at"));
            }
        } catch (SQLException e) {
            System.err.println("Error getting user by ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) DatabaseUtil.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        return user;
    }
    
    /**
     * Get all staff users
     * @return List of staff users
     */
    public List<User> getAllStaffUsers() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<User> staffUsers = new ArrayList<>();
        
        try {
            conn = DatabaseUtil.getConnection();
            String sql = "SELECT * FROM users WHERE user_type = 'staff'";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setUserType(rs.getString("user_type"));
                staffUsers.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) DatabaseUtil.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return staffUsers;
    }
    
    /**
     * Check if username exists
     * @param username Username to check
     * @return true if username exists, false otherwise
     */
    public boolean usernameExists(String username) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean exists = false;
        
        try {
            conn = DatabaseUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }
            
            System.out.println("Username check for '" + username + "': " + (exists ? "exists" : "available"));
        } catch (SQLException e) {
            System.err.println("Error checking username: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) DatabaseUtil.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        return exists;
    }

    /**
     * Get total number of users in the system
     */
    public int getTotalUsers() {
        String sql = "SELECT COUNT(*) FROM users";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Get count of pending user approvals
     */
    public int getPendingApprovalsCount() {
        String sql = "SELECT COUNT(*) FROM users WHERE registration_status = 'pending'";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Get count of active staff members
     */
    public int getActiveStaffCount() {
        String sql = "SELECT COUNT(*) FROM users WHERE user_type = 'staff' AND registration_status = 'approved'";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY id";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setUserType(rs.getString("user_type"));
                user.setRegistrationStatus(rs.getString("registration_status"));
                user.setStaffCategoryId(rs.getInt("staff_category_id"));
                users.add(user);
            }
        }
        
        return users;
    }

    public boolean deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            int rowsAffected = stmt.executeUpdate();
            
            return rowsAffected > 0;
        }
    }

    public boolean isUsernameExists(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }

    public boolean createUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, password, full_name, email, user_type, registration_status, staff_category_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getFullName());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getUserType());
            stmt.setString(6, user.getRegistrationStatus());
            stmt.setObject(7, user.getStaffCategoryId());
            
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Get staff members by category ID
     * @param categoryId Staff category ID
     * @return List of staff members in the category
     * @throws SQLException if database error occurs
     */
    public List<User> getStaffByCategory(int categoryId) throws SQLException {
        List<User> staffMembers = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE user_type = 'staff' AND staff_category_id = ? AND registration_status = 'approved'";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setUserType(rs.getString("user_type"));
                user.setStaffCategoryId(rs.getObject("staff_category_id", Integer.class));
                user.setRegistrationStatus(rs.getString("registration_status"));
                user.setCreatedAt(rs.getTimestamp("created_at"));
                staffMembers.add(user);
            }
        }
        return staffMembers;
    }
} 