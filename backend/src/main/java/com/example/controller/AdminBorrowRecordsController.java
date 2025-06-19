package com.example.controller;

import com.example.service.BorrowRecordService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 管理员获取指定用户借阅记录接口
 */
@WebServlet("/api/admin/borrow-records")
public class AdminBorrowRecordsController extends HttpServlet {
    private final BorrowRecordService borrowRecordService = new BorrowRecordService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userIdStr = request.getParameter("userId");

        if (userIdStr == null) {
            response.setStatus(400);
            response.getWriter().write("{\"message\":\"缺少 userId 参数\"}");
            return;
        }

        try {
            int userId = Integer.parseInt(userIdStr);
            List<Map<String, Object>> records = borrowRecordService.getBorrowRecordsByUserId(userId);

            response.setContentType("application/json;charset=UTF-8");
            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().write(mapper.writeValueAsString(records));
        } catch (NumberFormatException e) {
            response.setStatus(400);
            response.getWriter().write("{\"message\":\"userId 参数格式错误\"}");
        } catch (Exception e) {
            response.setStatus(500);
            response.getWriter().write("{\"message\":\"查询失败\"}");
        }
    }
}
