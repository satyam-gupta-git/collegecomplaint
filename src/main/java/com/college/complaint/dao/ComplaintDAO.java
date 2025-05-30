package com.college.complaint.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.college.complaint.model.Complaint;
import com.college.complaint.util.DatabaseUtil;

/**
 * Data Access Object for Complaint entity
 */
public class ComplaintDAO {
    
    /**
     * Add a new complaint
     * @param complaint Complaint to add
     * @return true if successful, false otherwise
     */
    public boolean addComplaint(Complaint complaint) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;
        
        try {
            conn = DatabaseUtil.getConnection();
            String sql = "INSERT INTO complaints (user_id, title, description, category, location, image_path, assigned_staff_category_id, submission_date, status) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, complaint.getUserId());
            stmt.setString(2, complaint.getTitle());
            stmt.setString(3, complaint.getDescription());
            stmt.setString(4, complaint.getCategory());
            stmt.setString(5, complaint.getLocation());
            stmt.setString(6, complaint.getImagePath());
            stmt.setInt(7, complaint.getAssignedStaffCategoryId());
            stmt.setTimestamp(8, new Timestamp(complaint.getSubmissionDate().getTime()));
            stmt.setString(9, complaint.getStatus());
            
            int rowsAffected = stmt.executeUpdate();
            success = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) DatabaseUtil.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return success;
    }
    
    /**
     * Get all complaints
     * @return List of all complaints
     */
    public List<Complaint> getAllComplaints() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Complaint> complaints = new ArrayList<>();
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM complaints ORDER BY submission_date DESC";
            rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Complaint complaint = extractComplaintFromResultSet(rs);
                complaints.add(complaint);
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
        
        return complaints;
    }
    
    /**
     * Get complaints by user ID
     * @param userId User ID
     * @return List of complaints for the user
     */
    public List<Complaint> getComplaintsByUserId(int userId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Complaint> complaints = new ArrayList<>();
        
        try {
            conn = DatabaseUtil.getConnection();
            String sql = "SELECT * FROM complaints WHERE user_id = ? ORDER BY submission_date DESC";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Complaint complaint = extractComplaintFromResultSet(rs);
                complaints.add(complaint);
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
        
        return complaints;
    }
    
    /**
     * Get complaint by ID
     * @param complaintId Complaint ID
     * @return Complaint object if found, null otherwise
     */
    public Complaint getComplaintById(int complaintId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Complaint complaint = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            String sql = "SELECT * FROM complaints WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, complaintId);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                complaint = extractComplaintFromResultSet(rs);
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
        
        return complaint;
    }
    
    /**
     * Update complaint status
     * @param complaintId Complaint ID
     * @param status New status
     * @param resolution Resolution text
     * @return true if successful, false otherwise
     */
    public boolean updateComplaintStatus(int complaintId, String status, String resolution) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;
        
        try {
            conn = DatabaseUtil.getConnection();
            String sql = "UPDATE complaints SET status = ?, resolution = ?, resolution_date = ? WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, status);
            stmt.setString(2, resolution);
            stmt.setTimestamp(3, status.equals("resolved") ? new Timestamp(System.currentTimeMillis()) : null);
            stmt.setInt(4, complaintId);
            
            int rowsAffected = stmt.executeUpdate();
            success = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) DatabaseUtil.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return success;
    }
    
    /**
     * Extract complaint from result set
     * @param rs Result set
     * @return Complaint object
     * @throws SQLException if a database access error occurs
     */
    private Complaint extractComplaintFromResultSet(ResultSet rs) throws SQLException {
        Complaint complaint = new Complaint();
        complaint.setId(rs.getInt("id"));
        complaint.setUserId(rs.getInt("user_id"));
        complaint.setTitle(rs.getString("title"));
        complaint.setDescription(rs.getString("description"));
        complaint.setCategory(rs.getString("category"));
        complaint.setLocation(rs.getString("location"));
        complaint.setImagePath(rs.getString("image_path"));
        complaint.setSubmissionDate(rs.getTimestamp("submission_date"));
        complaint.setStatus(rs.getString("status"));
        complaint.setResolution(rs.getString("resolution"));
        
        Timestamp resolutionDate = rs.getTimestamp("resolution_date");
        if (resolutionDate != null) {
            complaint.setResolutionDate(resolutionDate);
        }
        
        return complaint;
    }
    
    /**
     * Get total number of complaints in the system
     */
    public int getTotalComplaints() {
        String sql = "SELECT COUNT(*) FROM complaints";
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

    public List<Complaint> getComplaintsByStudentId(int studentId) throws SQLException {
        List<Complaint> complaints = new ArrayList<>();
        String sql = "SELECT * FROM complaints WHERE user_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                complaints.add(extractComplaintFromResultSet(rs));
            }
        }
        return complaints;
    }

    public List<Complaint> getComplaintsByStaffId(int staffId) throws SQLException {
        List<Complaint> complaints = new ArrayList<>();
        String sql = "SELECT * FROM complaints WHERE assigned_staff_category_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, staffId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                complaints.add(extractComplaintFromResultSet(rs));
            }
        }
        return complaints;
    }

    public boolean updateComplaint(Complaint complaint) throws SQLException {
        String sql = "UPDATE complaints SET status = ?, resolution = ?, resolution_date = ? WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, complaint.getStatus());
            stmt.setString(2, complaint.getResolution());
            stmt.setTimestamp(3, complaint.getStatus().equals("resolved") ? new java.sql.Timestamp(System.currentTimeMillis()) : null);
            stmt.setInt(4, complaint.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Assigns a staff member to a complaint and updates its status to 'assigned'
     * @param complaintId The ID of the complaint
     * @param staffCategoryId The ID of the staff member to assign
     * @return true if the assignment was successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean assignStaff(int complaintId, int staffCategoryId) throws SQLException {
        String sql = "UPDATE complaints SET assigned_staff_category_id = ?, status = 'assigned' WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, staffCategoryId);
            stmt.setInt(2, complaintId);
            return stmt.executeUpdate() > 0;
        }
    }
} 