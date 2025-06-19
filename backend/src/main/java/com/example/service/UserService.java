package com.example.service;

import com.example.dao.UserDao;
import com.example.entity.User;

import java.sql.SQLException;

import java.util.List;
import java.util.Map;

/**
 * 用户业务逻辑层，处理用户相关操作的业务逻辑
 */
public class UserService {
    private final UserDao userDao = new UserDao();

    /**
     * 验证用户登录
     */
    public User login(String username, String password) throws SQLException {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new IllegalArgumentException("用户名和密码不能为空");
        }
        return userDao.findByUsernameAndPassword(username, password);
    }

    /**
     * 注册新用户
     */
    public boolean register(String username, String password) throws SQLException {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new IllegalArgumentException("用户名和密码不能为空");
        }
        User existingUser = userDao.findByUsername(username);
        if (existingUser != null) {
            return false; // 用户名已存在
        }
        int rows = userDao.insertUser(username, password);
        return rows > 0;
    }

    /**
     * 更新用户信息
     */
    public boolean updateUser(String oldUsername, String newUsername, String newPassword) throws SQLException {
        if (oldUsername == null || oldUsername.isBlank() ||
                newUsername == null || newUsername.isBlank()) {
            throw new IllegalArgumentException("用户名不能为空");
        }
        int rows = userDao.updateUser(oldUsername, newUsername, newPassword);
        return rows > 0;
    }

    /**
     * 查询用户信息
     */
    public User getUserByUsername(String username) throws SQLException {
        return userDao.findByUsername(username);
    }

    /**
     * 获取所有用户信息（供管理员使用）
     */
    public List<Map<String, Object>> getAllUsers() {
        return userDao.queryAllUsers();
    }
}
