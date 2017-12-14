package com.bootdo.cms.dao;

import com.bootdo.cms.domain.SiteDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author zscat
 * @email 951449465@qq.com
 * @date 2017-12-02 18:07:28
 */
@Mapper
public interface SiteDao {

	SiteDO get(Long id);
	
	List<SiteDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(SiteDO site);
	
	int update(SiteDO site);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
