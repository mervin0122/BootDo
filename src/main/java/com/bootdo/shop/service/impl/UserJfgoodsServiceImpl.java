package com.bootdo.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.shop.dao.UserJfgoodsDao;
import com.bootdo.shop.domain.UserJfgoodsDO;
import com.bootdo.shop.service.UserJfgoodsService;



@Service
public class UserJfgoodsServiceImpl implements UserJfgoodsService {
	@Autowired
	private UserJfgoodsDao userJfgoodsDao;
	
	@Override
	public UserJfgoodsDO get(Long id){
		return userJfgoodsDao.get(id);
	}
	
	@Override
	public List<UserJfgoodsDO> list(Map<String, Object> map){
		return userJfgoodsDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return userJfgoodsDao.count(map);
	}
	
	@Override
	public int save(UserJfgoodsDO userJfgoods){
		return userJfgoodsDao.save(userJfgoods);
	}
	
	@Override
	public int update(UserJfgoodsDO userJfgoods){
		return userJfgoodsDao.update(userJfgoods);
	}
	
	@Override
	public int remove(Long id){
		return userJfgoodsDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return userJfgoodsDao.batchRemove(ids);
	}
	
}
