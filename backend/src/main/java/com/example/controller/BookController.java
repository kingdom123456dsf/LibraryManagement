package com.example.controller;

import com.example.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@WebServlet("/api/books")
public class BookController extends HttpServlet {
    private final BookService bookService = new BookService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取请求参数
        String query = request.getParameter("query");
        String category = request.getParameter("category");
        int page = Integer.parseInt(Optional.ofNullable(request.getParameter("page")).orElse("1"));
        int size = Integer.parseInt(Optional.ofNullable(request.getParameter("size")).orElse("6"));

        try {
            // 调用业务层方法获取数据
            Map<String, Object> result = bookService.getBooks(query, category, page, size);

            // 返回JSON
            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().write(mapper.writeValueAsString(result));
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
            response.getWriter().write("{\"status\":\"error\",\"message\":\"数据库错误\"}");
        }
    }
}
