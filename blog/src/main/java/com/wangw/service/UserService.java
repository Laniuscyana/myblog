package com.wangw.service;

import com.wangw.po.User;

public interface UserService {
    User checkUser(String username, String password);
}
