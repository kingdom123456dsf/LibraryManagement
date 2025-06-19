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

//@WebServlet("/api/admin/borrow-records")
public class AdminBorrowRecordsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("userId");
        if (userId == null) {
            response.setStatus(400);
            response.getWriter().write("{\"message\":\"缺少 userId 参数\"}");
            return;
        }
        //获取某用户借阅记录
        try (Connection conn = DBUtil.getDataSource().getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT br.id, b.title, br.borrow_time, br.return_time, br.returned " +
                            "FROM borrow_records br JOIN books b ON br.book_id = b.id WHERE br.user_id = ?"
            );
            stmt.setInt(1, Integer.parseInt(userId));
            ResultSet rs = stmt.executeQuery();

            List<Map<String, Object>> records = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> record = new HashMap<>();
                record.put("id", rs.getInt("id"));
                record.put("title", rs.getString("title"));
                record.put("borrowTime", rs.getTimestamp("borrow_time"));
                record.put("returnTime", rs.getTimestamp("return_time"));
                record.put("returned", rs.getBoolean("returned"));
                records.add(record);
            }

            response.getWriter().write(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(records));
        } catch (Exception e) {
            response.setStatus(500);
            response.getWriter().write("{\"message\":\"查询失败\"}");
        }
    }
}

