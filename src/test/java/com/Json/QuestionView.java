package com.Json;

import com.finalx.model.Question;
import com.finalx.model.User;

/**
 * Created by tengyu on 2016/8/26.
 */
public class QuestionView {
    public QuestionViewObject[] questionViewObjects;
}
class QuestionViewObject{
    public Question question;
    public int followCount;
    public User user;
}

