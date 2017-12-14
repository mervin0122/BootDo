package com.bootdo.cms.controller;

import java.util.List;
import java.util.Map;

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
public class CmsLinkController {
	@Autowired
	private LinkService linkService;
	
	@GetMapping()
	@RequiresPermissions("cms:link:link")
	String Link(){
	    return "cms/link/link";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("cms:link:link")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<LinkDO> linkList = linkService.list(query);
		int total = linkService.count(query);
		PageUtils pageUtils = new PageUtils(linkList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("cms:link:add")
	String add(){
	    return "cms/link/add";
	}

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
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("cms:link:add")
	public R save( LinkDO link){
		if(linkService.save(link)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("cms:link:edit")
	public R update( LinkDO link){
		linkService.update(link);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("cms:link:remove")
	public R remove( Long id){
		if(linkService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("cms:link:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		linkService.batchRemove(ids);
		return R.ok();
	}
	
}
