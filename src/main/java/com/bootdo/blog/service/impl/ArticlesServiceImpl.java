package com.bootdo.blog.service.impl;

import com.bootdo.blog.dao.ArticleMapper;
import com.bootdo.blog.domain.Article;
import com.bootdo.blog.service.ArticlesService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
* Created by GeneratorFx on 2017-04-11.
*/
@Service
@Transactional
    public class ArticlesServiceImpl implements ArticlesService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public List<Article> list(Map<String, Object> map){
        return articleMapper.list(map);
    }

    @Override
    public int counts(Map<String, Object> map){
        return articleMapper.counts(map);
    }

    public int saveArticle(Article article) {
       return   articleMapper.saveArticle(article);
    }

    public  int updateArticle(Article article) {
        return  articleMapper.updateArticle(article);
    }

    public int deleteArticle(Integer id){
        return  articleMapper.deleteArticle(id);
    }

    public int batchRemove(Long[] ids){
        return  articleMapper.batchRemove(ids);
    }

    public int updateStatue(Integer id, int status){
        return   articleMapper.updateStatue(id,status);
    }
}
