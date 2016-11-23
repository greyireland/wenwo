package com.finalx.async.handler;

import com.finalx.async.EventHandler;
import com.finalx.async.EventModel;
import com.finalx.async.EventType;
import com.finalx.util.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tengyu on 2016/8/5.
 */
@Component
public class LoginHandler  implements EventHandler{
    @Autowired
    MailSender mailSender;

    //用户登录
    @Override
    public void doHandle(EventModel model) {
        // xxxx判断发现这个用户登陆异常
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", model.getExt("username"));
        System.out.println(map.get("username"));
        //mailSender.sendWithHTMLTemplate(model.getExt("email"), "登陆IP异常", "mails/login_exception.html", map);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LOGIN);
    }
}
