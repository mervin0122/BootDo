package com.bootdo.blog.service;


import com.bootdo.blog.domain.Article;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
* Created by GeneratorFx on 2017-04-11.
*/
public interface ArticlesService {

    List<Article> list(Map<String,Object> map);

    int counts(Map<String,Object> map);

    int saveArticle(Article article);

    int updateArticle(Article article);

    int deleteArticle(Integer id);

    int batchRemove(Long[] ids);

    int updateStatue(Integer id, int status);

}
