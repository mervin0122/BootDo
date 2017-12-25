package com.bootdo.blog.controller;


import com.bootdo.blog.domain.Article;
import com.bootdo.blog.service.ArticlesService;
import com.bootdo.blog.service.BlogArticleService;
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
@RequestMapping("/blog/article")
public class ArticlesController extends BaseController {

    @Resource
    private ArticlesService articlesService;

    @Resource
    private BlogArticleService blogArticleService;



    @GetMapping()
    @RequiresPermissions("blog:article:article")
    String article() {
        return "blog/article/article";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("blog:article:article")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        List<Article> articleList = articlesService.list(query);
        int total = articlesService.counts(query);
        PageUtils pageUtils = new PageUtils(articleList, total);
        return pageUtils;
    }
    @GetMapping("/add")
    @RequiresPermissions("blog:article:add")
    String add() {
        return "blog/article/add";
    }

    @GetMapping("/edit/{cid}")
    @RequiresPermissions("blog:article:edit")
    String edit(@PathVariable("id") Integer id, Model model) {
        Article article = blogArticleService.getArticleById(id);
        model.addAttribute("article", article);
        return "blog/article/edit";
    }
    /**
     * 保存
     */
    @ResponseBody
    @RequiresPermissions("blog:article:add")
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
    @RequiresPermissions("blog:article:edit")
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
    @RequiresPermissions("blog:article:remove")
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
    @RequiresPermissions("blog:article:batchRemove")
    @PostMapping("/batchRemove")
    @ResponseBody
    public R remove(@RequestParam("ids[]") Long[] cids) {
        if ("test".equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        articlesService.batchRemove(cids);
        return R.ok();
    }
    /**
     * 修改状态
     */
    @RequiresPermissions("blog:article:status")
    @PostMapping("/status")
    @ResponseBody
    public R status(Integer id,int status) {
        if ("test".equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        if (articlesService.updateStatue(id,status) > 0) {
            return R.ok();
        }
        return R.error();
    }

}
