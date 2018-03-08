package com.bootdo.blog.controller;

import com.bootdo.blog.domain.Category;
import com.bootdo.blog.domain.Tag;
import com.bootdo.blog.service.TagService;
import com.bootdo.blog.domain.ArticleCustom;
import com.bootdo.blog.domain.Pager;
import com.bootdo.common.annotation.Log;
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
 * @author Do
 * @package com.bootdo.blog.controller.admin
 * @name TagController
 * @date 2017/4/13
 * @time 18:54
 */
@Controller
@RequestMapping("/blog/tags")
public class TagController  extends BaseController {

   @Resource
    private TagService tagService;
    @GetMapping()
    @RequiresPermissions("blog:tag:tag")
    String category() {
        return "blog/tag/tag";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("blog:tag:taglist")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        List<Tag> partnerList = tagService.loadTagList(query);
        int total = tagService.count(params);
        PageUtils pageUtils = new PageUtils(partnerList, total);
        return pageUtils;
    }
    @Log("新增博客标签")
    @GetMapping("/add")
    @RequiresPermissions("blog:tag:add")
    String add() {
        return "blog/tag/add";
    }
    @Log("编辑博客标签")
    @GetMapping("/edit/{id}")
    @RequiresPermissions("blog:tag:edit")
    String edit(@PathVariable("id") Integer id, Model model) {
        Tag tag = tagService.getTagById(id);
        model.addAttribute("tag", tag);
        return "blog/tag/edit";
    }
    /**
     * 保存
     */
    @Log("保存博客标签")
    @ResponseBody
    @RequiresPermissions("blog:tag:add")
    @PostMapping("/save")
    public R save(Tag tag) {
        if ("test".equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }

        int count;
        if (tag.getId() == null || tag.getId().equals("")) {
            count = tagService.saveTag(tag);
        } else {
            count = tagService.updateTag(tag);
        }
        if (count > 0) {
            return R.ok().put("id", tag.getId());
        }
        return R.error();
    }

    /**
     * 修改
     */
    @Log("更新博客标签")
    @RequiresPermissions("blog:tag:edit")
    @RequestMapping("/update")
    public R update(@RequestBody Tag tag) {
        if ("test".equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        tagService.updateTag(tag);
        return R.ok();
    }

    /**
     * 删除
     */
    @Log("删除博客标签")
    @RequiresPermissions("blog:tag:remove")
    @PostMapping("/remove")
    @ResponseBody
    public R remove(Integer id) {
        if ("test".equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        if (tagService.deleteTag(id)> 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 批量删除
     */
    @Log("批量删除博客标签")
    @RequiresPermissions("blog:tag:batchRemove")
    @PostMapping("/batchRemove")
    @ResponseBody
    public R remove(@RequestParam("ids[]") Long[] cids) {
        if ("test".equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        tagService.batchRemove(cids);
        return R.ok();
    }
    /**
     * 通过tag获取文章列表
     * @param pager 分页信息
     * @param tagId 标签id
     * @param model 数据视图
     * @return
     */
   @RequestMapping("/load/{tagId}")
    public String loadArticleByTag(Pager pager, @PathVariable Integer tagId, Model model){
       List<ArticleCustom> articleList = tagService.loadArticleByTag(pager,tagId);
       if (!articleList.isEmpty()){
           model.addAttribute("articleList",articleList);
           model.addAttribute("pager",pager);
           //2017-05-07修复获取tag名称错误的问题,不应该从articlelist中取,因为每篇文章可能有多个tag
           model.addAttribute("tagName",tagService.getTagById(tagId).getTagName());
       }

       return "blog/part/tagSummary";
   }

}

