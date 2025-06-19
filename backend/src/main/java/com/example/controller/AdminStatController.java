package com.example.controller;

import com.example.service.StatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 管理员统计接口，返回图书分类柱状图数据
 */
@WebServlet("/api/admin/stat/category-count")
public class AdminStatController extends HttpServlet {
    private final StatService statService = new StatService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            List<Map<String, Object>> data = statService.getCategoryCount();

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(data);

            response.getWriter().write(json);
        } catch (SQLException e) {
            response.setStatus(500);
            response.getWriter().write("{\"message\":\"数据库错误\"}");
        }
    }
}
