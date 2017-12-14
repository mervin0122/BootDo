package com.bootdo.web;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.bootdo.common.utils.MD5Utils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.shop.domain.TFloorDO;
import com.bootdo.shop.domain.TGoodsClassDO;
import com.bootdo.shop.domain.TGoodsDO;

import com.bootdo.shop.domain.TStoreDO;
import com.bootdo.shop.service.AddressService;
import com.bootdo.shop.service.ArticleService;
import com.bootdo.shop.service.BannerService;
import com.bootdo.shop.service.CouponService;
import com.bootdo.shop.service.FavoriteService;
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
import com.bootdo.wap.MemberUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author stylefeng
 * @since 2017-05-17
 */
@Controller
@RequestMapping("okShop")
public class OkShopController {
    private String PREFIX = "/okshop/";
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
    /**
     * 跳转到部门管理首页
     */
    @RequestMapping("index")
    public String index(Model model) {
        try {
            UserDO user = ShiroUtils.getUser();
            model.addAttribute("user", user);
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
            System.out.println("goodsClassList:"+goodsClassList);
            model.addAttribute("goodsClassList", goodsClassList);
            model.addAttribute("user", MemberUtils.getSessionLoginUser());
            Map<String, Object> params1 = new HashMap<>();
            params = new Query(params1);
            params.put("limit", 8);
            params.put("sort","clickHit");
            params.put("order","desc");
            model.addAttribute("hitList", tGoodsService.list1(params));
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
            params.put("sort","replyhit");
            params.put("order","desc");
            params.put("limit", 8);
            model.addAttribute("replyList", tGoodsService.list1(params));

            params = new Query(params1);
            params.put("limit", 16);
            model.addAttribute("brandList", tBrandService.list(params));

            params = new Query(params1);
            params.put("limit", 8);
            model.addAttribute("storeList", tStoreService.list(params));
           List<TFloorDO> floorList = tFloorService.list(null);
           for (TFloorDO floorDO:floorList){
               List<TGoodsDO> goodsList = tGoodsService.getProductByFloorid(floorDO.getId());
               floorDO.setGoodsList(goodsList);
           }
            model.addAttribute("floorList", floorList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return PREFIX + "index";
    }


    /**
     * 跳转到商品详情
     */
    @RequestMapping("/goodsDetail/{id}")
    public String goodsDetail(@PathVariable Long id, Model model) {
        TGoodsDO goods = tGoodsService.get(id);
        model.addAttribute("goods",goods);
        if(goods.getImgmore()!=null && goods.getImgmore().indexOf(",")>-1){
            model.addAttribute("imgs", goods.getImgmore().split(","));
        }
        goods.setClickhit(goods.getClickhit()+1);
        tGoodsService.update(goods);
        TStoreDO store = tStoreService.get(goods.getStoreid());
        model.addAttribute("store",store);
        Map<String, Object> params1 = new HashMap<>();
        Query params = new Query(params1);
        params.put("limit", 4);
        params.put("iscom","1");
        params.put("storeid",goods.getStoreid());
        model.addAttribute("commList", tGoodsService.list1(params));

        params = new Query(params1);
        params.put("limit", 6);
        params.put("brandid",goods.getStoreid());
        model.addAttribute("brandList", tGoodsService.list1(params));

        params = new Query(params1);
        params.put("limit", 6);
        params.put("typeid",goods.getStoreid());
        model.addAttribute("typeList", tGoodsService.list1(params));

        params = new Query(params1);
        params.put("limit", 6);
        params.put("classid",goods.getStoreid());
        model.addAttribute("classList", tGoodsService.list1(params));
        return PREFIX + "goodsDetail";
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
    @RequestMapping(value = "tlogin", method = RequestMethod.GET)
    public String toLogin() {
        if( MemberUtils.getSessionLoginUser() != null){
            return "redirect:/index";
        }
        return PREFIX + "login";
    }


    /**
     * 跳转到登录页面
     *
     * @return
     */
    @RequestMapping(value = "treg", method = RequestMethod.GET)
    public String reg() {
        if( MemberUtils.getSessionLoginUser() != null){
            return "redirect:/index";
        }
        return PREFIX + "register";
    }

    @RequestMapping(value = "treg", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> reg(
            @RequestParam(value = "password",required=true)String  password,
            @RequestParam(value = "email",required=false)String email,
            @RequestParam(value = "username",required=false)String username,
            @RequestParam(value = "phone",required=false)String phone,
            @RequestParam(value = "passwordRepeat",required=true)String passwordRepeat,HttpServletRequest request) {
        Map<String, Object> msg = new HashMap<String, Object>();
        if (!StringUtils.equalsIgnoreCase(password, passwordRepeat)) {
            msg.put("error", "密码不一致!");
            return msg;
        }
        String secPwd = null ;

        UserDO m=new UserDO();
        m.setEmail(email);
        m.setUsername(username);
        secPwd = MD5Utils.encrypt(password, username);

        m.setStatus(1);
        
        m.setPassword(secPwd);
        Random r = new Random();
//        m.setGold(r.nextInt(10));
//        m.setAddtime(new Date());
//        m.setTruename(m.getUsername());
        try {
            int result =1;// tMemberService.save(m);
            HttpSession session = request.getSession();
            if (result>0) {
                UserDO mb=new UserDO();
                mb.setUsername(username);
                mb.setPassword(secPwd);
                session.setAttribute(MemberUtils.SESSION_LOGIN_MEMBER, tMemberService.get(m.getUserId()));
            } else {
                msg.put("error", "注册失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }
    /**
     * 用户退出
     *
     * @return 跳转到登录页面
     */
    @RequestMapping("tlogout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/front/tlogin";
    }

    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFileHandlerStore(@RequestParam("file") MultipartFile file,
                                     HttpServletRequest request) throws IOException {
        return fileUpload(file, request,"upload/store/");
    }


    @RequestMapping(value = "/fileUpload1", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFileHandlerGoods(@RequestParam("file") MultipartFile file,
                                    HttpServletRequest request) throws IOException {
        return fileUpload(file, request,"upload/project");
    }

    private String fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request,String path) throws IOException {
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
}
