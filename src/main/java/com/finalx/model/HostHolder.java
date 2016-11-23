package com.finalx.model;

import org.springframework.stereotype.Component;

/**
 */
@Component
public class HostHolder {
    //每一个请求相当于一个线程，这里为每一个请求创建一个本地存储user的地方
    private static ThreadLocal<User> users = new ThreadLocal<User>();

    public User getUser() {
        return users.get();
    }

    public void setUser(User user) {
        users.set(user);
    }

    public void clear() {
        users.remove();
    }
}
