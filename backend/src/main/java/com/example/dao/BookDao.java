package com.example.dao;

import com.example.entity.Book;
import com.example.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 图书数据访问对象，负责数据库操作
 */
public class BookDao {

    /**
     * 根据查询条件查询图书，支持分页
     *
     * @param query    书名模糊查询关键字
     * @param category 分类筛选
     * @param offset   分页偏移
     * @param size     每页大小
     * @return 图书列表
     * @throws SQLException 数据库异常
     */
    public List<Book> findBooks(String query, String category, int offset, int size) throws SQLException {
        List<Book> books = new ArrayList<>();
        try (Connection conn = DBUtil.getDataSource().getConnection()) {
            StringBuilder sql = new StringBuilder("SELECT SQL_CALC_FOUND_ROWS * FROM books WHERE 1=1");
            List<Object> params = new ArrayList<>();

            if (query != null && !query.isEmpty()) {
                sql.append(" AND title LIKE ?");
                params.add("%" + query + "%");
            }
            if (category != null && !category.isEmpty()) {
                sql.append(" AND category = ?");
                params.add(category);
            }

            sql.append(" LIMIT ?, ?");
            params.add(offset);
            params.add(size);

            PreparedStatement stmt = conn.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setIsbn(rs.getString("isbn"));
                book.setDescription(rs.getString("description"));
                book.setImage(rs.getString("image"));
                book.setAvailable(rs.getBoolean("available"));
                book.setCategory(rs.getString("category"));
                books.add(book);
            }
        }
        return books;
    }

    /**
     * 获取上一条查询的总记录数
     *
     * @return 总记录数
     * @throws SQLException 数据库异常
     */
    public int getLastFoundRows() throws SQLException {
        int total = 0;
        try (Connection conn = DBUtil.getDataSource().getConnection()) {
            ResultSet rs = conn.prepareStatement("SELECT FOUND_ROWS()").executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        }
        return total;
    }

    /**
     * 添加图书
     *
     * @param book 图书实体
     * @throws SQLException
     */
    public void addBook(Book book) throws SQLException {
        String sql = "INSERT INTO books (title, author, isbn, description, available, image, category) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getIsbn());
            stmt.setString(4, book.getDescription());
            stmt.setBoolean(5, book.isAvailable());
            stmt.setString(6, book.getImage());
            stmt.setString(7, book.getCategory());
            stmt.executeUpdate();
        }
    }

    /**
     * 修改图书
     *
     * @param book 图书实体
     * @throws SQLException
     */
    public void updateBook(Book book) throws SQLException {
        String sql = "UPDATE books SET title=?, author=?, isbn=?, description=?, available=?, image=?, category=? WHERE id=?";
        try (Connection conn = DBUtil.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getIsbn());
            stmt.setString(4, book.getDescription());
            stmt.setBoolean(5, book.isAvailable());
            stmt.setString(6, book.getImage());
            stmt.setString(7, book.getCategory());
            stmt.setInt(8, book.getId());
            stmt.executeUpdate();
        }
    }

    /**
     * 删除图书
     *
     * @param id 图书ID
     * @throws SQLException
     */
    public void deleteBook(int id) throws SQLException {
        String sql = "DELETE FROM books WHERE id=?";
        try (Connection conn = DBUtil.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // 检查图书是否可借
    public boolean isAvailable(int bookId) throws SQLException {
        String sql = "SELECT available FROM books WHERE id = ?";
        try (Connection conn = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getBoolean("available");
        }
    }

    // 设置图书是否可借
    public void setAvailable(int bookId, boolean available) throws SQLException {
        String sql = "UPDATE books SET available = ? WHERE id = ?";
        try (Connection conn = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, available);
            ps.setInt(2, bookId);
            ps.executeUpdate();
        }
    }

}
