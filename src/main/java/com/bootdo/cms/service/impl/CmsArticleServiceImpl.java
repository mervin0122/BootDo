package com.bootdo.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.cms.dao.CmsArticleDao;
import com.bootdo.cms.domain.CmsArticleDO;
import com.bootdo.cms.service.CmsArticleService;



@Service
public class CmsArticleServiceImpl implements CmsArticleService {
	@Autowired
	private CmsArticleDao cmsArticleDao;
	
	@Override
	public CmsArticleDO get(Long id){
		return cmsArticleDao.get(id);
	}
	
	@Override
	public List<CmsArticleDO> list(Map<String, Object> map){
		return cmsArticleDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return cmsArticleDao.count(map);
	}
	
	@Override
	public int save(CmsArticleDO article){
		return cmsArticleDao.save(article);
	}
	
	@Override
	public int update(CmsArticleDO article){
		return cmsArticleDao.update(article);
	}
	
	@Override
	public int remove(Long id){
		return cmsArticleDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return cmsArticleDao.batchRemove(ids);
	}
	
}
