package com.bootdo.cms.controller;

import java.util.List;
import java.util.Map;

import com.bootdo.common.annotation.Log;
import com.bootdo.common.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootdo.cms.domain.SiteDO;
import com.bootdo.cms.service.SiteService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 
 * 
 * @author zscat
 * @email 951449465@qq.com
 * @date 2017-12-02 18:07:28
 */
 
@Controller
@RequestMapping("/cms/site")
public class CmsSiteController extends BaseController {
	@Autowired
	private SiteService siteService;
	
	@GetMapping()
	@RequiresPermissions("cms:site:site")
	String Site(){
	    return "cms/site/site";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("cms:site:sitelist")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<SiteDO> siteList = siteService.list(query);
		int total = siteService.count(query);
		PageUtils pageUtils = new PageUtils(siteList, total);
		return pageUtils;
	}
	@Log("新增网站文章")
	@GetMapping("/add")
	@RequiresPermissions("cms:site:add")
	String add(){
	    return "cms/site/add";
	}
	@Log("编辑网站文章")
	@GetMapping("/edit/{id}")
	@RequiresPermissions("cms:site:edit")
	String edit(@PathVariable("id") Long id,Model model){
		SiteDO site = siteService.get(id);
		model.addAttribute("site", site);
	    return "cms/site/edit";
	}
	
	/**
	 * 保存
	 */
	@Log("保存网站站点")
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("cms:site:add")
	public R save( SiteDO site){
		if ("test".equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		if(siteService.save(site)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@Log("更新网站站点")
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("cms:site:edit")
	public R update( SiteDO site){
		if ("test".equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		siteService.update(site);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@Log("删除网站站点")
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("cms:site:remove")
	public R remove( Long id){
		if ("test".equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		if(siteService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 批量删除
	 */
	@Log("批量删除网站站点")
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("cms:site:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		if ("test".equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		siteService.batchRemove(ids);
		return R.ok();
	}
	
}
