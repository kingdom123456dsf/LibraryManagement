package com.example.servlet;

import com.example.util.DBUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

//@WebServlet("/api/register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> data = mapper.readValue(request.getInputStream(), Map.class);
        String username = data.get("username");
        String password = data.get("password");

        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            response.getWriter().write("{\"status\":\"error\",\"message\":\"用户名或密码不能为空\"}");
            return;
        }

        try (Connection conn = DBUtil.getDataSource().getConnection()) {
            String check = "SELECT id FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(check);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                response.getWriter().write("{\"status\":\"error\",\"message\":\"用户名已存在\"}");
                return;
            }

            String insert = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement ins = conn.prepareStatement(insert);
            ins.setString(1, username);
            ins.setString(2, password);
            ins.executeUpdate();

            response.getWriter().write("{\"status\":\"success\",\"message\":\"注册成功\"}");

        } catch (SQLException e) {
            response.setStatus(500);
            response.getWriter().write("{\"status\":\"error\",\"message\":\"数据库错误\"}");
            e.printStackTrace();
        }
    }
}

