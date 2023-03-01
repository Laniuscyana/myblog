package com.wangw.service;

import com.wangw.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {

    //新增标签
    Tag saveTag(Tag tag);

    //查询标签
    Tag getTag(Long id);

    //查询标签名称
    Tag getTagByName(String name);

    //分页查询
    Page<Tag> listTag(Pageable pageable);

    //获取所有标签
    List<Tag> listTag();

    //获取一列标签
    List<Tag> listTag(String ids);

    //获取展示在访问首页的标签
    List<Tag> listTagTop(Integer size);

    //修改标签
    Tag updateTag(Long id, Tag tag);

    //删除标签
    void deleteTag(Long id);
}
