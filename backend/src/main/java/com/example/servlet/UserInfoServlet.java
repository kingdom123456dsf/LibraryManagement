package com.example.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

//@WebServlet("/api/user-info")
public class UserInfoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        Object username = (session != null) ? session.getAttribute("user") : null;
        Object role = (session != null) ? session.getAttribute("role") : null;

        if (username != null) {
            // 返回包含用户名和角色的 JSON
            response.getWriter().write("{\"username\":\"" + username + "\",\"role\":\"" + role + "\"}");
        } else {
            response.setStatus(401);
            response.getWriter().write("{\"message\":\"未登录\"}");
        }
    }
}