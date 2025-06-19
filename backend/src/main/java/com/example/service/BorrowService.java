package com.example.service;

import com.example.dao.BookDao;
import com.example.dao.BorrowRecordDao;
import com.example.dao.UserDao;
import com.example.entity.User;

import java.sql.SQLException;

public class BorrowService {

    private final UserDao userDao = new UserDao();
    private final BookDao bookDao = new BookDao();
    private final BorrowRecordDao borrowRecordDao = new BorrowRecordDao();

    /**
     * 用户借阅图书
     *
     * @param username 用户名
     * @param bookId   图书ID
     */
    public void borrowBook(String username, int bookId) throws SQLException {
        // 查用户
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("用户不存在");
        }

        int userId = user.getId();

        // 是否已借未还
        boolean alreadyBorrowed = borrowRecordDao.existsUnreturned(userId, bookId);
        if (alreadyBorrowed) {
            throw new IllegalArgumentException("不能重复借阅");
        }

        // 图书是否可借
        boolean available = bookDao.isAvailable(bookId);
        if (!available) {
            throw new IllegalArgumentException("该书不可借");
        }

        // 插入借阅记录
        borrowRecordDao.insertRecord(userId, bookId);

        // 更新图书状态
        bookDao.setAvailable(bookId, false);
    }
}
