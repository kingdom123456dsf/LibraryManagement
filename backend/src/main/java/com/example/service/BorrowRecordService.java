package com.example.service;

import com.example.dao.BorrowRecordDao;
import com.example.dao.UserDao;
import com.example.entity.BorrowRecord;
import com.example.entity.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 借阅记录业务逻辑类
 */
public class BorrowRecordService {
    private final BorrowRecordDao borrowRecordDao = new BorrowRecordDao();

    /**
     * 查询用户借阅记录，包含图书标题
     */
    public List<Map<String, Object>> getBorrowRecordsByUserId(int userId) throws SQLException {
        return borrowRecordDao.findByUserIdWithBookTitle(userId);
    }

    /**
     * 处理还书业务
     */
    public boolean returnBook(int borrowId) throws SQLException {
        Integer bookId = borrowRecordDao.markReturned(borrowId);
        return bookId != null;
    }

    // 添加此方法到原有 BorrowRecordService 中
    public List<Map<String, Object>> getUserBorrowHistory(String username) throws SQLException {
        User user = new UserDao().findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        return borrowRecordDao.getBorrowHistoryByUserId(user.getId());
    }

}
