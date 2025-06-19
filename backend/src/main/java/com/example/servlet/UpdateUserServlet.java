package com.example.servlet;

import com.example.util.DBUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;
import java.util.Map;

//@WebServlet("/api/update-user")
public class UpdateUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        Object sessionUser = (session != null) ? session.getAttribute("user") : null;

        var mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        var data = mapper.readValue(request.getInputStream(), Map.class);
        String oldUsername = data.get("oldUsername").toString();
        String newUsername = data.get("newUsername").toString();
        String newPassword = data.getOrDefault("newPassword", "").toString();

        try (Connection conn = DBUtil.getDataSource().getConnection()) {
            StringBuilder sql = new StringBuilder("UPDATE users SET username = ?");
            if (!newPassword.isBlank()) {
                sql.append(", password = ?");
            }
            sql.append(" WHERE username = ?");

            PreparedStatement stmt = conn.prepareStatement(sql.toString());
            stmt.setString(1, newUsername);
            if (!newPassword.isBlank()) {
                stmt.setString(2, newPassword);
                stmt.setString(3, oldUsername);
            } else {
                stmt.setString(2, oldUsername);
            }
            int updated = stmt.executeUpdate();

            if (updated > 0) {
                // 同步更新 session
                session.setAttribute("user", newUsername);
                response.getWriter().write("{\"message\":\"信息已更新\"}");
            } else {
                response.setStatus(400);
                response.getWriter().write("{\"message\":\"更新失败，用户不存在\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
            response.getWriter().write("{\"message\":\"服务器错误\"}");
        }
    }
}


