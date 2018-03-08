package com.bootdo.blog.controller;


import com.bootdo.blog.domain.Article;
import com.bootdo.blog.domain.Partner;
import com.bootdo.blog.service.ArticlesService;
import com.bootdo.blog.service.BlogArticleService;
import com.bootdo.blog.service.CategoryService;
import com.bootdo.blog.service.PartnerService;
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
 * Created by GeneratorFx on 2017-04-11.
 */
@Controller
@RequestMapping("/blog/partner")
public class PartnerController extends BaseController {

    @Resource
    private PartnerService partnerService;


    @GetMapping()
    @RequiresPermissions("blog:partner:partner")
    String article() {
        return "blog/partner/partner";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("blog:partner:partnerlist")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        List<Partner> partnerList = partnerService.findAll(query);
        int total = partnerService.count(params);
        PageUtils pageUtils = new PageUtils(partnerList, total);
        return pageUtils;
    }
    @Log("新增博客友情链接")
    @GetMapping("/add")
    @RequiresPermissions("blog:partner:add")
    String add() {
        return "blog/partner/add";
    }

    @Log("编辑博客友情链接")
    @GetMapping("/edit/{id}")
    @RequiresPermissions("blog:partner:edit")
    String edit(@PathVariable("id") Integer id, Model model) {
        Partner partner = partnerService.getPartnerById(id);
        model.addAttribute("partner", partner);
        return "blog/partner/edit";
    }
    /**
     * 保存
     */
    @Log("保存博客友情链接")
    @ResponseBody
    @RequiresPermissions("blog:partner:add")
    @PostMapping("/save")
    public R save(Partner partner) {
        if ("test".equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        partner.setCreateTime(new Date());
        int count;
        if (partner.getId() == null || partner.getId().equals("")) {
            count = partnerService.savePartner(partner);
        } else {
            count = partnerService.updatePartner(partner);
        }
        if (count > 0) {
            return R.ok().put("id", partner.getId());
        }
        return R.error();
    }

    /**
     * 修改
     */
    @Log("更新博客友情链接")
    @RequiresPermissions("blog:partner:edit")
    @RequestMapping("/update")
    public R update(@RequestBody Partner partner) {
        if ("test".equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        partnerService.updatePartner(partner);
        return R.ok();
    }

    /**
     * 删除
     */
    @Log("删除博客友情链接")
    @RequiresPermissions("blog:partner:remove")
    @PostMapping("/remove")
    @ResponseBody
    public R remove(Integer id) {
        if ("test".equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        if (partnerService.deletePartner(id)> 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 批量删除
     */
    @Log("批量删除博客友情链接")
    @RequiresPermissions("blog:partner:batchRemove")
    @PostMapping("/batchRemove")
    @ResponseBody
    public R remove(@RequestParam("ids[]") Long[] cids) {
        if ("test".equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        partnerService.batchRemove(cids);
        return R.ok();
    }
    /**
     * 修改状态
     */
    @Log("修改博客友情链接状态")
    @RequiresPermissions("blog:partner:status")
    @PostMapping("/status")
    @ResponseBody
    public R status(Integer id,int status) {
        if ("test".equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        if (partnerService.updateState(id,status) > 0) {
            return R.ok();
        }
        return R.error();
    }

}
