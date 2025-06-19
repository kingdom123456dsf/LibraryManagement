package com.example.servlet;

import com.example.util.DBUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;
import java.util.Map;

//@WebServlet("/api/borrow")
public class BorrowServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 检查登录状态
        HttpSession session = request.getSession(false);
        Object username = (session != null) ? session.getAttribute("user") : null;

        // 解析前端请求体中的 bookId
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> data = mapper.readValue(request.getInputStream(), Map.class);
        int bookId = (int) data.get("bookId");

        try (Connection conn = DBUtil.getDataSource().getConnection()) {
            // 获取用户 ID
            PreparedStatement findUser = conn.prepareStatement("SELECT id FROM users WHERE username = ?");
            findUser.setString(1, username.toString());
            ResultSet userRs = findUser.executeQuery();

            if (!userRs.next()) {
                response.setStatus(400);
                response.getWriter().write("{\"status\":\"error\",\"message\":\"用户不存在\"}");
                return;
            }

            int userId = userRs.getInt("id");

            // 检查是否已借该书且未归还
            PreparedStatement check = conn.prepareStatement(
                    "SELECT id FROM borrow_records WHERE user_id = ? AND book_id = ? AND returned = false"
            );
            check.setInt(1, userId);
            check.setInt(2, bookId);
            ResultSet checkRs = check.executeQuery();

            if (checkRs.next()) {
                response.setStatus(400);
                response.getWriter().write("{\"status\":\"error\",\"message\":\"不能重复借阅\"}");
                return;
            }

            // 检查书籍是否可借
            PreparedStatement bookStmt = conn.prepareStatement("SELECT available FROM books WHERE id = ?");
            bookStmt.setInt(1, bookId);
            ResultSet bookRs = bookStmt.executeQuery();
            if (!bookRs.next() || !bookRs.getBoolean("available")) {
                response.setStatus(400);
                response.getWriter().write("{\"status\":\"error\",\"message\":\"该书不可借\"}");
                return;
            }

            // 插入借阅记录
            PreparedStatement insert = conn.prepareStatement(
                    "INSERT INTO borrow_records (user_id, book_id) VALUES (?, ?)"
            );
            insert.setInt(1, userId);
            insert.setInt(2, bookId);
            insert.executeUpdate();

            // 修改书籍状态为不可借
            PreparedStatement update = conn.prepareStatement("UPDATE books SET available = false WHERE id = ?");
            update.setInt(1, bookId);
            update.executeUpdate();

            // 返回成功
            response.getWriter().write("{\"status\":\"success\",\"message\":\"借阅成功\"}");

        } catch (SQLException e) {
            response.setStatus(500);
            response.getWriter().write("{\"status\":\"error\",\"message\":\"服务器错误\"}");
            e.printStackTrace();
        }
    }
}
