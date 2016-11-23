package com.finalx.service;

import com.finalx.dao.FeedDAO;
import com.finalx.model.Feed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tengyu on 2016/8/19.
 */
@Service
public class FeedService {
    private static final Logger logger = LoggerFactory.getLogger(FeedService.class);
    @Autowired
    FeedDAO feedDAO;

    public List<Feed> getUserFeeds(int maxId, List<Integer> userIds, int count) {
        return feedDAO.selectUserFeeds(maxId, userIds, count);
    }

    public boolean addFeed(Feed feed) {
        int res=feedDAO.addFeed(feed);
        logger.info("添加新鲜事成功。");
        return res>0;
    }

    public Feed getById(int id) {
        return feedDAO.getFeedById(id);
    }
}
