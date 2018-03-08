package com.bootdo.blog.controller;


import com.bootdo.blog.service.BlogArticleService;
import com.bootdo.blog.service.CategoryService;
import com.bootdo.blog.service.PartnerService;
import com.bootdo.blog.service.TagService;

import com.bootdo.blog.domain.*;
import com.bootdo.utils.ResultInfo;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/blog/article")
public class BlogArticleController {

    @Resource
    private BlogArticleService blogArticleService;

    @Resource
    private PartnerService partnerService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private TagService tagService;

    @RequestMapping("/list/{id}")
    @ResponseBody
    public ResultInfo articleList(Pager pager){

        return null;
    }

    @RequestMapping("/load")
    public String loadArticle(Pager<Article> pager, Model model){
        List<ArticleCustom> articleList = blogArticleService.articleList(pager);
        model.addAttribute("articleList",articleList);
        return "blog/part/articleSummary";
    }

    /**发布后的文章才显示
     * 加载文章
     * 包括总标签数
     * 总文章数量
     * 分类及每个分类文章数量
     * 友链集合
     *
     * @return
     */
    @RequestMapping("/details/{articleId}")
    public String loadArticle(@PathVariable Integer articleId, Model model){
        Map<String, Object> params=new HashedMap();
        params.put("status","1");
        List<Partner> partnerList = partnerService.findAll(params);
        List<CategoryCustom> categoryList = categoryService.initCategoryList();
        Article lastArticle = blogArticleService.getLastArticle(articleId);
        Article nextArticle = blogArticleService.getNextArticle(articleId);
        blogArticleService.addArticleCount(articleId);
        int articleCount = blogArticleService.getArticleCount();
        int tagCount = tagService.getTagCount();
        ArticleCustom articleCustom = blogArticleService.getArticleCustomById(articleId);//发布后的文章才显示
        model.addAttribute("lastArticle",lastArticle);
        model.addAttribute("nextArticle",nextArticle);
        model.addAttribute("article",articleCustom);
        model.addAttribute("categoryCount",categoryList.size());
        model.addAttribute("articleCount",articleCount);
        model.addAttribute("tagCount",tagCount);
        model.addAttribute("categoryList",categoryList);
        model.addAttribute("partnerList",partnerList);
        return "blog/article";
    }
    /**未发布和发布后的文章都显示
     * 加载文章
     * 包括总标签数
     * 总文章数量
     * 分类及每个分类文章数量
     * 友链集合
     *
     * @return
     */
    @RequestMapping("/detail/{articleId}")
    public String loadAArticle(@PathVariable Integer articleId, Model model){
        Map<String, Object> params=new HashedMap();
        params.put("status","1");
        List<Partner> partnerList = partnerService.findAll(params);
        List<CategoryCustom> categoryList = categoryService.initCategoryList();
        Article lastArticle = blogArticleService.getLastArticle(articleId);
        Article nextArticle = blogArticleService.getNextArticle(articleId);
        blogArticleService.addArticleCount(articleId);
        int articleCount = blogArticleService.getArticleCount();
        int tagCount = tagService.getTagCount();
        ArticleCustom articleCustom = blogArticleService.getAArticleCustomById(articleId);//未发布和发布后的文章都显示
        model.addAttribute("lastArticle",lastArticle);
        model.addAttribute("nextArticle",nextArticle);
        model.addAttribute("article",articleCustom);
        model.addAttribute("categoryCount",categoryList.size());
        model.addAttribute("articleCount",articleCount);
        model.addAttribute("tagCount",tagCount);
        model.addAttribute("categoryList",categoryList);
        model.addAttribute("partnerList",partnerList);
        return "blog/article";
    }

    @RequestMapping("/content/search")
    public String search(String keyword,Model model){
        List<Article> articleList = blogArticleService.getArticleListByKeywords(keyword);
        model.addAttribute("articleList",articleList);
        return "blog/part/search-info";
    }


}
