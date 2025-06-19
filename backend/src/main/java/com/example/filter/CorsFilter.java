package com.example.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//✅ CorsFilter.java
//作用：处理跨域请求（CORS）
//设置允许跨域的响应头
//允许前端携带 Cookie（即 Access-Control-Allow-Credentials: true）
//拦截所有请求（/*），包括 API、静态资源、OPTIONS 预检
//这个是必须保留的，只要你前后端分离部署（例如 localhost:8080 → localhost:8081）
@WebFilter("/*")
public class CorsFilter implements Filter {
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080"); // 必须是前端地址
        response.setHeader("Access-Control-Allow-Credentials", "true"); // ✅ 最关键
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        // 预检请求直接返回
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        chain.doFilter(req, res);
    }
}

