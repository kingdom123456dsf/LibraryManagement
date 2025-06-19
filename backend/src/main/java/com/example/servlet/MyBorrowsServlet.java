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

//@WebServlet("/api/my-borrows")
public class MyBorrowsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        Object username = (session != null) ? session.getAttribute("user") : null;

        try (Connection conn = DBUtil.getDataSource().getConnection()) {
            // 查找用户ID
            PreparedStatement userStmt = conn.prepareStatement("SELECT id FROM users WHERE username = ?");
            userStmt.setString(1, username.toString());
            ResultSet userRs = userStmt.executeQuery();
            if (!userRs.next()) return;

            int userId = userRs.getInt("id");

            // 获取借阅记录
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT b.title, br.borrow_time, br.return_time, br.returned " +
                            "FROM borrow_records br JOIN books b ON br.book_id = b.id " +
                            "WHERE br.user_id = ? ORDER BY br.borrow_time DESC"
            );
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            List<Map<String, Object>> records = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("title", rs.getString("title"));
                row.put("borrowTime", rs.getTimestamp("borrow_time"));
                row.put("returnTime", rs.getTimestamp("return_time"));
                row.put("returned", rs.getBoolean("returned"));
                records.add(row);
            }

            response.getWriter().write(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(records));
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
            response.getWriter().write("{\"message\":\"服务器错误\"}");
        }
    }
}
