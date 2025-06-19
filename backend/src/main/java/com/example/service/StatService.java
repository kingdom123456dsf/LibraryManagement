package com.example.service;

import com.example.dao.StatDao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 统计相关业务逻辑处理类
 */
public class StatService {
    private final StatDao statDao = new StatDao();

    /**
     * 获取图书分类数量统计数据
     */
    public List<Map<String, Object>> getCategoryCount() throws SQLException {
        return statDao.countBooksByCategory();
    }
}
