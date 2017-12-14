package com.bootdo.web;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.shop.Constan;
import com.bootdo.shop.domain.AddressDO;
import com.bootdo.shop.domain.CouponDO;
import com.bootdo.shop.domain.FavoriteDO;
import com.bootdo.shop.domain.JifengoodsDO;
import com.bootdo.shop.domain.TCartDO;
import com.bootdo.shop.domain.TGoodSorderDO;
import com.bootdo.shop.domain.TGoodsDO;

import com.bootdo.shop.domain.TOrderDO;
import com.bootdo.shop.domain.TStoreDO;
import com.bootdo.shop.domain.UserJfgoodsDO;
import com.bootdo.shop.service.AddressService;
import com.bootdo.shop.service.ArticleService;
import com.bootdo.shop.service.BannerService;
import com.bootdo.shop.service.CouponService;
import com.bootdo.shop.service.FavoriteService;
import com.bootdo.shop.service.JifendataService;
import com.bootdo.shop.service.JifengoodsService;
import com.bootdo.shop.service.TBrandService;
import com.bootdo.shop.service.TCartService;
import com.bootdo.shop.service.TGoodSorderService;
import com.bootdo.shop.service.TGoodsClassService;
import com.bootdo.shop.service.TGoodsService;
import com.bootdo.shop.service.TGoodsTypeService;
import com.bootdo.shop.service.TMemberService;
import com.bootdo.shop.service.TOrderService;
import com.bootdo.shop.service.TReplyService;
import com.bootdo.shop.service.TStoreService;
import com.bootdo.shop.service.TopicService;
import com.bootdo.shop.service.UserJfgoodsService;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.service.UserService;
import com.bootdo.wap.MemberUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/19 0019.
 */
@Controller
@RequestMapping("/person/street")
public class StreetPersonController {
    private static final Logger LOG = LoggerFactory.getLogger(StreetPersonController.class.getName());
    private String PREFIX = "/street/";
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
    private JifendataService jifendataService;
    @Autowired
    private UserService userService;
    @Autowired
    private JifengoodsService jifengoodsService;
    @Autowired
    private UserJfgoodsService userJfgoodsService;
    /**
     * 获取用户信息
     * @param
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goods_count_adjust", method = RequestMethod.POST)
    public @ResponseBody
    R session(HttpServletRequest req) throws Exception {
        String cart_id = req.getParameter("cart_id");
        String count = req.getParameter("count");
        R r=new R();
        UserDO user=MemberUtils.getSessionLoginUser();
        try {
            if(user!=null){
                TCartDO cart = new TCartDO();
                cart.setCount(Integer.parseInt(count));
                cart.setId(Long.parseLong(cart_id));
                tCartService.update(cart);

                Map<String, Object> map = new HashMap<>();
                map.put("userid",user.getUserId());
                TCartDO newCart =tCartService.get(Long.parseLong(cart_id));
                Integer price = Integer.parseInt(newCart.getPrice());
                r.put("totalprice",tCartService.countprice(map));
                r.put("goodsprice", price*Integer.parseInt(count));
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
     * 开店
     */

    @RequestMapping(value = "addStore", method = RequestMethod.GET)
    public String addStore(Model model) {
        UserDO tMember = MemberUtils.getSessionLoginUser();
        if (tMember!=null && tMember.getStoreid()!=null){
            TStoreDO store = tStoreService.get(tMember.getStoreid());
            model.addAttribute("store",store);
            return PREFIX + "store";
        }else{
            return PREFIX + "addStore";
        }

    }
    @RequestMapping(value = "addStore", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> addStores(TStoreDO tStore,
                                   HttpServletRequest req) {
        Map<String, Object> map = new HashMap<>();
        UserDO tMember = MemberUtils.getSessionLoginUser();
        if (tMember!=null) {
            tStore.setAddtime(new Date());
            tStoreService.save(tStore);
            tMember.setStoreid(tStore.getId());
          //  tMemberService.update(tMember);
            HttpSession session = req.getSession();
            session.setAttribute(MemberUtils.SESSION_LOGIN_MEMBER, tMember);
            map.put("code","1");
            map.put("id",tStore.getId());
        }else {
            map.put("code","2");
        }
        LOG.info("addStores:{}",map);
        return map;
    }


    @RequestMapping(value = "addGoods", method = RequestMethod.GET)
    public String addGoods() {
        UserDO tMember = MemberUtils.getSessionLoginUser();
        if (tMember!=null && tMember.getStoreid()!=null) {
            return PREFIX + "addGoods";
        }else{
            return PREFIX + "login";
        }
    }
    @RequestMapping(value = "addGoods", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> addGoods(TGoodsDO tGoods,
                                  HttpServletRequest req) {
        Map<String, Object> map = new HashMap<>();

        UserDO tMember = MemberUtils.getSessionLoginUser();
        if (tMember!=null) {
            String imges="";
            String blogInfo=tGoods.getRemark();
            Document doc=Jsoup.parse(blogInfo);
            Elements jpgs=doc.select("img[src]"); //　查找扩展名是jpg的图片
            for(int i=0;i<jpgs.size();i++){
                Element jpg=jpgs.get(i);
                if(jpg!=null && jpg!=null){
                    String linkHref = jpg.attr("src");
                    imges+=linkHref+",";
                }
                if(i==2){
                    break;
                }
            }
            tGoods.setStoreid(tMember.getStoreid());
            tGoods.setImgmore(imges);
            tGoods.setCreateDate(new Date());
            tGoods.setCreateBy(tMember.getUserId());
            tGoodsService.save(tGoods);
            map.put("id",tMember.getStoreid());
            map.put("code","1");
        }else {
            map.put("code","2");
        }
        LOG.info("addGoods:{}",map);
        return map;
    }
    /**
     * 删除购物车
     * @param
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteCart", method = RequestMethod.POST)
    public @ResponseBody
    R deleteCart(HttpServletRequest req) throws Exception {
        try {
            String goodsid = req.getParameter("goodsid");
            if(tCartService.remove(Long.parseLong(goodsid))>0){
                return R.ok();
            }
            return R.error();
        }
        catch (Exception e){
            e.printStackTrace();
            return R.error();
        }
    }
    /**
     * 加入购物车
     * @param
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addCart", method = RequestMethod.POST)
    public @ResponseBody
    R addCart(HttpServletRequest req) throws Exception {
     Map<String, Object> params =new HashMap<>();
        params.put("offset", 0);
        R r=new R();
        UserDO user=MemberUtils.getSessionLoginUser();
        try {
            String goodsid = req.getParameter("goodsid");
            String count = req.getParameter("count");
            if (StringUtils.isEmpty(goodsid) || user == null) {
                return R.Empty();
            }
            if (StringUtils.isEmpty(count)) {
                count ="1";
            }
            TGoodsDO goods = tGoodsService.get(Long.parseLong(goodsid));
            params.remove("count");
            params.put("userid", user.getUserId());
            params.put("goodsid", goodsid);
            TCartDO cart = tCartService.selectOne(params);
            if (cart != null) {
                cart.setCount(cart.getCount() + Integer.parseInt(count));
                tCartService.update(cart);
            } else {
                cart = new TCartDO();
                cart.setCount(Integer.parseInt(count));
                cart.setGoodsid(Long.parseLong(goodsid));
                cart.setUserid(user.getUserId());
                cart.setPrice(goods.getPrices());
                cart.setStoreid(goods.getStoreid());
                cart.setImg(goods.getImg());
                cart.setGoodsname(goods.getTitle());
                tCartService.save(cart);
            }
        }
       catch (Exception e){
            e.printStackTrace();
            return R.error();
        }
        return r;
    }
    /**
     * 立即购买
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping("/likBuy/{ProductId}")
    public ModelAndView ProductDetail(@PathVariable("ProductId") Long ProductId,
                                      HttpSession session)throws Exception{
        ModelAndView mav=new ModelAndView();
        Map<String, Object> param =new HashMap<>();
        param.put("userid",MemberUtils.getSessionLoginUser().getUserId());
        mav.addObject("totalprice",tCartService.countprice(param));
        param.put("limit", 20);
        List<AddressDO> addressList= addressService.list(param);
        mav.addObject("addressList", addressList);
        List<CouponDO> couponList = couponService.list(param);
        mav.addObject("couponList", couponList);
        param.put("goodsid", ProductId);
        List<TCartDO> cartList=tCartService.list(param);
        mav.addObject("cartList", cartList);

        mav.setViewName("street/likBuy");
        return mav;
    }

    /**
     * 重购物车过来
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping("/likBuy1")
    public ModelAndView ProductDetail1()throws Exception{
        ModelAndView mav=new ModelAndView();
        Map<String, Object> param =new HashMap<>();

        param.put("userid",MemberUtils.getSessionLoginUser().getUserId());
        mav.addObject("totalprice",tCartService.countprice(param));
        param.put("limit", 20);
        List<TCartDO> cartList=tCartService.list(param);
        mav.addObject("cartList", cartList);

        List<AddressDO> addressList= addressService.list(param);
        mav.addObject("addressList", addressList);
        List<CouponDO> couponList = couponService.list(param);
        mav.addObject("couponList", couponList);
        mav.setViewName("street/likBuy");
        return mav;
    }
    /**
     * 提交订单
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/submitOrder")
    public R orderSubmit(HttpServletRequest req){
        R r=new R();
        try {
            TOrderDO result = tCartService.orderStreetSubmit(req);
            if (result!=null){
                jifendataService.save(Constan.SUBMITORDER);
                r.put("data",result);
            }else{
                return R.error();
            }
        }catch (Exception e){
            e.printStackTrace();
            return R.error();
        }
        return r;
    }
    /**
     * 余额支付
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/balancePay")
    public R balancePay(HttpServletRequest req){
        try {
            return tCartService.balancePay(req);
        }catch (Exception e){
            e.printStackTrace();
            return R.error();
        }
    }
    /**
     * 立即付款
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping("/pay/{orderId}")
    public ModelAndView pay(@PathVariable("orderId") Long orderId
                            )throws Exception{
        ModelAndView mav=new ModelAndView();
        TOrderDO orderDO =orderService.get(orderId);
        mav.addObject("order", orderDO);
        AddressDO addressDO = addressService.get(orderDO.getAddressid());
        String str = "收货信息："+addressDO.getDetail()+","+addressDO.getName()+",手机号:"+addressDO.getMobile();
        mav.addObject("str", str);
        mav.addObject("user", userService.get(MemberUtils.getSessionLoginUser().getUserId()));
        mav.setViewName("street/pay");
        return mav;
    }

    /**
     * 我的订单
     * @return
     * @throws Exception
     */
    @RequestMapping("/order")
    public ModelAndView order()throws Exception{
        ModelAndView mav=new ModelAndView();
        Map<String, Object> param =new HashMap<>();
        param.put("userid",MemberUtils.getSessionLoginUser().getUserId());
        Query params = new Query(param);
        params.put("limit", 4);
        params.put("iscom","1");
        mav.addObject("commList", tGoodsService.list1(params));
        mav.setViewName("street/my_order");
        return mav;
    }

    /**
     * 我的积分
     * @return
     * @throws Exception
     */
    @RequestMapping("/jifen")
    public ModelAndView jifen()throws Exception{
        ModelAndView mav=new ModelAndView();
        Map<String, Object> param =new HashMap<>();
        UserDO userDO =MemberUtils.getSessionLoginUser();
        param.put("userid",userDO.getUserId());
        Query params = new Query(param);
        params.put("limit", 20);
        mav.addObject("jfList", jifendataService.list(param));
        mav.addObject("jf", userDO.getJifen());

        param.clear();
        param.put("userid",userDO.getUserId());
        params.put("limit", 5);
        params.put("iscom","1");
        Query query = new Query(param);
        mav.addObject("commList", tGoodsService.list1(query));
        mav.setViewName("street/jifen");
        return mav;
    }
    @RequestMapping("/ajax-order")
    public String ajaxorder(HttpServletRequest request) {
        R r=new R();
        try {
            String status = request.getParameter("status");
            if (status != null && !status.equals("")) {
                Map<String, Object> params = new HashMap<>();
                if(!"0".equals(status)){
                    params.put("status",status);
                }
                params.put("offset", 0);
                Query query = new Query(params);
                List<TOrderDO> tOrderList = orderService.list(query);
                //int total = orderService.count(query);
                for (TOrderDO orderDO : tOrderList){
                    params.clear();
                    params.put("orderid",orderDO.getId());
                    List<TGoodSorderDO> gs = tGoodSorderService.list(params);
                    orderDO.setGoodsList(gs);
                }
                request.setAttribute("tOrderList",tOrderList);
            }
        } catch (Exception e) {

        }
        return "street/ajax_order";
    }
    /**
     * 我的个人信息
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping("/my")
    public ModelAndView jfgoods(HttpSession session)throws Exception{
        ModelAndView mav=new ModelAndView();
        Map<String, Object> param =new HashMap<>();
        Query params = new Query(param);
        params.put("limit", 5);
        params.put("iscom","1");
        mav.addObject("commList", tGoodsService.list1(params));
        mav.setViewName("street/my");
        return mav;
    }
    /**
     * 我的个人信息
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajax-my")
    public String ajaxmy(HttpServletRequest request) {

        try {
            String status = request.getParameter("status");
            if (status != null && !status.equals("")) {
                Map<String, Object> params = new HashMap<>();
                if("me".equals(status)){
                   UserDO u = MemberUtils.getSessionLoginUser();
                   UserDO user = userService.get(u.getUserId());
                    request.setAttribute("user",user);
                    return "street/ajax_me";
                }else if("coupon".equals(status)){
                    params.clear();
                    params.put("offset", 0);
                    Query query = new Query(params);
                    List<CouponDO> list = couponService.list(query);
                    request.setAttribute("couponList",list);
                    return "street/ajax_coupon";
                }
                if("collect".equals(status)){
                    params.clear();
                    params.put("offset", 0);
                    params.put("type",1);
                    Query query = new Query(params);
                    List<FavoriteDO> list = favoriteService.userFavorite(query);
                    request.setAttribute("collectList",list);
                    return "street/ajax_collect";
                }
                if("address".equals(status)){
                    params.clear();
                    params.put("offset", 0);
                    Query query = new Query(params);
                    List<AddressDO> list = addressService.list(query);
                    request.setAttribute("addressList",list);
                    return "street/ajax_address";
                }
                if("jfgoods".equals(status)){
                    params.clear();
                    params.put("userid",MemberUtils.getSessionLoginUser().getUserId());
                    List<UserJfgoodsDO> ujList=userJfgoodsService.list(params);
                    request.setAttribute("ujList",ujList);
                    return "street/ajax_jfgoods";
                }
            }
        } catch (Exception e) {

        }
        return "street/ajax_order";
    }

    /**
     * 取消订单
     * @param req
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
    public @ResponseBody
    R cancelOrder(HttpServletRequest req) throws Exception {
        try {
            String orderid = req.getParameter("orderid");
            TOrderDO order =new TOrderDO();
            order.setId(Long.parseLong(orderid));
            order.setStatus(4);
            if(orderService.update(order)>0){
                return R.ok();
            }
            return R.error();
        }
        catch (Exception e){
            e.printStackTrace();
            return R.error();
        }
    }

    /**
     * 购物车列表
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping("/cartList")
    public ModelAndView cartList(HttpSession session)throws Exception{
        ModelAndView mav=new ModelAndView();
        Map<String, Object> param =new HashMap<>();
        param.put("userid",MemberUtils.getSessionLoginUser().getUserId());
        List<TCartDO> cartList=tCartService.list1(param);
        mav.addObject("cartList", cartList);
        mav.addObject("totalprice",tCartService.countprice(param));
        param.clear();
        Query params = new Query(param);
        params.put("limit", 5);
        params.put("iscom","1");
        mav.addObject("commList", tGoodsService.list1(params));
        mav.setViewName("street/cartList");
        return mav;
    }



    /**
     * 积分兑换商品
     * @param
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/duihuan", method = RequestMethod.POST)
    public @ResponseBody
    R duihuan(HttpServletRequest req) throws Exception {

        R r=new R();
        UserDO user=MemberUtils.getSessionLoginUser();
        try {
            String goodsid = req.getParameter("goodsid");
            if (StringUtils.isEmpty(goodsid) || user == null) {
                return R.Empty();
            }

            UserDO userDO = userService.get(user.getUserId());
            JifengoodsDO goods = jifengoodsService.get(Long.parseLong(goodsid));
           if(Integer.parseInt(goods.getPrices())>userDO.getJifen()){
               return R.error("积分不足，请去挣够积分再来！");
           }else{
               Map<String, Object> params =new HashMap<>();
               params.put("offset", 0);
               params.put("goodsid", goods.getId());
               params.put("userid", userDO.getUserId());
               List<UserJfgoodsDO> uj = userJfgoodsService.list(params);
               if (uj!=null && uj.size()>0){
                   return R.error("您已经兑换过此商品！");
               }else{
                   UserJfgoodsDO ujf =new UserJfgoodsDO();
                   ujf.setGoodsid(goods.getId());
                   ujf.setGoodsname(goods.getTitle());
                   ujf.setPrice(BigDecimal.valueOf(Double.parseDouble(goods.getPrices())));
                   ujf.setUserid(userDO.getUserId());
                   ujf.setImg(goods.getImg());
                   userJfgoodsService.save(ujf);
               }
           }
        }
        catch (Exception e){
            e.printStackTrace();
            return R.error();
        }
        return r;
    }

    /**
     * 添加地址
     * @param req
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/favorite-add")
    public R favoriteadd(@RequestParam Map<String, Object> params,HttpServletRequest req)throws Exception{
        Long goodsid=Long.parseLong(req.getParameter("goodsid"));
        params.put("offset", 0);
        R r=new R();
        try {
            FavoriteDO favoriteDO = favoriteService.selectOne(params);
            if (favoriteDO!=null){
                FavoriteDO favoriteDO1 =new FavoriteDO();
                favoriteDO1.setId(favoriteDO.getId());
                //更新状态
                if(favoriteDO.getDeletestatus()==2){
                    favoriteDO1.setDeletestatus(1);
                }else{
                    favoriteDO1.setDeletestatus(2);
                }

                favoriteDO1.setAddtime(new Date());
                favoriteService.update(favoriteDO1);
            }else{
                TGoodsDO goodsDO = tGoodsService.get(goodsid);
                favoriteDO = new FavoriteDO();
                favoriteDO.setType(1); //增加
                favoriteDO.setDeletestatus(2);
                favoriteDO.setGoodsid(goodsid);
                favoriteDO.setUserid(MemberUtils.getSessionLoginUser().getUserId());
              //  favoriteDO.setStoreid(goodsDO.getStoreid());
                favoriteDO.setAddtime(new Date());
                favoriteService.save(favoriteDO);
            }
            r.put("data",favoriteDO);
        }catch (Exception e){
            e.printStackTrace();
            return R.error();
        }
        return r;
    }
//	favorite_remove: _api_root + "user/favorite-remove",
    /**
     * 删除
     */
    @PostMapping( "/favorite-remove")
    @ResponseBody
    public R favoriteremove( Long id){
        if(favoriteService.remove(id)>0){
            return R.ok();
        }
        return R.error();
    }
}
