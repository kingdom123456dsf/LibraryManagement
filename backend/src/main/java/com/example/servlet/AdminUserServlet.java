package com.example.servlet;

import com.example.util.DBUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@WebServlet("/api/admin/users")
public class AdminUserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (Connection conn = DBUtil.getDataSource().getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT id, username, role FROM users");

            List<Map<String, Object>> users = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> user = new HashMap<>();
                user.put("id", rs.getInt("id"));
                user.put("username", rs.getString("username"));
                user.put("role", rs.getString("role"));
                users.add(user);
            }

            response.getWriter().write(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(users));
        } catch (Exception e) {
            response.setStatus(500);
            response.getWriter().write("{\"message\":\"数据库错误\"}");
        }
    }
}

