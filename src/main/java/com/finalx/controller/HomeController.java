package com.finalx.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.finalx.model.*;
import com.finalx.service.CommentService;
import com.finalx.service.FollowService;
import com.finalx.service.QuestionService;
import com.finalx.service.UserService;
import com.finalx.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nowcoder on 2016/7/15.
 */
@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;

    @Autowired
    FollowService followService;

    @Autowired
    CommentService commentService;

    @Autowired
    HostHolder hostHolder;

    private List<ViewObject> getQuestions(int userId, int offset, int limit) {
        List<Question> questionList = questionService.getLatestQuestions(userId, offset, limit);
        List<ViewObject> vos = new ArrayList<>();
        for (Question question : questionList) {
            ViewObject vo = new ViewObject();
            vo.set("question", question);
            vo.set("followCount",followService.getFollowerCount(EntityType.ENTITY_QUESTION,question.getId()));
            vo.set("user", userService.getUser(question.getUserId()));
            vos.add(vo);
        }
        return vos;
    }


    //主页
    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model,
                        @RequestParam(value = "pop", defaultValue = "0") int pop) {
        model.addAttribute("vos", getQuestions(0, 0, 10));
        return "index";
    }

    //返回JSON字符串
    @RequestMapping(path = {"/getQuestion"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String getQuestion(@RequestParam("offset") int offset,@RequestParam("limit") int limit) {
        List<Question> questionList = questionService.getLatestQuestions(0, offset, limit);
        List<Map<String,Object>> list=new ArrayList<>();
        for (Question question : questionList) {
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("question", JSON.toJSONString(question));
            map.put("followCount",followService.getFollowerCount(EntityType.ENTITY_QUESTION,question.getId()));
            map.put("user",JSON.toJSONString(userService.getUser(question.getUserId())));
            list.add(map);
        }
        return JSON.toJSONString(list);
    }






    @RequestMapping(path = {"/user/{userId}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(Model model, @PathVariable("userId") int userId) {
        model.addAttribute("vos", getQuestions(userId, 0, 10));
        User user = userService.getUser(userId);
        ViewObject vo = new ViewObject();
        vo.set("user", user);
        vo.set("commentCount", commentService.getUserCommentCount(userId));
        vo.set("followerCount", followService.getFollowerCount(EntityType.ENTITY_USER, userId));
        vo.set("followeeCount", followService.getFolloweeCount(userId, EntityType.ENTITY_USER));
        if (hostHolder.getUser() != null) {
            vo.set("followed", followService.isFollower(hostHolder.getUser().getId(), EntityType.ENTITY_USER, userId));
        } else {
            vo.set("followed",false);
        }
        model.addAttribute("profileUser", vo);
        return "profile";
    }
}
