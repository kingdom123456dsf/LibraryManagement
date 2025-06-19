package com.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.util.DBUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//@WebServlet("/api/admin/books")
public class AdminBookServlet extends HttpServlet {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取请求参数（前端传入）
        String category = request.getParameter("category"); // 分类过滤
        int page = Integer.parseInt(Optional.ofNullable(request.getParameter("page")).orElse("1")); // 当前页
        int size = Integer.parseInt(Optional.ofNullable(request.getParameter("size")).orElse("6")); // 每页数量

        try (Connection conn = DBUtil.getDataSource().getConnection()) {
            // 使用 SQL_CALC_FOUND_ROWS 可配合 FOUND_ROWS() 查询总记录数
            StringBuilder sql = new StringBuilder("SELECT SQL_CALC_FOUND_ROWS * FROM books WHERE 1=1");
            List<Object> params = new ArrayList<>();

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
                book.put("available", rs.getBoolean("available"));
                book.put("image", rs.getString("image"));
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
            response.getWriter().write(mapper.writeValueAsString(result));

        } catch (SQLException e) {
            // 错误处理
            response.setStatus(500);
            response.getWriter().write("{\"status\":\"error\",\"message\":\"数据库错误\"}");
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 添加书籍
        var book = mapper.readValue(request.getInputStream(), Map.class);
        try (Connection conn = DBUtil.getDataSource().getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO books (title, author, isbn, description, available, image, category) VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            stmt.setString(1, book.get("title").toString());
            stmt.setString(2, book.get("author").toString());
            stmt.setString(3, book.get("isbn").toString());
            stmt.setString(4, book.get("description").toString());
            stmt.setBoolean(5, Boolean.parseBoolean(book.get("available").toString()));
            stmt.setString(6, book.get("image").toString());
            stmt.setString(7, book.get("category").toString());
            stmt.executeUpdate();
            response.getWriter().write("{\"message\":\"添加成功\"}");
        } catch (Exception e) {
            response.setStatus(500);
            response.getWriter().write("{\"message\":\"添加失败\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 修改书籍
        var book = mapper.readValue(request.getInputStream(), Map.class);
        try (Connection conn = DBUtil.getDataSource().getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE books SET title=?, author=?, isbn=?, description=?, available=?, image=?, category=? WHERE id=?"
            );
            stmt.setString(1, book.get("title").toString());
            stmt.setString(2, book.get("author").toString());
            stmt.setString(3, book.get("isbn").toString());
            stmt.setString(4, book.get("description").toString());
            stmt.setBoolean(5, Boolean.parseBoolean(book.get("available").toString()));
            stmt.setString(6, book.get("image").toString());
            stmt.setString(7, book.get("category").toString());
            stmt.setInt(8, Integer.parseInt(book.get("id").toString()));
            stmt.executeUpdate();
            response.getWriter().write("{\"message\":\"修改成功\"}");
        } catch (Exception e) {
            response.setStatus(500);
            response.getWriter().write("{\"message\":\"修改失败\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 删除书籍
        int id = Integer.parseInt(request.getParameter("id"));
        try (Connection conn = DBUtil.getDataSource().getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM books WHERE id=?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
            response.getWriter().write("{\"message\":\"删除成功\"}");
        } catch (Exception e) {
            response.setStatus(500);
            response.getWriter().write("{\"message\":\"删除失败\"}");
        }
    }
}