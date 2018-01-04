package com.bootdo.blog.controller;


import com.bootdo.blog.domain.Article;
import com.bootdo.blog.domain.Partner;
import com.bootdo.blog.service.ArticlesService;
import com.bootdo.blog.service.BlogArticleService;
import com.bootdo.blog.service.CategoryService;
import com.bootdo.blog.service.PartnerService;
import com.bootdo.common.controller.BaseController;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by GeneratorFx on 2017-04-11.
 */
@Controller
@RequestMapping("/blog/partner")
public class PartnerController extends BaseController {

    @Resource
    private PartnerService partnerService;

    @Resource
    private ArticlesService articlesService;

    @Resource
    private BlogArticleService blogArticleService;

    @Resource
    CategoryService categoryService;

    @GetMapping()
    @RequiresPermissions("blog:partner:partner")
    String article() {
        return "blog/partner/partner";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("blog:partner:partner")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        List<Partner> partnerList = partnerService.findAll();
        //List<Article> articleList = articlesService.list(query);
        int total = partnerService.count();
        PageUtils pageUtils = new PageUtils(partnerList, total);
        return pageUtils;
    }
    @GetMapping("/add")
    @RequiresPermissions("blog:partner:add")
    String add(Model model) {
        model.addAttribute("categoriesList",categoryService.getCategoryList());
        return "blog/article/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("blog:partner:edit")
    String edit(@PathVariable("id") Integer id, Model model) {
        Article article = blogArticleService.getArticleById(id);
        model.addAttribute("article", article);
        return "blog/article/edit";
    }
    /**
     * 保存
     */
    @ResponseBody
    @RequiresPermissions("blog:partner:add")
    @PostMapping("/save")
    public R save(Article article) {
        if ("test".equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        if (article.getShowCount() == null) {
            article.setShowCount(0);
        }
        article.setCreateTime(new Date());
        article.setUpdateTime(new Date());
        int count;
        if (article.getId() == null || article.getId().equals("")) {
            count = articlesService.saveArticle(article);
        } else {
            count = articlesService.updateArticle(article);
        }
        if (count > 0) {
            return R.ok().put("id", article.getId());
        }
        return R.error();
    }

    /**
     * 修改
     */
    @RequiresPermissions("blog:partner:edit")
    @RequestMapping("/update")
    public R update(@RequestBody Article article) {
        if ("test".equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        article.setUpdateTime(new Date());
        articlesService.updateArticle(article);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequiresPermissions("blog:partner:remove")
    @PostMapping("/remove")
    @ResponseBody
    public R remove(Integer id) {
        if ("test".equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        if (articlesService.deleteArticle(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 批量删除
     */
    @RequiresPermissions("blog:partner:batchRemove")
    @PostMapping("/batchRemove")
    @ResponseBody
    public R remove(@RequestParam("ids[]") Long[] cids) {
        if ("test".equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        articlesService.batchRemove(cids);
        return R.ok();
    }


}
