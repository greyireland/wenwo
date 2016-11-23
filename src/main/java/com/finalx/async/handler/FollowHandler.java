package com.finalx.async.handler;

import com.finalx.async.EventHandler;
import com.finalx.async.EventModel;
import com.finalx.async.EventType;
import com.finalx.model.EntityType;
import com.finalx.model.Message;
import com.finalx.model.User;
import com.finalx.service.MessageService;
import com.finalx.service.UserService;
import com.finalx.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by tengyu on 2016/8/19.
 */
@Component
public class FollowHandler implements EventHandler {
    private static final Logger logger = LoggerFactory.getLogger(FollowHandler.class);
    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    //分为两种：1.关注某个人 2.关注某个问题
    @Override
    public void doHandle(EventModel model) {
        Message message = new Message();
        message.setFromId(WendaUtil.SYSTEM_USERID);
        message.setToId(model.getEntityOwnerId());
        message.setCreatedDate(new Date());
        User user = userService.getUser(model.getActorId());
        logger.info(model.toString()+":"+user.toString());
        if (model.getEntityType() == EntityType.ENTITY_QUESTION) {
            message.setContent("用户" + user.getName()
                    + "关注了你的问题,http://127.0.0.1:8080/question/" + model.getEntityId());
        } else if (model.getEntityType() == EntityType.ENTITY_USER) {
            message.setContent("用户" + user.getName()
                    + "关注了你,http://127.0.0.1:8080/user/" + model.getActorId());
        }

        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.FOLLOW);
    }
}
