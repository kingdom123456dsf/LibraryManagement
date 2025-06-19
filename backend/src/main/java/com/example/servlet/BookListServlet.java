package com.example.servlet;

import com.example.util.DBUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

//@WebServlet("/api/books")
public class BookListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取请求参数（前端传入）
        String query = request.getParameter("query");         // 书名模糊查询
        String category = request.getParameter("category");   // 分类过滤
        int page = Integer.parseInt(Optional.ofNullable(request.getParameter("page")).orElse("1")); // 当前页
        int size = Integer.parseInt(Optional.ofNullable(request.getParameter("size")).orElse("6")); // 每页数量

        try (Connection conn = DBUtil.getDataSource().getConnection()) {
            // 使用 SQL_CALC_FOUND_ROWS 可配合 FOUND_ROWS() 查询总记录数
            StringBuilder sql = new StringBuilder("SELECT SQL_CALC_FOUND_ROWS * FROM books WHERE 1=1");
            List<Object> params = new ArrayList<>();

            // 模糊查询条件拼接
            if (query != null && !query.isEmpty()) {
                sql.append(" AND title LIKE ?");
                params.add("%" + query + "%");
            }

            // 分类筛选条件拼接
            if (category != null && !category.isEmpty()) {
                sql.append(" AND category = ?");
                params.add(category);
            }

            // 添加分页参数：LIMIT offset, size
            sql.append(" LIMIT ?, ?");
            params.add((page - 1) * size); // offset
            params.add(size);              // page size

            // 预编译执行 SQL
            PreparedStatement stmt = conn.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            // 执行查询
            ResultSet rs = stmt.executeQuery();
            List<Map<String, Object>> books = new ArrayList<>();

            // 将每条记录封装为 Map 添加进列表
            while (rs.next()) {
                Map<String, Object> book = new HashMap<>();
                book.put("id", rs.getInt("id"));
                book.put("title", rs.getString("title"));
                book.put("author", rs.getString("author"));
                book.put("isbn", rs.getString("isbn"));
                book.put("description", rs.getString("description"));
                book.put("image", rs.getString("image"));
                book.put("available", rs.getBoolean("available"));
                book.put("category", rs.getString("category"));
                books.add(book);
            }

            // 再次查询：总记录数（忽略分页）
            ResultSet countRs = conn.prepareStatement("SELECT FOUND_ROWS()").executeQuery();
            int total = 0;
            if (countRs.next()) {
                total = countRs.getInt(1);
            }

            // 构造响应 JSON
            Map<String, Object> result = new HashMap<>();
            result.put("data", books);  // 当前页的图书列表
            result.put("total", total); // 所有符合条件的图书总数

            // 写入响应
            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().write(mapper.writeValueAsString(result));

        } catch (SQLException e) {
            // 错误处理
            response.setStatus(500);
            response.getWriter().write("{\"status\":\"error\",\"message\":\"数据库错误\"}");
            e.printStackTrace();
        }
    }
}


