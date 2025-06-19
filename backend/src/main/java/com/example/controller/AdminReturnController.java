package com.example.controller;

import com.example.service.BorrowRecordService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

/**
 * 管理员还书接口
 */
@WebServlet("/api/admin/return")
public class AdminReturnController extends HttpServlet {
    private final BorrowRecordService service = new BorrowRecordService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> data = mapper.readValue(request.getInputStream(), Map.class);

        Object borrowIdObj = data.get("borrowId");
        if (borrowIdObj == null) {
            response.setStatus(400);
            response.getWriter().write("{\"message\":\"缺少 borrowId 参数\"}");
            return;
        }

        int borrowId;
        try {
            if (borrowIdObj instanceof Integer) {
                borrowId = (Integer) borrowIdObj;
            } else {
                borrowId = Integer.parseInt(borrowIdObj.toString());
            }
        } catch (NumberFormatException e) {
            response.setStatus(400);
            response.getWriter().write("{\"message\":\"borrowId 参数格式错误\"}");
            return;
        }

        try {
            boolean success = service.returnBook(borrowId);
            if (success) {
                response.getWriter().write("{\"message\":\"还书成功\"}");
            } else {
                response.setStatus(500);
                response.getWriter().write("{\"message\":\"还书失败，记录不存在\"}");
            }
        } catch (SQLException e) {
            response.setStatus(500);
            response.getWriter().write("{\"message\":\"还书失败\"}");
        }
    }
}
