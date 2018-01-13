package com.bootdo.cms.controller;


import com.bootdo.cms.domain.CmsArticleDO;
import com.bootdo.cms.service.*;
import com.bootdo.shop.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
	 * 
	 * @author zs 2016-5-5 11:33:51
	 * @Email: 951449465@qq.com
	 * @version 4.0v
	 *	我的blog
	 */
@Controller
@RequestMapping("cms/web")
public class IndexCmsController {

	
		@Resource
		private CmsArticleService cmsArticleService;
		@Resource
		private CmsCategoryService cmsCategoryService;

		@Autowired
		private SiteService siteService;
		@Autowired
		private LinkService linkService;

		@Autowired
		private CommentService commentService;
		@Autowired
		private BannerService bannerService;
	/**
	 * 请求主页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/index")
	public ModelAndView index()throws Exception{
		ModelAndView mav=new ModelAndView();
		Map<String, Object> map = new HashMap<>();
		map.put("limit",4);
		map.put("model","show");
		List<CmsArticleDO> articles =  cmsArticleService.list(map);
		mav.addObject("articles",articles);
		map.put("model","news");
		map.put("offset",1);
		List<CmsArticleDO> news =  cmsArticleService.list(map);
		mav.addObject("news",news);
		map.clear();
		map.put("limit",8);
		mav.addObject("links",linkService.list(map));
		map.clear();
		map.put("limit", 3);
		map.put("storeid", 3);
		mav.addObject("bannerList", bannerService.list(map));
		mav.setViewName("cms/index");
		return mav;
	}

	/**
	 * 系统展示(客户案例)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/customer-case")
	public ModelAndView case1()throws Exception{
		ModelAndView mav=new ModelAndView();
		Map<String, Object> map = new HashMap<>();
		map.put("model","show");
		map.put("limit",8);
		List<CmsArticleDO> articles =  cmsArticleService.list(map);
		mav.addObject("articles",articles);
		map.clear();
		map.put("limit",10);
		mav.addObject("links",linkService.list(map));
		mav.setViewName("cms/customer-case");
		return mav;
	}
	/**
	 * 跳转到案例详情
	 */
	@RequestMapping("/goodsDetail/{id}")
	public String goodsDetail(@PathVariable Long id, Model model) {
     model.addAttribute("goods",cmsArticleService.get(id));
		return "cms/case-inform";
	}
	/**
	 * 产品展示
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/product")
	public ModelAndView product()throws Exception{
		ModelAndView mav=new ModelAndView();
		Map<String, Object> map = new HashMap<>();
		map.put("model","product");
		map.put("limit",8);
		List<CmsArticleDO> articles =  cmsArticleService.list(map);
		mav.addObject("articles",articles);

		mav.setViewName("cms/product-show");
		return mav;
	}
	/**
	 * 跳转到产品详情
	 */
	@RequestMapping("/productDetail/{id}")
	public String productDetail(@PathVariable Long id, Model model) {
		model.addAttribute("goods",cmsArticleService.get(id));
		return "cms/solutions-inform";
	}
	/**
	 * 文章分享（解决方案）
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/solutions")
	public ModelAndView solutions()throws Exception{
		ModelAndView mav=new ModelAndView();
		Map<String, Object> map = new HashMap<>();
		map.put("model","share");
		map.put("limit",8);
		map.put("typeid",1);//网站
		List<CmsArticleDO> articles =  cmsArticleService.list(map);
		mav.addObject("articles",articles);
		map.put("typeid",2);//app
		List<CmsArticleDO> articles1 =  cmsArticleService.list(map);
		mav.addObject("articles1",articles1);
		map.put("typeid",3);//wap
		List<CmsArticleDO> articles2 =  cmsArticleService.list(map);
		mav.addObject("articles2",articles2);
		map.put("typeid",4);//小程序
		List<CmsArticleDO> articles3 =  cmsArticleService.list(map);
		mav.addObject("articles3",articles3);
		mav.setViewName("cms/solutions");
		return mav;
	}
	/**
	 * 跳转到解决方案详情
	 */
	@RequestMapping("/solutionsDetail/{id}")
	public String solutionsDetail(@PathVariable Long id, Model model) {
		model.addAttribute("goods",cmsArticleService.get(id));
		return "cms/solutions-inform";
	}


	/**
	 * 技术框架(服务中心)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/service-center")
	public ModelAndView servicecenter()throws Exception{
		ModelAndView mav=new ModelAndView();
		Map<String, Object> map = new HashMap<>();
		map.put("model","framework");
		map.put("limit",8);
		List<CmsArticleDO> articles =  cmsArticleService.list(map);
		mav.addObject("articles",articles);

		mav.setViewName("cms/service-center");
		return mav;
	}

	/**
	 * 新闻动态
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/news")
	public ModelAndView news()throws Exception{
		ModelAndView mav=new ModelAndView();
		Map<String, Object> map = new HashMap<>();
		map.put("model","news");
		map.put("limit",8);
		List<CmsArticleDO> articles =  cmsArticleService.list(map);
		mav.addObject("articles",articles);

		mav.setViewName("cms/news");
		return mav;
	}
	/**
	 * 跳转到新闻详情
	 */
	@RequestMapping("/newsDetail/{id}")
	public String newsDetail(@PathVariable Long id, Model model) {
		model.addAttribute("goods",cmsArticleService.get(id));
		return "cms/news-inform";
	}

	/**
	 * 关于我们
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/aboutus")
	public ModelAndView aboutus()throws Exception{
		ModelAndView mav=new ModelAndView();
		Map<String, Object> map = new HashMap<>();
		map.put("model","aboutus");
		map.put("limit",1);
		List<CmsArticleDO> articles =  cmsArticleService.list(map);
		mav.addObject("articles",articles);

		mav.setViewName("cms/about-us");
		return mav;
	}
	/**
	 * 招聘
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/recruit")
	public ModelAndView recruit()throws Exception{
		ModelAndView mav=new ModelAndView();
		Map<String, Object> map = new HashMap<>();
		map.put("model","recruit");
		map.put("limit",8);
		List<CmsArticleDO> articles =  cmsArticleService.list(map);
		mav.addObject("articles",articles);

		mav.setViewName("cms/recruit");
		return mav;
	}
//	@RequestMapping("/index/ajax/bna")
//	public String ajaxindex(HttpServletRequest request) {
//		try {
//			String id = request.getParameter("order");
//			if (id != null && !id.equals("")) {
//				CmsArticle a=new CmsArticle();
//				PageInfo<CmsArticle> artList =null;
//				if("reco".equals(id)){
//					a.setWeight(1);
//					artList = CmsArticleService.selectPage(1, 40, a,"");
//				}else if("late".equals(id)){
//					artList = CmsArticleService.selectPage(1, 40, a,"createdate desc");
//				}else if("hot".equals(id)){
//					artList = CmsArticleService.selectPage(1, 40, a,"hits desc");
//				}
//
//				request.setAttribute("artList", artList.getList());
//			}
//		} catch (Exception e) {
//
//		}
//		return "pc/ajax-index";
//	}
	
	
}
