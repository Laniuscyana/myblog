package com.wangw.service;

import com.wangw.po.Blog;
import com.wangw.po.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface CommentService {

    //分页显示评论
    Page<Comment> listAllComments(Pageable pageable);

    //获取评论列表
    List<Comment> listCommentBlogById(Long blogId);

    //根据博客id删除评论



    //保存评论
    Comment saveComment(Comment comment);

    //后台删除评论
    void deleteComment(Long id);

    //被删除博客对应评论为空
    void setCommentOfDeletedBlogNull(Long blogId);
}
