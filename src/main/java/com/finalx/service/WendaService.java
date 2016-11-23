package com.finalx.service;

import org.springframework.stereotype.Component;

/**
 * Created by tengyu on 2016/7/13.
 */
@Component
public class WendaService {
    public String getMessage(int userId) {
        return "hello,service"+String.valueOf(userId);
    }
}
