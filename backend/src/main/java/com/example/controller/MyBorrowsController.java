package com.example.controller;

import com.example.service.BorrowRecordService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/api/my-borrows")
public class MyBorrowsController extends HttpServlet {

    private final BorrowRecordService borrowRecordService = new BorrowRecordService();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        Object usernameObj = (session != null) ? session.getAttribute("user") : null;

        if (usernameObj == null) {
            response.setStatus(401);
            response.getWriter().write("{\"message\":\"请先登录\"}");
            return;
        }

        String username = usernameObj.toString();

        try {
            List<Map<String, Object>> records = borrowRecordService.getUserBorrowHistory(username);
            response.getWriter().write(mapper.writeValueAsString(records));
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
            response.getWriter().write("{\"message\":\"服务器错误\"}");
        }
    }
}
