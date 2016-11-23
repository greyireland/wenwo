package com.finalx.async.handler;

import com.finalx.async.EventHandler;
import com.finalx.async.EventModel;
import com.finalx.async.EventType;
import com.finalx.model.Message;
import com.finalx.model.User;
import com.finalx.service.MessageService;
import com.finalx.service.UserService;
import com.finalx.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by tengyu on 2016/8/5.
 */
@Component
public class LikeHandler implements EventHandler{

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    //给评论点赞
    @Override
    public void doHandle(EventModel eventModel) {
        //给被赞的用户发送一个消息
        Message message=new Message();
        message.setFromId(WendaUtil.SYSTEM_USERID);
        message.setToId(eventModel.getEntityOwnerId());
        message.setCreatedDate(new Date());
        User user = userService.getUser(eventModel.getActorId());
        message.setContent("用户" + user.getName() + "赞了你的评论，http://127.0.0.1:8080/question/" + eventModel.getExt("questionId"));
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}

