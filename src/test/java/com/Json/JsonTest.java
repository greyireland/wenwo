package com.Json;

import com.alibaba.fastjson.JSON;
import com.finalx.model.Question;
import com.finalx.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tengyu on 2016/8/26.
 */
public class JsonTest {
    public static void main(String[] args) {
        complextTest2();
    }
    public static void complexTest() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", "owen");
        map.put("age", 25);
        map.put("sex", "男");

        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("name", "jack");
        temp.put("age", 18);
        map.put("girinfo", temp);

        List<String> list = new ArrayList<String>();
        list.add("爬山");
        list.add("电影");
        list.add("旅游");
        map.put("hobby", list);

        String jsonStr = JSON.toJSONString(map);
        System.out.println(jsonStr);
    }

    public static void complextTest2() {
        List<Map<String,Object>> list=new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Question question=new Question();
            question.setId(1);
            question.setContent("你好");
            question.setTitle("向你问好。");

            User user = new User();
            user.setName("xiaobai");
            user.setPassword("123456");

            Map<String,Object> map=new HashMap<String, Object>();
            map.put("question",question);
            map.put("count", 1);
            map.put("user", user);
            list.add(map);
        }
        System.out.println(JSON.toJSONString(list));


    }
}

