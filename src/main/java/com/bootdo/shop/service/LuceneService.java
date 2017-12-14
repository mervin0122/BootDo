package com.bootdo.shop.service;


import com.bootdo.common.utils.PageUtils;
import com.bootdo.lucene.IndexObject;

public interface LuceneService {

    void save(IndexObject indexObject);

    void update(IndexObject indexObject);

    void delete(IndexObject indexObject);

    PageUtils page(Integer pageNumber, Integer pageSize, String keyword);
}
