package com.bootdo.cms.dao;

import com.bootdo.cms.domain.LinkDO;

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
public interface LinkDao {

	LinkDO get(Long id);
	
	List<LinkDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(LinkDO link);
	
	int update(LinkDO link);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
