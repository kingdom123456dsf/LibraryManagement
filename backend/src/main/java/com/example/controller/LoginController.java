package com.example.controller;

import com.example.entity.User;
import com.example.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Map;

/**
 * 用户登录控制器，处理登录请求
 */
@WebServlet("/api/login")
public class LoginController extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> data = mapper.readValue(request.getInputStream(), Map.class);
        String username = data.get("username");
        String password = data.get("password");

        try {
            User user = userService.login(username, password);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user.getUsername());
                session.setAttribute("role", user.getRole());

                System.out.println("用户登录成功，Session ID = " + session.getId());

                response.getWriter().write("{\"status\":\"success\",\"role\":\"" + user.getRole() + "\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"status\":\"error\",\"message\":\"用户名或密码错误\"}");
            }
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            response.setStatus(500);
            response.getWriter().write("{\"status\":\"error\",\"message\":\"服务器错误\"}");
            e.printStackTrace();
        }
    }
}
