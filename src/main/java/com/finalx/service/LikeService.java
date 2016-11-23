package com.finalx.service;

import com.finalx.util.JedisAdapter;
import com.finalx.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tengyu on 2016/8/3.
 */
@Service

public class LikeService {
    @Autowired
    JedisAdapter jedisAdapter;

    public long getLikeCount(int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        //直接获取喜欢的数量
        return jedisAdapter.scard(likeKey);
    }
    public long getLikeStatus(int userId,int entityType, int entityId) {
        //判断喜欢的set集合中是否有用户的id，
        // 有标明喜欢返回1，不喜欢返回-1,没有表态0
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        if (jedisAdapter.sismember(likeKey, String.valueOf(userId))) {
            return 1;
        }
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        return jedisAdapter.sismember(disLikeKey,String.valueOf(userId))?-1:0;
    }

    public long like(int userId, int entityType, int entityId) {
        //添加喜欢的用户id到set集合中
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedisAdapter.sadd(likeKey, String.valueOf(userId));
        //将用户id从不喜欢集合中移除
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        jedisAdapter.srem(disLikeKey, String.valueOf(userId));
        //返回喜欢集合的数量
        return jedisAdapter.scard(likeKey);
    }

    public long disLike(int userId, int entityType, int entityId) {
        //添加到不喜欢
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        jedisAdapter.sadd(disLikeKey, String.valueOf(userId));
        //移除喜欢
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedisAdapter.srem(likeKey, String.valueOf(userId));
        //最后还是返回喜欢的人数
        return jedisAdapter.scard(likeKey);
    }
}
