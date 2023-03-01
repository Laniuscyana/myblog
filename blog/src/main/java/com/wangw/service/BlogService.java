package com.wangw.service;

import com.wangw.po.Blog;
import com.wangw.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {


    //博客组件id，用于定位
    Blog getBlog(Long id);

    //转换博文格式为html时调用该方法
    Blog getAndConvert(Long id);

    //分页首页显示博客
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    //博客分页
    Page<Blog> listBlog(Pageable pageable);

    //博客查询分页
    Page<Blog> listBlog(String query, Pageable pageable);

    //博客按标签显示分页
    Page<Blog> listBlog(Long tagId, Pageable pageable);

    //按时间倒序排列推荐的博客
    List<Blog> listRecommendBlogTop(Integer size);

    //所有博客归档
    Map<String,List<Blog>> archiveBlog();

    //统计所有博客条数
    Long countBlog();

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);
}
