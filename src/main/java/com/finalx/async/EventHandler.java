package com.finalx.async;

import java.util.List;

/**
 * Created by tengyu on 2016/8/3.
 */
public interface EventHandler {
    void doHandle(EventModel eventModel);

    //获取所有的事件类型
    List<EventType> getSupportEventTypes();
}
