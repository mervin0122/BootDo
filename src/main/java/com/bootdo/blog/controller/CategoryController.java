package com.bootdo.blog.controller;

import com.bootdo.blog.domain.Category;
import com.bootdo.blog.service.CategoryService;
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
@RequestMapping("/blog/category")
public class CategoryController extends BaseController {

    @Resource
    CategoryService categoryService;
    @GetMapping()
    @RequiresPermissions("blog:category:category")
    String category() {
        return "blog/category/category";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("blog:category:category")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        List<Category> partnerList = categoryService.getCategoryList(query);
        int total = categoryService.count(params);
        PageUtils pageUtils = new PageUtils(partnerList, total);
        return pageUtils;
    }
    @GetMapping("/add")
    @RequiresPermissions("blog:category:add")
    String add() {
        return "blog/category/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("blog:category:edit")
    String edit(@PathVariable("id") Integer id, Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "blog/category/edit";
    }
    /**
     * 保存
     */
    @ResponseBody
    @RequiresPermissions("blog:category:add")
    @PostMapping("/save")
    public R save(Category category) {
        if ("test".equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        category.setCreateTime(new Date());
        int count;
        if (category.getId() == null || category.getId().equals("")) {
            count = categoryService.saveCategory(category);
        } else {
            count = categoryService.updateCategory(category);
        }
        if (count > 0) {
            return R.ok().put("id", category.getId());
        }
        return R.error();
    }

    /**
     * 修改
     */
    @RequiresPermissions("blog:category:edit")
    @RequestMapping("/update")
    public R update(@RequestBody Category category) {
        if ("test".equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        categoryService.updateCategory(category);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequiresPermissions("blog:category:remove")
    @PostMapping("/remove")
    @ResponseBody
    public R remove(Integer id) {
        if ("test".equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        if (categoryService.deleteCategory(id)> 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 批量删除
     */
    @RequiresPermissions("blog:category:batchRemove")
    @PostMapping("/batchRemove")
    @ResponseBody
    public R remove(@RequestParam("ids[]") Long[] cids) {
        if ("test".equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        categoryService.batchRemove(cids);
        return R.ok();
    }
    /**
     * 修改状态
     */
    @RequiresPermissions("blog:category:status")
    @PostMapping("/status")
    @ResponseBody
    public R status(Integer id,int status) {
        if ("test".equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        if (categoryService.updateState(id,status) > 0) {
            return R.ok();
        }
        return R.error();
    }

}
