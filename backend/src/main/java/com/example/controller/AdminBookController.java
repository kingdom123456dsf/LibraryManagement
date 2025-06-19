package com.example.controller;

import com.example.entity.Book;
import com.example.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 * 管理员图书控制器，处理增删改查请求
 */
@WebServlet("/api/admin/books")
public class AdminBookController extends HttpServlet {
    private final ObjectMapper mapper = new ObjectMapper();
    private final BookService bookService = new BookService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String category = request.getParameter("category");
        String query = request.getParameter("query");
        int page = Integer.parseInt(Optional.ofNullable(request.getParameter("page")).orElse("1")); // 当前页
        int size = Integer.parseInt(Optional.ofNullable(request.getParameter("size")).orElse("6")); // 每页数量

        try {
            Map<String, Object> result = bookService.getBooks(query, category, page, size);
//            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(mapper.writeValueAsString(result));
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
            response.getWriter().write("{\"message\":\"查询失败\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Book book = mapper.readValue(request.getInputStream(), Book.class);
            bookService.addBook(book);
            response.getWriter().write("{\"message\":\"添加成功\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
            response.getWriter().write("{\"message\":\"添加失败\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Book book = mapper.readValue(request.getInputStream(), Book.class);
            bookService.updateBook(book);
            response.getWriter().write("{\"message\":\"修改成功\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
            response.getWriter().write("{\"message\":\"修改失败\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            response.setStatus(400);
            response.getWriter().write("{\"message\":\"缺少id参数\"}");
            return;
        }
        try {
            int id = Integer.parseInt(idStr);
            bookService.deleteBook(id);
            response.getWriter().write("{\"message\":\"删除成功\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
            response.getWriter().write("{\"message\":\"删除失败\"}");
        }
    }
}
