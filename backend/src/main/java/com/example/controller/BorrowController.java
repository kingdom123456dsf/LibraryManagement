package com.example.controller;

import com.example.service.BorrowService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Map;

@WebServlet("/api/borrow")
public class BorrowController extends HttpServlet {

    private final BorrowService borrowService = new BorrowService();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        Object usernameObj = (session != null) ? session.getAttribute("user") : null;

        // 解析前端 JSON 请求体
        Map<String, Object> data = mapper.readValue(request.getInputStream(), Map.class);
        int bookId = (int) data.get("bookId");
        String username = usernameObj.toString();

        // 调用业务逻辑
        try {
            borrowService.borrowBook(username, bookId);
            response.getWriter().write("{\"status\":\"success\",\"message\":\"借阅成功\"}");
        } catch (IllegalArgumentException e) {
            response.setStatus(400);
            response.getWriter().write("{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
            response.getWriter().write("{\"status\":\"error\",\"message\":\"服务器错误\"}");
        }
    }
}
