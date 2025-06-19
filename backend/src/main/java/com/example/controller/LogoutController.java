package com.example.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * 用户登出接口
 * POST /api/logout
 */
@WebServlet("/api/logout")
public class LogoutController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取当前会话
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate(); // 使会话失效（清除用户状态）

        // 返回响应
        response.getWriter().write("{\"message\":\"已退出\"}");
    }
}
