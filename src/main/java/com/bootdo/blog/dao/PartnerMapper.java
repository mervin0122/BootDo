package com.bootdo.blog.dao;

import com.bootdo.blog.domain.Pager;
import com.bootdo.blog.domain.Partner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* Created by GeneratorFx on 2017-04-10.
*/
@Mapper
public interface PartnerMapper {


    List<Partner> findAll(Map<String, Object> params);

    int count(Map<String, Object> params);

    int savePartner(Partner partner);

    /**
     * 分页查询好友列表
     * @param pager 分页条件
     * @param param
     * @return
     */
    List<Partner> loadPartner(@Param("pager") Pager pager, @Param("param") String param);

    Partner getPartnerById(Integer id);

    int deletePartner(Integer id);

    int updatePartner(Partner partner);

    int initPage(Pager pager);

    int batchRemove(Long[] ids);

    int updateState(@Param("id") Integer id, @Param("status") int status);
}
