package com.example.dao;

import com.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * 统计相关数据库操作类
 */
public class StatDao {

    /**
     * 查询各分类图书数量
     *
     * @return List<Map<String, Object>>，每个 Map 包含 category 和 count
     * @throws SQLException 数据库异常
     */
    public List<Map<String, Object>> countBooksByCategory() throws SQLException {
        List<Map<String, Object>> result = new ArrayList<>();

        String sql = "SELECT category, COUNT(*) as count FROM books GROUP BY category";

        try (Connection conn = DBUtil.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("category", rs.getString("category"));
                item.put("count", rs.getInt("count"));
                result.add(item);
            }
        }

        return result;
    }
}
