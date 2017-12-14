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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootdo.cms.domain.CategoryDO;
import com.bootdo.cms.service.CmsCategoryService;
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
@RequestMapping("/cms/category")
public class CmsCategoryController {
	@Autowired
	private CmsCategoryService cmsCategoryService;
	
	@GetMapping()
	@RequiresPermissions("cms:category:category")
	String Category(){
	    return "cms/category/category";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("cms:category:category")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<CategoryDO> categoryList = cmsCategoryService.list(query);
		int total = cmsCategoryService.count(query);
		PageUtils pageUtils = new PageUtils(categoryList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("cms:category:add")
	String add(){
	    return "cms/category/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("cms:category:edit")
	String edit(@PathVariable("id") Long id,Model model){
		CategoryDO category = cmsCategoryService.get(id);
		model.addAttribute("category", category);
	    return "cms/category/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("cms:category:add")
	public R save( CategoryDO category){
		if(cmsCategoryService.save(category)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("cms:category:edit")
	public R update( CategoryDO category){
		cmsCategoryService.update(category);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("cms:category:remove")
	public R remove( Long id){
		if(cmsCategoryService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("cms:category:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		cmsCategoryService.batchRemove(ids);
		return R.ok();
	}
	
}
