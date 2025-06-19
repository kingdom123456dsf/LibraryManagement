package com.example.service;

import com.example.dao.BookDao;
import com.example.entity.Book;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 图书业务逻辑类
 */
public class BookService {
    private final BookDao bookDao = new BookDao();

    /**
     * 查询图书及总数，封装为 Map
     *
     * @param query    书名模糊查询关键字
     * @param category 分类筛选
     * @param page     当前页码，从1开始
     * @param size     每页大小
     * @return 包含图书列表和总数的 Map
     * @throws SQLException 数据库异常
     */
    public Map<String, Object> getBooks(String query, String category, int page, int size) throws SQLException {
        int offset = (page - 1) * size;
        List<Book> books = bookDao.findBooks(query, category, offset, size);
        int total = bookDao.getLastFoundRows();

        Map<String, Object> result = new HashMap<>();
        result.put("data", books);
        result.put("total", total);
        return result;
    }

    /**
     * 添加图书
     *
     * @param book 图书实体
     * @throws SQLException
     */
    public void addBook(Book book) throws SQLException {
        bookDao.addBook(book);
    }

    /**
     * 修改图书
     *
     * @param book 图书实体
     * @throws SQLException
     */
    public void updateBook(Book book) throws SQLException {
        bookDao.updateBook(book);
    }

    /**
     * 删除图书
     *
     * @param id 图书ID
     * @throws SQLException
     */
    public void deleteBook(int id) throws SQLException {
        bookDao.deleteBook(id);
    }
}
