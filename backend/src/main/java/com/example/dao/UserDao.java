package com.example.dao;

import com.example.entity.User;
import com.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户数据访问层，负责所有与数据库中 users 表交互的操作
 */
public class UserDao {

    /**
     * 根据用户名和密码查询用户，验证登录
     */
    public User findByUsernameAndPassword(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DBUtil.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"));
            }
            return null;
        }
    }

    /**
     * 根据用户名查询用户是否存在
     */
    public User findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DBUtil.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"));
            }
            return null;
        }
    }

    /**
     * 新增用户
     */
    public int insertUser(String username, String password) throws SQLException {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection conn = DBUtil.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            return stmt.executeUpdate();
        }
    }

    /**
     * 更新用户信息（用户名和密码）
     */
    public int updateUser(String oldUsername, String newUsername, String newPassword) throws SQLException {
        StringBuilder sql = new StringBuilder("UPDATE users SET username = ?");
        if (newPassword != null && !newPassword.isBlank()) {
            sql.append(", password = ?");
        }
        sql.append(" WHERE username = ?");

        try (Connection conn = DBUtil.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            stmt.setString(1, newUsername);
            if (newPassword != null && !newPassword.isBlank()) {
                stmt.setString(2, newPassword);
                stmt.setString(3, oldUsername);
            } else {
                stmt.setString(2, oldUsername);
            }
            return stmt.executeUpdate();
        }
    }

    /**
     * 查询所有用户
     */
    public List<Map<String, Object>> queryAllUsers() {
        List<Map<String, Object>> result = new ArrayList<>();
        String sql = "SELECT id, username, role FROM users";

        try (Connection conn = DBUtil.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> user = new HashMap<>();
                user.put("id", rs.getInt("id"));
                user.put("username", rs.getString("username"));
                user.put("role", rs.getString("role"));
                result.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
