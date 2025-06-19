package com.example.dao;

import com.example.entity.BorrowRecord;
import com.example.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 借阅记录数据访问对象，负责数据库操作
 */
public class BorrowRecordDao {
    /**
     * 根据用户ID查询借阅记录（包含图书标题）
     */
    public List<Map<String, Object>> findByUserIdWithBookTitle(int userId) throws SQLException {
        List<Map<String, Object>> records = new ArrayList<>();

        String sql = "SELECT br.id, br.borrow_time, br.return_time, br.returned, b.title " +
                "FROM borrow_records br JOIN books b ON br.book_id = b.id WHERE br.user_id = ?";

        try (Connection conn = DBUtil.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", rs.getInt("id"));
                map.put("title", rs.getString("title"));
                map.put("borrowTime", rs.getTimestamp("borrow_time"));
                map.put("returnTime", rs.getTimestamp("return_time"));
                map.put("returned", rs.getBoolean("returned"));
                records.add(map);
            }
        }

        return records;
    }

    /**
     * 获取某用户的借阅历史记录，包含书名和借还时间等
     */
    public List<Map<String, Object>> getBorrowHistoryByUserId(int userId) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();

        String sql = """
        SELECT b.title, br.borrow_time, br.return_time, br.returned
        FROM borrow_records br
        JOIN books b ON br.book_id = b.id
        WHERE br.user_id = ?
        ORDER BY br.borrow_time DESC
    """;

        try (Connection conn = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("title", rs.getString("title"));
                row.put("borrowTime", rs.getTimestamp("borrow_time"));
                row.put("returnTime", rs.getTimestamp("return_time"));
                row.put("returned", rs.getBoolean("returned"));
                list.add(row);
            }
        }

        return list;
    }


    // 判断是否重复借阅
    public boolean existsUnreturned(int userId, int bookId) throws SQLException {
        String sql = "SELECT id FROM borrow_records WHERE user_id = ? AND book_id = ? AND returned = false";
        try (Connection conn = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    // 插入新借阅记录
    public void insertRecord(int userId, int bookId) throws SQLException {
        String sql = "INSERT INTO borrow_records (user_id, book_id) VALUES (?, ?)";
        try (Connection conn = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            ps.executeUpdate();
        }
    }

    /**
     * 设置借阅记录为已归还，返回对应书籍ID
     */
    public Integer markReturned(int borrowId) throws SQLException {
        Integer bookId = null;

        try (Connection conn = DBUtil.getDataSource().getConnection()) {
            // 开启事务
            conn.setAutoCommit(false);

            try (PreparedStatement update = conn.prepareStatement(
                    "UPDATE borrow_records SET returned = true, return_time = NOW() WHERE id = ?"
            )) {
                update.setInt(1, borrowId);
                int affectedRows = update.executeUpdate();
                if (affectedRows == 0) {
                    conn.rollback();
                    return null;
                }
            }

            try (PreparedStatement getBook = conn.prepareStatement(
                    "SELECT book_id FROM borrow_records WHERE id = ?"
            )) {
                getBook.setInt(1, borrowId);
                ResultSet rs = getBook.executeQuery();
                if (rs.next()) {
                    bookId = rs.getInt("book_id");
                } else {
                    conn.rollback();
                    return null;
                }
            }

            if (bookId != null) {
                try (PreparedStatement updateBook = conn.prepareStatement(
                        "UPDATE books SET available = true WHERE id = ?"
                )) {
                    updateBook.setInt(1, bookId);
                    updateBook.executeUpdate();
                }
            }

            conn.commit();
        }

        return bookId;
    }
}
