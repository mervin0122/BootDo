package com.bootdo.cms.controller;

import com.bootdo.cms.domain.CmsArticleDO;
import com.bootdo.cms.service.CmsArticleService;
import com.bootdo.cms.service.CmsCategoryService;
import com.bootdo.cms.service.SiteService;
import com.bootdo.common.annotation.Log;
import com.bootdo.common.controller.BaseController;
import com.bootdo.common.domain.DictDO;
import com.bootdo.common.service.DictService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zscat
 * @email 951449465@qq.com
 * @date 2017-12-02 18:07:28
 */
 
@Controller
@RequestMapping("/cms/article")
public class CmsArticleController extends BaseController {
	@Autowired
	private CmsArticleService cmsArticleService;
	@Autowired
	private CmsCategoryService cmsCategoryService;
	@Autowired
	private SiteService siteService;
	@Autowired
	private DictService sysDictService;
	
	@GetMapping()
	@RequiresPermissions("cms:article:article")
	String Article(){
	    return "cms/article/article";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("cms:article:articlelist")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<CmsArticleDO> articleList = cmsArticleService.list(query);
		int total = cmsArticleService.count(query);
		PageUtils pageUtils = new PageUtils(articleList, total);
		return pageUtils;
	}
	@Log("新增网站文章")
	@GetMapping("/add")
	@RequiresPermissions("cms:article:add")
	String add(Model model){
		model.addAttribute("typeList",cmsCategoryService.list(null));
		model.addAttribute("siteList",siteService.list(null));
	/*	List<DictDO> modelList = new ArrayList<>();
		DictDO dictDO = new DictDO("news","新闻模块");
		DictDO dictDO1 = new DictDO("case","客户案例");
		DictDO dictDO2 = new DictDO("product","产品模块");
		DictDO dictDO3 = new DictDO("solutions","解决方案模块");
		DictDO dictDO4 = new DictDO("service","服务中心模块");
		DictDO dictDO5 = new DictDO("recruit","招贤纳什");
		DictDO dictDO6 = new DictDO("aboutus","关于我们");
		modelList.add(dictDO);modelList.add(dictDO1);modelList.add(dictDO2);
		modelList.add(dictDO3);modelList.add(dictDO4);modelList.add(dictDO5);
		modelList.add(dictDO6);*/
		Map<String, Object> map = new HashMap<>();
		map.put("type", "model_type");
		List<DictDO> modelList = sysDictService.list(map);
		model.addAttribute("modelList",modelList);
		return "cms/article/add";
	}
	@Log("编辑网站文章")
	@GetMapping("/edit/{id}")
	@RequiresPermissions("cms:article:edit")
	String edit(@PathVariable("id") Long id,Model model){
		CmsArticleDO article = cmsArticleService.get(id);
		model.addAttribute("article", article);

		model.addAttribute("typeList",cmsCategoryService.list(null));
		model.addAttribute("siteList",siteService.list(null));
		Map<String, Object> map = new HashMap<>();
		map.put("type", "model_type");
		List<DictDO> modelList = sysDictService.list(map);
		model.addAttribute("modelList",modelList);
	    return "cms/article/edit";
	}
	
	/**
	 * 保存
	 */
	@Log("保存网站文章")
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("cms:article:add")
	public R save( CmsArticleDO article){
		if ("test".equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		article.setCreatedate(new Date());
		String imgMore = article.getImg();
		if (StringUtils.isEmpty(article.getRemark())) {
			article.setRemark(article.getTitle());
		}
		if(StringUtils.isNoneBlank(article.getImg1())){
			imgMore=imgMore+","+article.getImg1();
		}
		if(StringUtils.isNoneBlank(article.getImg2())){
			imgMore=imgMore+","+article.getImg2();
		}
		article.setMoreimage(imgMore);

		if(cmsArticleService.save(article)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@Log("更新网站文章")
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("cms:article:edit")
	public R update( CmsArticleDO article){
		if ("test".equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		cmsArticleService.update(article);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@Log("删除网站文章")
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("cms:article:remove")
	public R remove( Long id){
		if ("test".equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		if(cmsArticleService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 批量删除
	 */
	@Log("批量删除网站文章")
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("cms:article:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		if ("test".equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		cmsArticleService.batchRemove(ids);
		return R.ok();
	}
	
}
