package com.bootdo.cms.controller;

import java.util.List;
import java.util.Map;

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

import com.bootdo.cms.domain.CommentDO;
import com.bootdo.cms.service.CommentService;
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
@RequestMapping("/cms/comment")
public class CmsCommentController extends BaseController {
	@Autowired
	private CommentService commentService;
	
	@GetMapping()
	@RequiresPermissions("cms:comment:comment")
	String Comment(){
	    return "cms/comment/comment";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("cms:comment:commentlist")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<CommentDO> commentList = commentService.list(query);
		int total = commentService.count(query);
		PageUtils pageUtils = new PageUtils(commentList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("cms:comment:add")
	String add(){
	    return "cms/comment/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("cms:comment:edit")
	String edit(@PathVariable("id") Long id,Model model){
		CommentDO comment = commentService.get(id);
		model.addAttribute("comment", comment);
	    return "cms/comment/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("cms:comment:add")
	public R save( CommentDO comment){
		if ("test".equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		if(commentService.save(comment)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("cms:comment:edit")
	public R update( CommentDO comment){
		if ("test".equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		commentService.update(comment);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("cms:comment:remove")
	public R remove( Long id){
		if ("test".equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		if(commentService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("cms:comment:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		if ("test".equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		commentService.batchRemove(ids);
		return R.ok();
	}
	
}
