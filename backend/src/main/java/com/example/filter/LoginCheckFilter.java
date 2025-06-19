package com.example.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

//✅ LoginCheckFilter.java
//作用：检查登录状态，仅拦截 /api/* 路径
//排除 /api/login 和 /api/register
//判断 session 中是否有 user 属性
//无登录则返回 401 拦截请求
@WebFilter("/api/*") // ⬅️ 拦截所有 API 接口请求
public class LoginCheckFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 登录接口不拦截
        String path = request.getRequestURI();
        if (path.endsWith("/login") || path.endsWith("/register")) {
            chain.doFilter(request, response);
            return;
        }


        // 检查 session 中是否有 user
        HttpSession session = request.getSession(false); // 不创建新会话
        Object user = (session != null) ? session.getAttribute("user") : null;

        // 对 admin 接口添加额外角色校验
        if (path.startsWith("/api/admin")) {
            String role = (session != null) ? (String) session.getAttribute("role") : null;
            if (!"admin".equals(role)) {
                response.setStatus(403);
                response.getWriter().write("{\"status\":\"forbidden\",\"message\":\"无管理员权限\"}");
                return;
            }
        }

        if (user == null) {
            // 未登录，返回 401 状态
            response.setContentType("application/json; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            response.getWriter().write("{\"status\":\"unauthorized\",\"message\":\"请先登录\"}");
            return;
        }

        // 已登录，放行
        chain.doFilter(request, response);
    }
}
