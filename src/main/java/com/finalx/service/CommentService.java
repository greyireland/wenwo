package com.finalx.service;

import com.finalx.dao.CommentDAO;
import com.finalx.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tengyu on 2016/7/28.
 */
@Service
public class CommentService {
    @Autowired
    private CommentDAO commentDAO;

    public List<Comment> getCommentsByEntity(int entityId, int entityType) {
        return commentDAO.selectByEntity(entityId, entityType);
    }

    public int addComment(Comment comment) {
        return commentDAO.addComment(comment);
    }

    public int getCommentCount(int entityId, int entityType) {
        return commentDAO.getCommentCount(entityId, entityType);
    }

    public void deleteComment(int entityId, int entityType) {
        commentDAO.updateStatus(entityId, entityType, 1);
    }

    public Comment getCommentById(int commentId) {
        return commentDAO.getCommentById(commentId);
    }

    public Object getUserCommentCount(int userId) {
        return commentDAO.getUserCommentCount(userId);
    }
}
