package com.example.servlet;

import com.example.util.DBUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

//@WebServlet("/api/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> data = mapper.readValue(request.getInputStream(), Map.class);
        String username = data.get("username");
        String password = data.get("password");

        try (Connection conn = DBUtil.getDataSource().getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("user", rs.getString("username"));
                session.setAttribute("role", rs.getString("role"));

                System.out.println("用户登录成功，Session ID = " + session.getId());

                response.getWriter().write("{\"status\":\"success\",\"role\":\"" + rs.getString("role") + "\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"status\":\"error\",\"message\":\"用户名或密码错误\"}");
            }
        } catch (SQLException e) {
            response.setStatus(500);
            response.getWriter().write("{\"status\":\"error\",\"message\":\"服务器错误\"}");
            e.printStackTrace();
        }
    }
}

