package com.example.entity;

import java.sql.Timestamp;

/**
 * 借阅记录实体类，封装 borrow_records 表结构
 */
public class BorrowRecord {
    private int id;
    private int userId;
    private int bookId;
    private Timestamp borrowTime;
    private Timestamp returnTime;
    private boolean returned;

    // Getter 和 Setter

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getBookId() {
        return bookId;
    }
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    public Timestamp getBorrowTime() {
        return borrowTime;
    }
    public void setBorrowTime(Timestamp borrowTime) {
        this.borrowTime = borrowTime;
    }
    public Timestamp getReturnTime() {
        return returnTime;
    }
    public void setReturnTime(Timestamp returnTime) {
        this.returnTime = returnTime;
    }
    public boolean isReturned() {
        return returned;
    }
    public void setReturned(boolean returned) {
        this.returned = returned;
    }
}
