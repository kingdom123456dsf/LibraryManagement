package com.example.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * 获取当前登录用户信息
 * GET /api/user-info
 */
@WebServlet("/api/user-info")
public class UserInfoController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取当前 session 中的用户信息
        HttpSession session = request.getSession(false);
        Object username = (session != null) ? session.getAttribute("user") : null;
        Object role = (session != null) ? session.getAttribute("role") : null;

        // 返回用户信息
        if (username != null) {
            response.getWriter().write("{\"username\":\"" + username + "\",\"role\":\"" + role + "\"}");
        } else {
            response.setStatus(401);
            response.getWriter().write("{\"message\":\"未登录\"}");
        }
    }
}
