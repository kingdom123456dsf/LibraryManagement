package com.example.servlet;

import com.example.util.DBUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

//图书柱形图
//@WebServlet("/api/admin/stat/category-count")
public class AdminStatServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        try (Connection conn = DBUtil.getDataSource().getConnection()) {
            String sql = "SELECT category, COUNT(*) as count FROM books GROUP BY category";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            List<Map<String, Object>> result = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("category", rs.getString("category"));
                item.put("count", rs.getInt("count"));
                result.add(item);
            }

            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().write(mapper.writeValueAsString(result));
        } catch (SQLException e) {
            response.setStatus(500);
            response.getWriter().write("{\"message\":\"数据库错误\"}");
        }
    }
}

