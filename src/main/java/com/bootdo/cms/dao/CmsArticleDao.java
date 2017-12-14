package com.bootdo.cms.dao;

import com.bootdo.cms.domain.CmsArticleDO;

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
public interface CmsArticleDao {

	CmsArticleDO get(Long id);
	
	List<CmsArticleDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(CmsArticleDO article);
	
	int update(CmsArticleDO article);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
