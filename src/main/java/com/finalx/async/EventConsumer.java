package com.finalx.async;

import com.alibaba.fastjson.JSON;
import com.finalx.util.JedisAdapter;
import com.finalx.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by tengyu on 2016/8/3.
 */
@Service
public class EventConsumer implements InitializingBean,ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);
    private Map<EventType, List<EventHandler>> config = new HashMap<>();
    //初始化注册
    private ApplicationContext applicationContext;

    @Autowired
    JedisAdapter jedisAdapter;

    @Override
    public void afterPropertiesSet() throws Exception {
        //找出所有的handler
        Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        if (beans != null) {
            for (Map.Entry<String, EventHandler> entry : beans.entrySet()) {
                List<EventType> eventTypes=entry.getValue().getSupportEventTypes();
                for (EventType type : eventTypes) {
                    //不存在就创建一个事件类型，+处理handler
                    //存在就直接添加到对应的处理handler集合中
                    if (!config.containsKey(type)) {
                        config.put(type, new ArrayList<EventHandler>());
                    }
                    config.get(type).add(entry.getValue());
                }
            }
        }


        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String key= RedisKeyUtil.getEventqueueKey();
                    List<String> events = jedisAdapter.brpop(0, key);
                    for (String message : events) {
                        if (message.equals(key)) {
                            continue;
                        }
                        //将消息队列中的事件转换为EventModel
                        EventModel eventModel = JSON.parseObject(message, EventModel.class);
                        if (!config.containsKey(eventModel.getType())) {
                            logger.error("不能识别的事件"+eventModel.toString());
                            continue;
                        }
                        //执行每一个事件
                        for (EventHandler handler : config.get(eventModel.getType())) {
                            logger.info("正在执行的事件："+eventModel.toString());
                            handler.doHandle(eventModel);
                        }
                    }
                }
            }
        }));
        /*Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String key= RedisKeyUtil.getEventqueueKey();
                    List<String> events = jedisAdapter.brpop(0, key);
                    for (String message : events) {
                        if (message.equals(key)) {
                            continue;
                        }
                        //将消息队列中的事件转换为EventModel
                        EventModel eventModel = JSON.parseObject(message, EventModel.class);
                        if (!config.containsKey(eventModel.getType())) {
                            logger.error("不能识别的事件"+eventModel.toString());
                            continue;
                        }
                        //执行每一个事件
                        for (EventHandler handler : config.get(eventModel.getType())) {
                            handler.doHandle(eventModel);
                        }
                    }
                }
            }
        });
        thread.start();*/
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext ) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
