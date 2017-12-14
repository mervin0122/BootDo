package com.bootdo.shop.service.impl;

import com.bootdo.common.utils.JSONUtils;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.RandomString;
import com.bootdo.shop.Constan;
import com.bootdo.shop.dao.CouponDao;
import com.bootdo.shop.domain.*;
import com.bootdo.shop.service.*;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.service.UserService;
import com.bootdo.wap.MemberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

import com.bootdo.shop.dao.TCartDao;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;


@Service
public class TCartServiceImpl implements TCartService {
	@Autowired
	private TCartDao tCartDao;
	@Autowired
	private TStoreService tStoreService;
	@Autowired
	private TGoodsService tGoodsService;
	@Autowired
	private AddressService addressService;
	@Autowired
	private CouponService couponService;
	@Autowired
	private TOrderService tOrderService;
	@Autowired
	private TOrderLogService tOrderLogService;
	@Autowired
	private TGoodSorderService tGoodSorderService;
	@Autowired
	private UserService userService;
	@Autowired
	private JifendataService jifendataService;
	@Override
	public TCartDO get(Long id){
		return tCartDao.get(id);
	}
	
	@Override
	public List<TCartDO> list(Map<String, Object> map){
		return tCartDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return tCartDao.count(map);
	}
	
	@Override
	public int save(TCartDO tCart){
		return tCartDao.save(tCart);
	}
	
	@Override
	public int update(TCartDO tCart){
		return tCartDao.update(tCart);
	}
	
	@Override
	public int remove(Long id){
		return tCartDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return tCartDao.batchRemove(ids);
	}

	@Override
	public TCartDO selectOne(Map<String, Object> params) {
		List<TCartDO> list = tCartDao.list(params);
		if (list!=null && list.size()>0){
			return  list.get(0);
		}
		return null;
	}
	@Override
	public
	List<TCartDO> list1(Map<String, Object> map){
		return tCartDao.list1(map);
	}

	/**
	 * 计算前
	 * @param map
	 * @return
	 */

	@Override
	public String countprice(Map<String, Object> map){
		return tCartDao.countprice(map);
	}
	@Override
	public int selectOwnCart(Long id) {
		Map<String, Object> map = new HashMap<>();
		map.put("userid",id);
		return tCartDao.count(map);
	}

	@Override
	public Map<String, Object> ordersubmitpreview(Map<String, Object> params) {
		Map<String, Object> data =new HashMap<>();
		String userid =params.get("userid").toString();
		String addressid =params.get("addressid").toString();
		Object goods_info =params.get("goods_info");
		if(!"".equals(goods_info) && goods_info!=null){
			Map<String, Object> map = JSONUtils.jsonToMap(goods_info.toString());
			String goodsid=map.get("goodsid").toString();
			String count=map.get("count").toString();
			map.put("userid",userid);
			TGoodsDO goods = tGoodsService.get(Long.parseLong(goodsid));
			map.remove("count");
			TCartDO cart = this.selectOne(map);
			if(cart!=null){
				cart.setCount(cart.getCount()+Integer.parseInt(count));
				tCartDao.update(cart);
			}else{
				cart = new TCartDO();
				cart.setCount(Integer.parseInt(count));
				cart.setGoodsid(Long.parseLong(goodsid));
				cart.setUserid(Long.parseLong(userid));
				cart.setPrice(goods.getPrices());
				cart.setStoreid(goods.getStoreid());
				cart.setImg(goods.getImg());
				cart.setGoodsname(goods.getTitle());
				tCartDao.save(cart);
			}
			List<TCartDO> cartDOList = new ArrayList<>();
			cartDOList.add(cart);
			data.put("goodsList",cartDOList);
			data.put("cartid",cart.getId());
		}else {
			String cart_id_list1 =params.get("cart_id_list").toString();
			//[5,2,1]
			String ids =cart_id_list1.substring(1,cart_id_list1.length()-1);
			String[] ids1 =ids.split(",");
			List<TCartDO> cartDOList =tCartDao.batchGet(ids1);
			data.put("goodsList",cartDOList);
			data.put("cart_id_list",cart_id_list1);
		}

		if(StringUtils.isNoneBlank(addressid)){
			AddressDO addressDO = addressService.get(Long.parseLong(addressid));
			data.put("address",addressDO);
		}else{
			params.clear();
			params.put("userid",userid);
			params.put("isdefault",1);
			AddressDO addressDO = addressService.selectOne(params);
			data.put("address",addressDO);
		}
		params.clear();
		params.put("id",userid);
		params.put("status",3);
		List<CouponDO> couponDaoList = couponService.userCoupon(params);
		data.put("couponList",couponDaoList);
		data.put("express_price",1);

		data.put("send_type",1);
		List<TStoreDO> shop_list =tStoreService.list(null);
		data.put("shop_list",shop_list);
		return data;
	}

	@Override
	@Transactional
	public TOrderDO orderSubmit(HttpServletRequest req) {
		TOrderDO order = new TOrderDO();
		String cart_id_list1 = req.getParameter("cart_id_list");
		String userid = req.getParameter("userid");
		String addressid = req.getParameter("addressid");
		String content = req.getParameter("content");
		String offline = req.getParameter("offline");
		String express_price = req.getParameter("express_price");
		String couponid = req.getParameter("couponid");
		String cartid = req.getParameter("cartid");
		order.setOrdersn(RandomString.generateRandomString(8));
		order.setCreatedate(new Date());
		order.setStatus(9);
		order.setUserid(Long.parseLong(userid));
		order.setUsername(express_price);//运费
		order.setPaymentid(Long.parseLong(offline));// 0仅快递 1仅自提
		order.setUsercontent(content);
		order.setAddressid(Long.parseLong(addressid));
		tOrderService.save(order);
		int count = 0;
		BigDecimal total = BigDecimal.ZERO;
		if(StringUtils.isNoneBlank(cartid)){
			TCartDO cart = tCartDao.get(Long.parseLong(cartid));
			if (cart == null) {
				return null;
			}
			count = cart.getCount();
			total = BigDecimal.valueOf(Double.valueOf(cart.getPrice())).multiply(BigDecimal.valueOf(cart.getCount()));
			TGoodSorderDO go = new TGoodSorderDO();
			go.setGoodsid(cart.getGoodsid());
			go.setOrderid(order.getId());
			go.setImg(cart.getImg());
			go.setGoodsname(cart.getGoodsname());
			go.setCount(cart.getCount());
			go.setPrice(cart.getPrice());
			tGoodSorderService.save(go);
			tCartDao.remove(cart.getId());
		}else{
			//[5,2,1]
			String ids = cart_id_list1.substring(1, cart_id_list1.length() - 1);
			String[] ids1 = ids.split(",");
			if (ids1 != null && ids1.length > 0) {
				for (String cartId : ids1) {
					TCartDO cart = tCartDao.get(Long.parseLong(cartId));
					if (cart == null) {
						return null;
					}
					count += cart.getCount();
					total = total.add(BigDecimal.valueOf(Double.valueOf(cart.getPrice())).multiply(BigDecimal.valueOf(cart.getCount())));
					TGoodSorderDO go = new TGoodSorderDO();
					go.setGoodsid(cart.getGoodsid());
					go.setOrderid(order.getId());
					go.setImg(cart.getImg());
					go.setCount(cart.getCount());
					go.setPrice(cart.getPrice());
					go.setGoodsname(cart.getGoodsname());
					tGoodSorderService.save(go);
					tCartDao.remove(cart.getId());
				}
			}
		}
		if (StringUtils.isNoneBlank(couponid)){
			order.setCouponid(Long.parseLong(couponid));
			CouponDO couponDO = couponService.get(Long.parseLong(couponid));
			total = total.subtract(couponDO.getAmount());
		}
		total = total.subtract(BigDecimal.valueOf(Double.valueOf(express_price)));
		order.setTotalcount(count);
		order.setTotalprice(total);
		//order.setOrderTotalPrice();


		TOrderLogDO log = new TOrderLogDO();
		log.setOrderId(order.getId());
		log.setOrderState("1");
		log.setStateInfo("提交订单");
		log.setCreateTime(new Date().getTime());
		log.setOperator(userid);
		tOrderLogService.save(log);
		tOrderService.update(order);

		return order;
	}

	@Override
	@Transactional
	public TOrderDO orderStreetSubmit(HttpServletRequest req) {
		TOrderDO order = new TOrderDO();
		Map<String, Object> param =new HashMap<>();
		Long userid = MemberUtils.getSessionLoginUser().getUserId();
		param.put("userid",userid);
		List<TCartDO> cartList = tCartDao.list(param);
		if (cartList != null && cartList.size() > 0) {
				String addressid = req.getParameter("addressid");
				String content = req.getParameter("content");
				String offline = "0";
				String express_price = "0";
				String couponid = req.getParameter("couponid");

				order.setOrdersn(RandomString.generateRandomString(8));
				order.setCreatedate(new Date());
				order.setStatus(9);
				order.setUserid(userid);
				order.setUsername(express_price);//运费
				order.setPaymentid(Long.parseLong(offline));// 0仅快递 1仅自提
				order.setUsercontent(content);
				order.setAddressid(Long.parseLong(addressid));
				tOrderService.save(order);
				int count = 0;
				BigDecimal total = BigDecimal.ZERO;
				for (TCartDO cart : cartList) {
					if (cart == null) {
						return null;
					}
					count += cart.getCount();
					total = total.add(BigDecimal.valueOf(Double.valueOf(cart.getPrice())).multiply(BigDecimal.valueOf(cart.getCount())));
					TGoodSorderDO go = new TGoodSorderDO();
					go.setGoodsid(cart.getGoodsid());
					go.setOrderid(order.getId());
					go.setImg(cart.getImg());
					go.setCount(cart.getCount());
					go.setPrice(cart.getPrice());
					go.setGoodsname(cart.getGoodsname());
					tGoodSorderService.save(go);
					tCartDao.remove(cart.getId());
				}
			if (StringUtils.isNoneBlank(couponid)){
				order.setCouponid(Long.parseLong(couponid));
				CouponDO couponDO = couponService.get(Long.parseLong(couponid));
				total = total.subtract(couponDO.getAmount());
				Map map = new HashMap();
				map.put("userid",userid);
				map.put("couponid",couponDO.getId());
				couponService.removeUserCoupon(map);

			}
			total = total.subtract(BigDecimal.valueOf(Double.valueOf(express_price)));
			order.setTotalcount(count);
			order.setTotalprice(total);
			//order.setOrderTotalPrice();


			TOrderLogDO log = new TOrderLogDO();
			log.setOrderId(order.getId());
			log.setOrderState("1");
			log.setStateInfo("提交订单");
			log.setCreateTime(new Date().getTime());
			log.setOperator(userid+"");
			tOrderLogService.save(log);
			tOrderService.update(order);
			}



		return order;
	}
	@Override
	public TOrderDO insertWebOrder(String[] cartIds, Long addressid, Long paymentid, String usercontent, Long id, String username) {
		return null;
	}

	@Override
	public R balancePay(HttpServletRequest req){
		R r =new R();
		String orderid = req.getParameter("orderid");
		String totalprice = req.getParameter("totalprice");
		String balance = req.getParameter("balance");
		if(Integer.parseInt(totalprice)>Integer.parseInt(balance)){
			return R.error("余额不足！");
		}else {

			TOrderDO order =tOrderService.get(Long.parseLong(orderid));
			UserDO userDO = MemberUtils.getSessionLoginUser();
			order.setStatus(1);
			tOrderService.update(order);
			userDO.setBalance(userDO.getBalance()-order.getTotalprice().intValue());
			userService.updateOnly(userDO);
			JifendataDO jifendata = new JifendataDO();
			jifendata.setAddcount(Integer.parseInt(totalprice));
			jifendata.setSource("余额支付");
			jifendata.setCreatedate(new Date());
			jifendata.setUserid(userDO.getUserId());
			jifendataService.save(jifendata);
			r.put("msg","支付成功！");
		}
		return r;
	}
}
