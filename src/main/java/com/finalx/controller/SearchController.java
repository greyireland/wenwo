package com.finalx.controller;

import com.finalx.model.EntityType;
import com.finalx.model.Question;
import com.finalx.model.ViewObject;
import com.finalx.service.FollowService;
import com.finalx.service.QuestionService;
import com.finalx.service.SearchService;
import com.finalx.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tengyu on 2016/8/29.
 */
@Controller
public class SearchController {
    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
    @Autowired
    SearchService searchService;
    @Autowired
    FollowService followService;
    @Autowired
    UserService userService;
    @Autowired
    QuestionService questionService;

    @RequestMapping(path = {"/search"}, method = {RequestMethod.GET})
    public String search(Model model, @RequestParam("q") String keyword, @RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "count", defaultValue = "10") int count) {
        try {
            List<Question> questionList = searchService.searchQuestion(keyword, offset, count, "<em>", "</em>");
            List<ViewObject> vos = new ArrayList<>();
            for (Question question : questionList) {
                Question q = questionService.getById(question.getId());
                ViewObject vo = new ViewObject();
                if (question.getTitle() != null) {
                    q.setTitle(question.getTitle());
                }
                if (question.getContent() != null) {
                    q.setContent(question.getContent());
                }
                vo.set("question", q);
                vo.set("followCount",followService.getFollowerCount(EntityType.ENTITY_QUESTION,question.getId()));
                vo.set("user", userService.getUser(q.getUserId()));
                vos.add(vo);
            }
            model.addAttribute("vos",vos);
            model.addAttribute("keyword", keyword);

        } catch (Exception e) {
            logger.error("搜索出错"+e.getMessage());
        }
        return "result";
    }

}
