package com.bootdo.cms.controller;

import java.util.Date;
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

import com.bootdo.cms.domain.LinkDO;
import com.bootdo.cms.service.LinkService;
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
@RequestMapping("/cms/link")
public class CmsLinkController extends BaseController {
	@Autowired
	private LinkService linkService;
	
	@GetMapping()
	@RequiresPermissions("cms:link:link")
	String Link(){
	    return "cms/link/link";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("cms:link:linklist")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<LinkDO> linkList = linkService.list(query);
		int total = linkService.count(query);
		PageUtils pageUtils = new PageUtils(linkList, total);
		return pageUtils;
	}
	@Log("新增网站文章")
	@GetMapping("/add")
	@RequiresPermissions("cms:link:add")
	String add(){
	    return "cms/link/add";
	}
	@Log("编辑网站文章")
	@GetMapping("/edit/{id}")
	@RequiresPermissions("cms:link:edit")
	String edit(@PathVariable("id") Long id,Model model){
		LinkDO link = linkService.get(id);
		model.addAttribute("link", link);
	    return "cms/link/edit";
	}
	
	/**
	 * 保存
	 */
	@Log("保存网站友链")
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("cms:link:add")
	public R save( LinkDO link){
		if ("test".equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		link.setWeightdate(new Date());
		if(linkService.save(link)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@Log("更新网站友链")
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("cms:link:edit")
	public R update( LinkDO link){
		if ("test".equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		linkService.update(link);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@Log("删除网站友链")
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("cms:link:remove")
	public R remove( Long id){
		if ("test".equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		if(linkService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 批量删除
	 */
	@Log("批量删除网站友链")
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("cms:link:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		if ("test".equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		linkService.batchRemove(ids);
		return R.ok();
	}
	
}
