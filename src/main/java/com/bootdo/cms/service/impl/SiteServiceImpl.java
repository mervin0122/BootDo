package com.bootdo.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.cms.dao.SiteDao;
import com.bootdo.cms.domain.SiteDO;
import com.bootdo.cms.service.SiteService;



@Service
public class SiteServiceImpl implements SiteService {
	@Autowired
	private SiteDao siteDao;
	
	@Override
	public SiteDO get(Long id){
		return siteDao.get(id);
	}
	
	@Override
	public List<SiteDO> list(Map<String, Object> map){
		return siteDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return siteDao.count(map);
	}
	
	@Override
	public int save(SiteDO site){
		return siteDao.save(site);
	}
	
	@Override
	public int update(SiteDO site){
		return siteDao.update(site);
	}
	
	@Override
	public int remove(Long id){
		return siteDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return siteDao.batchRemove(ids);
	}
	
}
