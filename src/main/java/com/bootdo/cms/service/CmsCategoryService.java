package com.bootdo.cms.service;

import com.bootdo.cms.domain.CategoryDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zscat
 * @email 951449465@qq.com
 * @date 2017-12-02 18:07:28
 */
public interface CmsCategoryService {
	
	CategoryDO get(Long id);
	
	List<CategoryDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(CategoryDO category);
	
	int update(CategoryDO category);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
