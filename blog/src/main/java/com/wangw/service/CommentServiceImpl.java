package com.wangw.service;

import com.wangw.dao.CommentRepository;
import com.wangw.po.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Page<Comment> listAllComments(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Override
    public List<Comment> listCommentBlogById(Long blogId) {
        Sort sort = Sort.by(Sort.Direction.DESC,"createTime");
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId,sort);
        return eachComments(comments);
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1) {
            comment.setParentComment(commentRepository.findById(parentCommentId).get());
        } else {
            //如果双亲评论不为-1,那么说明该评论没有双亲评论,设置双亲评论为null,防止报错
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    @Transactional
    @Override
    public void deleteComment(Long id) {
        if (commentRepository.findById(id).get().getReplyComments() != null) {
            resetDeletedParentComment(id);
        }
        commentRepository.deleteById(id);
    }

    /**
     * @param blogId
     * 设置被删除的博客的评论对应博客为空
     **/

    @Transactional
    @Override
    public void setCommentOfDeletedBlogNull(Long blogId) {
        List<Comment> comments = commentRepository.findByBlogId(blogId);
        for (Comment comment : comments) {
            comment.setBlog(null);
            comment.setParentComment(null);
        }
    }

    /**
     * @param id
     * 重新设置将被删除评论的从属评论的属性
     * 找到将被删除评论的主键id，获取该评论
     * 找到该评论下的所有回复的评论
     * 找到将被删除的评论所属的主评论，不论是否存在，赋值给将被删除评论的所有从属评论
    **/
    private void resetDeletedParentComment(Long id) {
        Comment comment = commentRepository.findById(id).get();
        List<Comment> allsubcomments = comment.getReplyComments();
        Comment newParentComment = comment.getParentComment();
        for (Comment subcomment : allsubcomments) {
            subcomment.setParentComment(newParentComment);
        }
    }


    /**
     * 循环每个顶级的评论节点
     * @param comments
     * @return
     */
    private List<Comment> eachComments(List<Comment> comments) {
        List<Comment> commentViews = new ArrayList<>();
        for(Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            commentViews.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentViews);
        return commentViews;
    }

    /**
     * @param comments root根节点，blog不为空的对象集合
     * @return
     * 功能:对每一个主评论找出所有的子代评论,并放在一起
     */

    private void combineChildren(List<Comment> comments) {
        for(Comment comment : comments) {
            List<Comment> replys = comment.getReplyComments();
            for(Comment reply : replys) {
                recursively(reply);
            }
            comment.setReplyComments(tempReplys);
            tempReplys = new ArrayList<>();
        }
    }


    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();

    /**
     * 递归迭代，剥洋葱
     * @param comment 被迭代的对象
     * @return
     * 功能:找出一个指定的评论的所有子评论
     */
    private void recursively(Comment comment) {
        tempReplys.add(comment);
        if (comment.getReplyComments().size() > 0) {
            List<Comment> replys = comment.getReplyComments();
            for(Comment reply : replys) {
                if (reply.getReplyComments().size() > 0) {
                    recursively(reply);
                } else {
                    tempReplys.add(reply);
                }
            }
        }
    }
}
