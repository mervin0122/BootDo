package com.bootdo.web;

import cn.binarywang.wx.miniapp.api.WxMaService;


import com.bootdo.common.utils.R;
import com.bootdo.shop.domain.TCartDO;
import com.bootdo.shop.domain.TGoodsDO;

import com.bootdo.shop.domain.TOrderDO;
import com.bootdo.shop.domain.TStoreDO;
import com.bootdo.shop.service.AddressService;
import com.bootdo.shop.service.ArticleService;
import com.bootdo.shop.service.BannerService;
import com.bootdo.shop.service.CouponService;
import com.bootdo.shop.service.FavoriteService;
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

import com.bootdo.system.domain.UserDO;
import com.bootdo.wap.MemberUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/19 0019.
 */
@Controller
@RequestMapping("/web/person")
public class PersonController {
    private static final Logger LOG = LoggerFactory.getLogger(PersonController.class.getName());
    private String PREFIX = "/web/";
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
     * 加入购物车
     * @param
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addCart", method = RequestMethod.POST)
    public @ResponseBody
    R addCart(@RequestParam Map<String, Object> params) throws Exception {
        params.put("offset", 0);
        R r=new R();
        UserDO user=MemberUtils.getSessionLoginUser();
        try {
            String goodsid = params.get("goodsid").toString();
            String count = params.get("count").toString();
            if (StringUtils.isEmpty(goodsid) || user == null ||
                    StringUtils.isEmpty(count)) {
                return R.Empty();
            }
            TGoodsDO goods = tGoodsService.get(Long.parseLong(goodsid));
            params.remove("count");
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
        List<TCartDO> cartList=tCartService.list(param);
        mav.addObject("cartList", cartList);

//        List<TAddress> addressList= AddressService.selectByMemberId();
//        mav.addObject("addressList", addressList);
//
//        TPayment Payment=new Payment();
//        Payment.setIsDel(1);
//        List<Payment> payList=PaymentService.select(Payment);
//        mav.addObject("payList", payList);

        mav.setViewName("web/likBuy");
        return mav;
    }
    /**
     * 提交订单
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping("submitOrder")
    public ModelAndView submitOrder(@RequestParam(value = "cartIds") String[] cartIds,
                                    @RequestParam(value = "addressid") Long addressid,
                                    @RequestParam(value = "paymentid") Long paymentid,
                                    @RequestParam(value = "paymentid",defaultValue="无留言") String usercontent
    )throws Exception{
        ModelAndView mav=new ModelAndView();
        UserDO m =MemberUtils.getSessionLoginUser();
       TOrderDO order=tCartService.insertWebOrder(cartIds,addressid,paymentid,usercontent,m.getUserId(),m.getUsername());
//        Payment Payment=new Payment();
//        Payment.setIsDel(1);
//        List<Payment> payList=PaymentService.select(Payment);
//        mav.addObject("payList", payList);
        if(order==null){
            mav.setViewName("web/forwad");
        }else{
            mav.setViewName("web/success");
        }

        mav.addObject("order", order);
        return mav;
    }
}
