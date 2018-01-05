package com.bootdo.blog.controller;

import com.bootdo.blog.service.BlogArticleService;
import com.bootdo.blog.service.CategoryService;
import com.bootdo.blog.service.PartnerService;
import com.bootdo.blog.service.TagService;
import com.bootdo.blog.domain.ArticleCustom;
import com.bootdo.blog.domain.CategoryCustom;
import com.bootdo.blog.domain.Partner;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 首页入口controller
 * FILE: com.bootdo.blog.controller.IndexController.java
 * MOTTO:  不积跬步无以至千里,不积小流无以至千里
 * AUTHOR: EumJi
 * DATE: 2017/4/8
 * TIME: 15:19
 */
@Controller
@RequestMapping("/blog")
public class  PageController {

    @Resource
    private PartnerService partnerService;

    @Resource
    private BlogArticleService blogArticleService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private TagService tagService;

    /**
     * 首页
     * @param model
     * @return
     */
    @RequestMapping("")
    public String home(Model model){
        Map<String, Object> params=new HashedMap();
        params.put("status","1");
        List<Partner> partnerList = partnerService.findAll(params);
        List<CategoryCustom> categoryList = categoryService.initCategoryList();
        int articleCount = blogArticleService.getArticleCount();
        List<Map> archiveList = blogArticleService.articleArchiveList();
        int tagCount = tagService.getTagCount();
        model.addAttribute("categoryCount",categoryList.size());
        model.addAttribute("articleCount",articleCount);
        model.addAttribute("tagCount",tagCount);
        model.addAttribute("categoryList",categoryList);
        model.addAttribute("partnerList",partnerList);
        model.addAttribute("archiveList",archiveList);
        return "blog/blogindex";
    }

    /**
     * 分类排序 暂时停用
     * @return
     */
    @RequestMapping("/archives")
    @Deprecated
    public String archivesPage(){
        return "blog/archives";
    }

    @RequestMapping("/login")
    public String loginPage(){
        return "login";
    }

    /**跳转到友链展示页面
     * @return
     */
    @RequestMapping("/partner/list")
    public String partnerPage() {
        return "admin/partner/partnerList";
    }


    @RequestMapping("/about/me")
    public String aboutMe(Model model){
        Map<String, Object> params=new HashedMap();
        params.put("status","1");
        List<Partner> partnerList = partnerService.findAll(params);
        List<CategoryCustom> categoryList = categoryService.initCategoryList();
        int articleCount = blogArticleService.getArticleCount();
        int tagCount = tagService.getTagCount();
        model.addAttribute("categoryCount",categoryList.size());
        model.addAttribute("articleCount",articleCount);
        model.addAttribute("tagCount",tagCount);
        model.addAttribute("categoryList",categoryList);
        model.addAttribute("partnerList",partnerList);
        return "blog/aboutMe";
    }

    @RequestMapping("/popular")
    public String popularArticle(Model model){
        Map<String, Object> params=new HashedMap();
        params.put("status","1");
        List<Partner> partnerList = partnerService.findAll(params);
        List<CategoryCustom> categoryList = categoryService.initCategoryList();
        int articleCount = blogArticleService.getArticleCount();
        List<ArticleCustom> articleList = blogArticleService.popularArticle();
        int tagCount = tagService.getTagCount();

        model.addAttribute("categoryCount",categoryList.size());
        model.addAttribute("articleCount",articleCount);
        model.addAttribute("tagCount",tagCount);
        model.addAttribute("articleList",articleList);
        model.addAttribute("categoryList",categoryList);
        model.addAttribute("partnerList",partnerList);
        return "blog/popular";
    }

    @RequestMapping("/thymeleaf")
    public String thymeleafPage(){
        return "blog/thymeleaf";
    }
}
