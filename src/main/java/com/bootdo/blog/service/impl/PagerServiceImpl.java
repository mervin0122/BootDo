package com.bootdo.blog.service.impl;


import com.bootdo.blog.dao.ArticleMapper;
import com.bootdo.blog.dao.PagerMapper;
import com.bootdo.blog.domain.ArticleCustom;
import com.bootdo.blog.service.PagerService;
import com.bootdo.blog.domain.Pager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Do
 * @package com.bootdo.blog.service.impl
 * @name PagerServiceImpl
 * @date 2017/4/11
 * @time 21:46
 */
@Service
public class PagerServiceImpl implements PagerService {

    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private PagerMapper pagerMapper;
    @Override
    public void initPage(Pager pager) {
        int count = articleMapper.getArticleCount();
        pager.setTotalCount(count);
    }

    @Override
    public void loadCategoryPager(Pager pager,Integer categoryId) {
        int count = pagerMapper.loadCategoryPager(categoryId);
        pager.setTotalCount(count);
    }

    @Override
    public void loadTagPager(Pager pager, Integer tagId) {
        int count = pagerMapper.loadTagPager(tagId);
        pager.setTotalCount(count);
    }

    @Override
    public void loadArticleByArchive(String createTime, Pager pager) {
        int count= articleMapper.loadArticleByArchive(pager,createTime).size();
        pager.setTotalCount(count);
    }
}
