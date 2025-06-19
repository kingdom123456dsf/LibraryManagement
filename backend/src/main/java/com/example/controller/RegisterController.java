package com.example.controller;

import com.example.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Map;

/**
 * 用户注册控制器，处理注册请求
 */
@WebServlet("/api/register")
public class RegisterController extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> data = mapper.readValue(request.getInputStream(), Map.class);
        String username = data.get("username");
        String password = data.get("password");

        try {
            boolean success = userService.register(username, password);
            if (success) {
                response.getWriter().write("{\"status\":\"success\",\"message\":\"注册成功\"}");
            } else {
                response.getWriter().write("{\"status\":\"error\",\"message\":\"用户名已存在\"}");
            }
        } catch (IllegalArgumentException e) {
            response.getWriter().write("{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            response.setStatus(500);
            response.getWriter().write("{\"status\":\"error\",\"message\":\"数据库错误\"}");
            e.printStackTrace();
        }
    }
}
