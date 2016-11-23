package com.finalx.controller;

import com.finalx.async.EventModel;
import com.finalx.async.EventProducer;
import com.finalx.async.EventType;
import com.finalx.model.Comment;
import com.finalx.model.EntityType;
import com.finalx.model.HostHolder;
import com.finalx.service.CommentService;
import com.finalx.service.QuestionService;
import com.finalx.service.SensitiveService;
import com.finalx.service.UserService;
import com.finalx.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;

/**
 * Created by tengyu on 2016/7/28.
 */
@Controller
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    @Autowired
    QuestionService questionService;

    @Autowired
    SensitiveService sensitiveService;

    @Autowired
    EventProducer eventProducer;

    @RequestMapping(path = {"/addComment"}, method = {RequestMethod.POST})
    public String addComment(@RequestParam("questionId") int questionId,
                             @RequestParam("content") String content) {
        try {
            content = HtmlUtils.htmlEscape(content);
            content = sensitiveService.filter(content);
            Comment comment = new Comment();
            if (hostHolder.getUser() != null) {
                comment.setUserId(hostHolder.getUser().getId());
            } else {
                comment.setUserId(WendaUtil.ANONYMOUS_USERID);
            }
            comment.setContent(content);
            comment.setEntityId(questionId);
            comment.setEntityType(EntityType.ENTITY_QUESTION);
            comment.setCreatedDate(new Date());
            comment.setStatus(0);
            commentService.addComment(comment);

            //评论事件
            eventProducer.fireEvent(new EventModel(EventType.COMMENT)
                    .setActorId(hostHolder.getUser().getId())
                    .setEntityType(EntityType.ENTITY_COMMENT)
                    .setEntityId(comment.getId())
                    .setEntityOwnerId(questionService.getById(questionId).getUserId())
                    .setExt("questionId", String.valueOf(questionId)));

            int count = commentService.getCommentCount(comment.getEntityId(), comment.getEntityType());
            questionService.updateCommentCount(comment.getEntityId(), count);

        } catch (Exception e) {
            logger.error("增加评论失败了"+e.getMessage());
        }
        return "redirect:/question/" + String.valueOf(questionId);
    }
    }

