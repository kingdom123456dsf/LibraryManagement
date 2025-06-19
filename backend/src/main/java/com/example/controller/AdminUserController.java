package com.example.controller;

import com.example.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * 获取所有用户（仅管理员）
 * GET /api/admin/users
 */
@WebServlet("/api/admin/users")
public class AdminUserController extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 检查是否为管理员
        HttpSession session = request.getSession(false);
        Object role = (session != null) ? session.getAttribute("role") : null;
        if (role == null || !"admin".equals(role)) {
            response.setStatus(403);
            response.getWriter().write("{\"message\":\"无权限\"}");
            return;
        }

        // 查询所有用户并返回
        var users = userService.getAllUsers();
//        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(users));
    }
}
