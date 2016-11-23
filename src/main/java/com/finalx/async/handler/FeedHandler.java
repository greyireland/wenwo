
package com.finalx.async.handler;

import com.alibaba.fastjson.JSONObject;
import com.finalx.async.EventHandler;
import com.finalx.async.EventModel;
import com.finalx.async.EventType;
import com.finalx.model.*;
import com.finalx.service.*;
import com.finalx.util.JedisAdapter;
import com.finalx.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * Created by tengyu on 2016/8/19.
 */

@Component
public class FeedHandler implements EventHandler{
    private static final Logger logger = LoggerFactory.getLogger(FeedHandler.class);
    @Autowired
    FollowService followService;
    @Autowired
    UserService userService;
    @Autowired
    FeedService feedService;
    @Autowired
    JedisAdapter jedisAdapter;
    @Autowired
    QuestionService questionService;
    @Autowired
    CommentService commentService;

    @Override
    public void doHandle(EventModel model) {
        // 为了测试，把model的userId随机一下
        //Random r = new Random();
        //model.setActorId(1+r.nextInt(10));

        Feed feed = new Feed();
        feed.setCreatedDate(new Date());
        feed.setType(model.getType().getValue());
        //保存谁触发的新鲜事
        feed.setUserId(model.getActorId());
        feed.setData(buildFeedData(model));
        if (feed.getData() == null) {
            return ;
        }

        feedService.addFeed(feed);
        logger.info(feed.toString());

        List<Integer> followers = followService.getFollowers(EntityType.ENTITY_USER, model.getActorId(), Integer.MAX_VALUE);
        //系统用户
        followers.add(0);
        for (int follower : followers) {
            String timelineKey = RedisKeyUtil.getTimelineKey(follower);
            //将新鲜事推送到用户的队列中
            jedisAdapter.lpush(timelineKey, String.valueOf(feed.getId()));
        }
    }
    //将事件模型转为字符串
    private String buildFeedData(EventModel model) {
        Map<String, String> map = new HashMap<>();
        //触发用户
        User actor = userService.getUser(model.getActorId());
        if (actor == null) {
            return null;
        }
        map.put("userId", String.valueOf(actor.getId()));
        map.put("userHead", actor.getHeadUrl());
        map.put("userName", actor.getName());
        //暂时先处理两种：1.关注的好友关注某个问题 2.关注的好友评论某个问题
        if (model.getType() == EventType.FOLLOW && model.getEntityType() == EntityType.ENTITY_QUESTION) {
            Question question = questionService.getById(model.getEntityId());
            if (question == null) {
                return null;
            }
            map.put("questionId", String.valueOf(question.getId()));
            map.put("questionTitle", question.getTitle());
            //扩展
            //map.put("questionContent", question.getContent().length()>300?question.getContent().substring(0,300):question.getContent());
            return JSONObject.toJSONString(map);
        } else if (model.getType() == EventType.COMMENT) {
            Question question = questionService.getById(Integer.valueOf(model.getExt("questionId")));
            if (question == null) {
                return null;
            }
            map.put("questionId", String.valueOf(question.getId()));
            map.put("questionTitle", question.getTitle());
            return JSONObject.toJSONString(map);
        }
        return null;
    }
    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(new EventType[]{EventType.COMMENT, EventType.FOLLOW});
    }
}

