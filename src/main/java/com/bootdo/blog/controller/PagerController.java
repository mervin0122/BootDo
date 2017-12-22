package com.bootdo.blog.controller;

import com.bootdo.blog.domain.ArticleCustom;
import com.bootdo.blog.service.CategoryService;
import com.bootdo.blog.service.PagerService;
import com.bootdo.blog.service.TagService;
import com.bootdo.blog.domain.Pager;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Do
 * @package com.bootdo.blog.controller.admin
 * @name PagerController
 * @date 2017/4/11
 * @time 21:44
 */
@RestController
@RequestMapping("/blog")
public class PagerController {

    @Resource
    private PagerService pagerService;
    @Resource
    private TagService tagService;
    @Resource
     CategoryService categoryService;
    /**
     * 初始化文章分页信息
     * @return
     */
    @RequestMapping("/pager/articles/load")
    public Pager loadArticlePager(Pager pager){
        pagerService.initPage(pager);
        return pager;
    }

    /**
     * 初始化时间的文章分页信息
     * @param pager 分页对象
     * @param createTime 分类id
     * @return
     */
    @RequestMapping("/pager/createTime/{createTime}")
    public Pager loadCreateTimesPager(Pager pager,@PathVariable String createTime){
        pagerService.loadArticleByArchive(createTime,pager);
        return pager;
    }
    /**
     * 初始化当前分类id的文章分页信息
     * @param pager 分页对象
     * @param categoryId 分类id
     * @return
     */
    @RequestMapping("/pager/categories/{categoryId}")
    public Pager loadCategoryPager(Pager pager,@PathVariable String categoryId){
        Integer id=Integer.parseInt(categoryId);
        pagerService.loadCategoryPager(pager,id);
        return pager;
    }

    /**
     *初始化当前标签的文章分页信息
     * @param pager 分页对象
     * @param tagId 标签
     * @return
     */
    @RequestMapping("/pager/tags/{tagId}")
    @ResponseBody
    public Pager initPage(Pager pager,@PathVariable Integer tagId){
        tagService.ArticleTagPage(pager,tagId);
        return pager;
    }

}
