package com.example.servlet;

import com.example.util.DBUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;
import java.util.Map;

//@WebServlet("/api/admin/return")
public class AdminReturnServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        var data = mapper.readValue(request.getInputStream(), Map.class);
        int borrowId = (int) data.get("borrowId");

        try (Connection conn = DBUtil.getDataSource().getConnection()) {
            // 设置记录为已归还
            PreparedStatement update = conn.prepareStatement(
                    "UPDATE borrow_records SET returned = true, return_time = NOW() WHERE id = ?"
            );
            update.setInt(1, borrowId);
            update.executeUpdate();

            // 同时将对应书籍设为可借
            PreparedStatement getBook = conn.prepareStatement("SELECT book_id FROM borrow_records WHERE id = ?");
            getBook.setInt(1, borrowId);
            ResultSet rs = getBook.executeQuery();
            if (rs.next()) {
                int bookId = rs.getInt("book_id");
                PreparedStatement updateBook = conn.prepareStatement("UPDATE books SET available = true WHERE id = ?");
                updateBook.setInt(1, bookId);
                updateBook.executeUpdate();
            }

            response.getWriter().write("{\"message\":\"还书成功\"}");
        } catch (Exception e) {
            response.setStatus(500);
            response.getWriter().write("{\"message\":\"还书失败\"}");
        }
    }
}
