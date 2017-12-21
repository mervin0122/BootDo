package com.bootdo.web;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.bootdo.common.utils.MD5Utils;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.lucene.IndexObject;
import com.bootdo.shop.Constan;
import com.bootdo.shop.domain.ArticleDO;
import com.bootdo.shop.domain.CouponDO;
import com.bootdo.shop.domain.FavoriteDO;
import com.bootdo.shop.domain.JifendataDO;
import com.bootdo.shop.domain.JifengoodsDO;
import com.bootdo.shop.domain.TFloorDO;
import com.bootdo.shop.domain.TGoodsClassDO;
import com.bootdo.shop.domain.TGoodsDO;
import com.bootdo.shop.domain.TStoreDO;
import com.bootdo.shop.service.AddressService;
import com.bootdo.shop.service.ArticleService;
import com.bootdo.shop.service.BannerService;
import com.bootdo.shop.service.CouponService;
import com.bootdo.shop.service.FavoriteService;
import com.bootdo.shop.service.JifendataService;
import com.bootdo.shop.service.JifengoodsService;
import com.bootdo.shop.service.LuceneService;
import com.bootdo.shop.service.TBrandService;
import com.bootdo.shop.service.TCartService;
import com.bootdo.shop.service.TFloorService;
import com.bootdo.shop.service.TGoodSorderService;
import com.bootdo.shop.service.TGoodsClassService;
import com.bootdo.shop.service.TGoodsService;
import com.bootdo.shop.service.TGoodsTypeService;
import com.bootdo.shop.service.TMemberService;
import com.bootdo.shop.service.TOrderService;
import com.bootdo.shop.service.TReplyService;
import com.bootdo.shop.service.TStoreService;
import com.bootdo.shop.service.TopicService;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.service.UserService;
import com.bootdo.utils.CookieUtil;
import com.bootdo.wap.MemberUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.DateUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author stylefeng
 * @since 2017-05-17
 */
@Controller
@RequestMapping("diaShop")
public class DiaShopController {
    private String PREFIX = "/diaShop/";
    String path ="F:/zscat-b2b2c/";
    @Autowired
    private ArticleService articleService;
    @Autowired
    private TBrandService tBrandService;
    @Autowired
    private TGoodsTypeService tGoodsTypeService;
    @Autowired
    private TGoodsService tGoodsService;
    @Autowired
    private TGoodsClassService tGoodsClassService;
    @Autowired
    private TStoreService tStoreService;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private TCartService tCartService;
    @Autowired
    private TReplyService tReplyService;
    @Autowired
    private TMemberService tMemberService;
    @Autowired
    private WxMaService wxService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private TOrderService orderService;
    @Autowired
    private TGoodSorderService tGoodSorderService;
    @Autowired
    private TFloorService tFloorService;
    @Autowired
    private JifendataService jifendataService;
    @Autowired
    private UserService userService;
    @Autowired
    private JifengoodsService jifengoodsService;
    @Autowired
    private LuceneService luceneService;
    /**
     * 获取用户信息
     * @param
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/session", method = RequestMethod.GET)
    public @ResponseBody
    R session(HttpServletRequest req) throws Exception {
        R r=new R();
        UserDO user=MemberUtils.getSessionLoginUser();
        try {
            if(user!=null){
                r.put("user",user);
                r.put("username",user.getUsername());
                r.put("cartCount",tCartService.selectOwnCart(user.getUserId()));
            }else{
                return R.error();
            }

        }
        catch (Exception e){
            e.printStackTrace();
            return R.error();
        }
        return r;
    }
    /**
     * 跳转到首页
     */
    @RequestMapping("index")
    public String index(Model model) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("pid",1);
            params.put("limit",6);
            Query query =new Query(params);
            List<TGoodsClassDO> goodsClassList = tGoodsClassService.list(query);
            for(TGoodsClassDO gc: goodsClassList){
                query =new Query(params);
                query.put("pid",gc.getId());
                query.put("limit",4);
                List<TGoodsClassDO> childs = tGoodsClassService.list(query);
                if(childs!=null && childs.size()>0){
                    gc.setChilds(childs);
                }else{
                    gc.setChilds(null);
                }
                for(TGoodsClassDO gc1: childs){
                    query =new Query(params);
                    query.put("pid",gc1.getId());
                    query.put("limit",15);
                    List<TGoodsClassDO> childs1 = tGoodsClassService.list(query);
                    if(childs!=null && childs.size()>0){
                        gc1.setChilds(childs1);
                    }else{
                        gc1.setChilds(null);
                    }
                }
            }
            model.addAttribute("goodsClassList", goodsClassList);
            Map<String, Object> params1 = new HashMap<>();
            params = new Query(params1);
            params.put("limit", 2);
            params.put("iscom","1");
            model.addAttribute("commList", tGoodsService.list1(params));
            params = new Query(params1);
            params.put("limit", 6);
            params.put("sort","clickHit");
            params.put("order","desc");
            model.addAttribute("hitList", tGoodsService.list1(params));
            params = new Query(params1);
            params.put("limit", 8);
            params.put("sort","create_date");
            params.put("order","desc");
            model.addAttribute("xinpinList", tGoodsService.list1(params));

            params = new Query(params1);
            params.put("limit", 16);
            model.addAttribute("brandList", tBrandService.list(params));

            params = new Query(params1);
            params.put("limit", 5);
            params.put("storeid", 4);
            model.addAttribute("bannerList", bannerService.list(params));

            List<TFloorDO> floorList = tFloorService.list(null);
            int i=0;
            for (TFloorDO floorDO:floorList){
                List<TGoodsDO> goodsList = tGoodsService.getProductByFloorid(floorDO.getId());
                floorDO.setGoodsList(goodsList);
                floorDO.setBrandList(tBrandService.getBrandByFloorid(floorDO.getId()));
                params = new Query(params1);
                params.put("limit", 8);
                params.put("pid", floorDO.getParentId());
                floorDO.setGoodsClassList(tGoodsClassService.list(params));
                if(i==0){
                    params = new Query(params1);
                    params.put("limit", 4);
                    params.put("sort","create_date");
                    params.put("order","desc");
                    floorDO.setGoodsList1(tGoodsService.list1(params));
                    floorDO.setParentIds("最新商品");
                }else if(i==1){
                    params = new Query(params1);
                    params.put("limit", 4);
                    params.put("sort","clickHit");
                    params.put("order","desc");
                    floorDO.setParentIds("最爱商品");
                    floorDO.setGoodsList1(tGoodsService.list1(params));
                }else if(i==2){
                    params = new Query(params1);
                    params.put("sort","sellhit");
                    params.put("order","desc");
                    params.put("limit", 4);
                    floorDO.setParentIds("热卖商品");
                    floorDO.setGoodsList1(tGoodsService.list1(params));
                }else if(i==3){
                    params = new Query(params1);
                    params.put("sort","replyhit");
                    params.put("order","desc");
                    params.put("limit", 4);
                    floorDO.setParentIds("争议商品");
                    floorDO.setGoodsList1(tGoodsService.list1(params));
                }else if(i==4){
                    params = new Query(params1);
                    params.put("limit", 4);
                    params.put("iscom","1");
                    floorDO.setParentIds("推荐商品");
                    floorDO.setGoodsList1(tGoodsService.list1(params));
                }
                i++;
            }
            model.addAttribute("floorList", floorList);
            params = new Query(params1);
            params.put("limit", 8);
            List<ArticleDO> tArticleList = articleService.list(params);
            model.addAttribute("tArticleList", tArticleList);
            model.addAttribute("home", "1");
        }catch (Exception e){
            e.printStackTrace();
        }
        return PREFIX + "index";
    }

    /**
     * 跳转到搜索
     */
    @RequestMapping("search")
    public String search(Model model,HttpServletRequest req) {
        try {
            String keyword =req.getParameter("keyword");
            PageUtils data = luceneService.page(1,15,keyword);
            model.addAttribute("data",data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return PREFIX + "search";
    }

    /**
     * 跳转到商品详情
     */
    @RequestMapping("/goodsDetail/{id}")
    public String goodsDetail(@PathVariable Long id, Model model, HttpServletRequest request, HttpServletResponse resp) {
        TGoodsDO goods = tGoodsService.get(id);
        UserDO userDO =MemberUtils.getSessionLoginUser();
        if (userDO!=null){
            Map<String, Object> params = new HashMap<>();
            params.put("userid",userDO.getUserId());
            params.put("goodsid",id);
            params.put("deletestatus",2);
            params.put("type",1);
            FavoriteDO favoriteDO = favoriteService.selectOne(params);
            if(favoriteDO!=null){
                goods.setIs_favorite(1);
            }else{
                goods.setIs_favorite(2);
            }
        }else{
            goods.setIs_favorite(2);
        }

        model.addAttribute("goods",goods);
        if(goods.getImgmore()!=null && goods.getImgmore().indexOf(",")>-1){
            model.addAttribute("imgs", goods.getImgmore().split(","));
        }
        goods.setClickhit(goods.getClickhit()+1);
        tGoodsService.update(goods);
        Map<String, Object> params1 = new HashMap<>();
        Query params = new Query(params1);
        params.put("limit", 5);
        params.put("iscom","1");
        model.addAttribute("commList", tGoodsService.list1(params));

//        params = new Query(params1);
//        params.put("limit", 6);
//        params.put("brandid",goods.getBrandid());
//        model.addAttribute("brandList", tGoodsService.list1(params));

        params = new Query(params1);
        params.put("limit", 4);
        params.put("typeid",goods.getTypeid());
        model.addAttribute("typeList", tGoodsService.list1(params));

        params = new Query(params1);
        params.put("limit", 6);
        params.put("classid",goods.getClassid());
        model.addAttribute("classList", tGoodsService.list1(params));
        Cookie[] cookies = request.getCookies();
        String result=null;
        List<TGoodsDO> viewGoodslist = new ArrayList<>();
        if(cookies!=null){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("recorder")){
                    result = cookie.getValue();
                    String [] pids = result.split("-");
                    //其实将Service换成单例模式比较好
                    viewGoodslist = tGoodsService.getProductListByPids(pids);
                    model.addAttribute("viewGoodslist", viewGoodslist);
                    break;
                }
            }
        }
        CookieUtil.addGoodsToCookie(request,resp,id);
//        params = new Query(params1);
//        params.put("limit", 60);
//        params.put("goodsid",goods.getId());
//        model.addAttribute("replyList", tReplyService.list(params));
        return PREFIX + "goodsDetail";
    }

    /**
     * 跳转到积分商城
     */
    @RequestMapping("jfindex")
    public String jifen(Model model) {
        try {
            Map<String, Object> params = new HashMap<>();

            Map<String, Object> params1 = new HashMap<>();
            params = new Query(params1);
            params.put("limit", 8);

            model.addAttribute("jfList", jifengoodsService.list(params));
            params = new Query(params1);
            params.put("limit", 8);
            params.put("sort","create_date");
            params.put("order","desc");
            model.addAttribute("xinpinList", tGoodsService.list1(params));
            params = new Query(params1);
            params.put("limit", 4);
            params.put("iscom","1");
            model.addAttribute("commList", tGoodsService.list1(params));

            params = new Query(params1);
            params.put("limit", 16);
            model.addAttribute("brandList", tBrandService.list(params));

            params = new Query(params1);
            params.put("limit", 3);
            params.put("storeid", 2);
            model.addAttribute("bannerList", bannerService.list(params));
        }catch (Exception e){
            e.printStackTrace();
        }
        return PREFIX + "jfindex";
    }

    /**
     * 跳转到商品详情
     */
    @RequestMapping("/jfDetail/{id}")
    public String jfDetail(@PathVariable Long id, Model model) {
        JifengoodsDO goods = jifengoodsService.get(id);
        model.addAttribute("goods",goods);

        Map<String, Object> params1 = new HashMap<>();
        Query params = new Query(params1);
        params.put("limit", 4);
        params.put("iscom","1");
        model.addAttribute("commList", tGoodsService.list1(params));

        params = new Query(params1);
        params.put("limit", 6);
        params.put("typeid",goods.getTypeid());
        model.addAttribute("typeList", tGoodsService.list1(params));

        params = new Query(params1);
        params.put("limit", 6);
        params.put("classid",goods.getClassid());
        model.addAttribute("classList", tGoodsService.list1(params));

        return PREFIX + "jfDetail";
    }
    /**
     * 跳转到优惠劵
     */
    @RequestMapping("youhuijuan")
    public String youhuijuan(Model model) {
        try {
            UserDO user = ShiroUtils.getUser();
            if(user!=null){
                model.addAttribute("username", user.getUsername());
                model.addAttribute("cartCount", tCartService.selectOwnCart(user.getUserId()));
            }
            Map<String, Object> params = new HashMap<>();

            Map<String, Object> params1 = new HashMap<>();
            params = new Query(params1);
            params.put("limit", 8);
            List<CouponDO> couponList = couponService.list(params);
            model.addAttribute("couponList", couponList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return PREFIX + "youhuijuan";
    }
    /**
     * 跳转到文章详情
     */
    @RequestMapping("/articleDetail/{id}")
    public String articleDetail(@PathVariable Long id, Model model) {
        ArticleDO article = articleService.get(id);
        model.addAttribute("article",article);
        Map<String, Object> params1 = new HashMap<>();
        Query params = new Query(params1);
        params.put("limit", 4);
        params.put("iscom","1");
        model.addAttribute("commList", tGoodsService.list1(params));
        return PREFIX + "article";
    }
    /**
     * 跳转到店铺详情
     */
    @RequestMapping("/store/{id}")
    public String store(@PathVariable Long id, Model model) {
        TStoreDO store = tStoreService.get(id);
        model.addAttribute("store",store);

        return PREFIX + "store";
    }

    /**
     * 根据品牌查询商品
     */
    @RequestMapping("/brand/{id}")
    public String brand(@PathVariable Long id, Model model) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("brandid",id);
            params.put("limit",15);
            Query query =new Query(params);
            model.addAttribute("brandGoods", tGoodsService.list1(query));
        }catch (Exception e){
            e.printStackTrace();
        }
        return PREFIX + "list";
    }

    /**
     * 通过菜单类别
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping("/goodsListBygcId/{gcId}")
    public ModelAndView goodsListBygcId(@PathVariable("gcId") Long gcId)throws Exception{
        ModelAndView mav=new ModelAndView();
        TGoodsDO g=new TGoodsDO();
        //g.setGcId(gcId);
//        PageInfo<TGoodsDO> page=tGoodsService.selectgoodsListByType(1, 40, g);
//        mav.addObject("page", page);
        mav.setViewName("mall/search");
        return mav;
    }












    /**
     * 跳转到登录页面
     *
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String toLogin() {
        if( MemberUtils.getSessionLoginUser() != null){
            return "redirect:/diaShop/index";
        }
        return PREFIX + "login";
    }

    /**
     * 登录验证
     * @param
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    R ajaxLogin(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        password = MD5Utils.encrypt(username, password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            jifendataService.save(Constan.LOGIN);
            return R.ok();
        } catch (AuthenticationException e) {
            return R.error("用户或密码错误");
        }
    }

    /**
     * 跳转到登录页面
     *
     * @return
     */
    @RequestMapping(value = "reg", method = RequestMethod.GET)
    public String reg() {
        if( MemberUtils.getSessionLoginUser() != null){
            return "redirect:/diaShop/index";
        }
        return PREFIX + "zhuce";
    }

    @RequestMapping(value = "reg", method = RequestMethod.POST)
    public @ResponseBody R reg(
            @RequestParam(value = "password",required=true)String  password,
            @RequestParam(value = "username",required=true)String username,
            @RequestParam(value = "passwordRepeat",required=true)String passwordRepeat,HttpServletRequest request) {
        Map<String, Object> msg = new HashMap<String, Object>();
        if (!StringUtils.equalsIgnoreCase(password, passwordRepeat)) {
            return R.error("密码不一致！");
        }
        String secPwd = null ;

        UserDO m=new UserDO();
        m.setUsername(username);
        m.setName(username);
        secPwd = MD5Utils.encrypt(password, username);
        m.setStatus(1);
        m.setJifen(Constan.REGISTER.getCode());
        m.setPassword(secPwd);
        m.setGmtCreate(new Date());
        m.setBalance(1000);//余额
        try {
            int result = userService.saveOnly(m);
            if (result>0) {
                JifendataDO jifendata = new JifendataDO();
                jifendata.setAddcount(Constan.REGISTER.getCode());
                jifendata.setSource(Constan.REGISTER.getMsg());
                jifendata.setCreatedate(new Date());
                jifendata.setUserid(m.getUserId());
                jifendataService.save(jifendata);
            } else {
                return R.error("用户或密码错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok();
    }
    /**
     * 用户退出
     *
     * @return 跳转到登录页面
     */
    @RequestMapping("logout")
    public String logout(HttpServletRequest request) {
        ShiroUtils.logout();
        return "redirect:/diaShop/index";
    }

    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFileHandlerStore(@RequestParam("files") MultipartFile file,
                                         HttpServletRequest request) throws IOException {
        return fileUpload(file, request,"upload/store/");
    }


    @RequestMapping(value = "/fileUpload1", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFileHandlerGoods(@RequestParam("files") MultipartFile file,
                                         HttpServletRequest request) throws IOException {
        return fileUpload(file, request,"upload/project");
    }

    private String fileUpload(@RequestParam("files") MultipartFile file, HttpServletRequest request, String path) throws IOException {
        if (!file.isEmpty()) {
            InputStream in = null;
            OutputStream out = null;
            try {
                // String filePath = request.getSession().getServletContext().getRealPath("/") +path1 ;
                String pictureSaveFilePath =request.getRealPath(path);
                String filePath = pictureSaveFilePath ;
                File dir = new File(filePath);
                if (!dir.exists())
                    dir.mkdirs();
                File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
                in = file.getInputStream();
                out = new FileOutputStream(serverFile);
                byte[] b = new byte[1024];
                int len = 0;
                while ((len = in.read(b)) > 0) {
                    out.write(b, 0, len);
                }
                System.out.println("file:"+serverFile);
                out.close();
                in.close();
                return file.getOriginalFilename();

            } catch (Exception e) {
                return file.getOriginalFilename();
            } finally {
                if (out != null) {
                    out.close();
                    out = null;
                }

                if (in != null) {
                    in.close();
                    in = null;
                }
            }
        } else {
            return file.getOriginalFilename();
        }
    }
    @RequestMapping("/init")
    public ModelAndView init(HttpServletRequest request)throws Exception{
        luceneService.delete(null);
        ModelAndView mav=new ModelAndView();
        try {
            List<TGoodsDO> l =tGoodsService.list(null);
            for (TGoodsDO content : l) {
                IndexObject indexObject = new IndexObject();
                indexObject.setId(content.getId());
                indexObject.setTitle(content.getTitle());
                indexObject.setKeywords(content.getTitle());
                indexObject.setDescripton(content.getRemark());
                indexObject.setPostDate(DateUtils.formatDate(content.getCreateDate()));
                indexObject.setUrl(content.getTitle());
                indexObject.setCol1(content.getPrices());
                indexObject.setCol2(content.getImg());
                //   indexObject.setUrl(this.httpProtocol + "://" + ControllerUtil.getDomain() + "/front/" + content.getSiteId() + "/" + content.getCategoryId() + "/" + content.getContentId());
                luceneService.save(indexObject);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try {

            mav.setViewName("diaShop/search");
        }catch (Exception e){
            e.printStackTrace();
        }
        return mav;
    }
    /**
     * 通过菜单类别
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping("/goodsList")
    public ModelAndView goodsList(HttpServletRequest request, HttpServletResponse resp)throws Exception{
        ModelAndView mav=new ModelAndView();

        Map<String, Object> params1 = new HashMap<>();
        params1.put("limit", 8);
        String offset = request.getParameter("offset");
        if(StringUtils.isNotEmpty(offset)){
            params1.put("offset", offset);
        }
        Query query = new Query(params1);
        List<TGoodsDO> tGoodsList = tGoodsService.list1(query);
        int total = tGoodsService.count(query);
        PageUtils pageUtils = new PageUtils(tGoodsList, total);
        mav.addObject("pageUtils", pageUtils);
        mav.addObject("typeList", tGoodsTypeService.list(null));
        Query params = new Query(params1);
        params.clear();
        params = new Query(params1);
        params.put("limit", 2);
        params.put("iscom","1");
        mav.addObject("commList", tGoodsService.list1(params));
        params.clear();
        params = new Query(params1);
        params.put("limit", 6);
        params.put("sort","sellhit");
        params.put("order","desc");
        mav.addObject("sellList", tGoodsService.list1(params));
        mav.setViewName("web/goodsList");
        return mav;
    }
}
