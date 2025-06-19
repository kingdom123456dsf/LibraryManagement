package com.example.controller;

import com.example.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Map;

/**
 * 用户信息更新控制器，处理用户更新请求
 */
@WebServlet("/api/update-user")
public class UpdateUserController extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        Object sessionUser = (session != null) ? session.getAttribute("user") : null;

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> data = mapper.readValue(request.getInputStream(), Map.class);

        String oldUsername = data.get("oldUsername").toString();
        String newUsername = data.get("newUsername").toString();
        String newPassword = data.getOrDefault("newPassword", "").toString();

        try {
            boolean updated = userService.updateUser(oldUsername, newUsername, newPassword);
            if (updated) {
                // 更新session中的用户名
                if (session != null && sessionUser != null && sessionUser.equals(oldUsername)) {
                    session.setAttribute("user", newUsername);
                }
                response.getWriter().write("{\"message\":\"信息已更新\"}");
            } else {
                response.setStatus(400);
                response.getWriter().write("{\"message\":\"更新失败，用户不存在\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
            response.getWriter().write("{\"message\":\"服务器错误\"}");
        }
    }
}
