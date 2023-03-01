package com.wangw.service;

import com.wangw.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {

    //新增分类
    Type saveType(Type type);

    //查询
    Type getType(Long id);

    //查询分类名称
    Type getTypeByName(String name);

    //分页查询
    Page<Type> listType(Pageable pageable);

    //表单查询
    List<Type> listType();

    //获取展示在首页的分类
    List<Type> listTypeTop(Integer size);

    //修改
    Type updateType(Long id, Type type);

    //删除
    void deleteType(Long id);
}
