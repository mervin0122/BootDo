package com.bootdo.shop.service.impl;

import com.bootdo.shop.Constan;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.service.UserService;
import com.bootdo.wap.MemberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bootdo.shop.dao.JifendataDao;
import com.bootdo.shop.domain.JifendataDO;
import com.bootdo.shop.service.JifendataService;



@Service
public class JifendataServiceImpl implements JifendataService {
	@Autowired
	private JifendataDao jifendataDao;
	@Autowired
	private UserService userService;
	@Override
	public JifendataDO get(Long id){
		return jifendataDao.get(id);
	}
	
	@Override
	public List<JifendataDO> list(Map<String, Object> map){
		return jifendataDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return jifendataDao.count(map);
	}
	
	@Override
	public int save(JifendataDO jifendata){
		return jifendataDao.save(jifendata);
	}
	@Override
	public int save(Constan addjifen){
		UserDO m = MemberUtils.getSessionLoginUser();
		if (m!=null){
			JifendataDO jifendata = new JifendataDO();
			jifendata.setAddcount(addjifen.getCode());
			jifendata.setSource(addjifen.getMsg());
			jifendata.setCreatedate(new Date());
			jifendata.setUserid(m.getUserId());
			m.setJifen(m.getJifen()+addjifen.getCode());
			userService.updateOnly(m);
			return jifendataDao.save(jifendata);
		}
		return 1;
	}
	
	@Override
	public int update(JifendataDO jifendata){
		return jifendataDao.update(jifendata);
	}
	
	@Override
	public int remove(Long id){
		return jifendataDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return jifendataDao.batchRemove(ids);
	}
	
}
