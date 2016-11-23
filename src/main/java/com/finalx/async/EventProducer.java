package com.finalx.async;

import com.alibaba.fastjson.JSONObject;
import com.finalx.util.JedisAdapter;
import com.finalx.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tengyu on 2016/8/3.
 */
@Service
public class EventProducer {
    @Autowired
    JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel eventModel) {
        try {
            String json = JSONObject.toJSONString(eventModel);
            //BIZ_EVENTQUEUE
            String key = RedisKeyUtil.getEventqueueKey();
            jedisAdapter.lpush(key, json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
