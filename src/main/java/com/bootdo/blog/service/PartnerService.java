package com.bootdo.blog.service;

import com.bootdo.blog.domain.Pager;
import com.bootdo.blog.domain.Partner;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* Created by GeneratorFx on 2017-04-10.
*/
public interface PartnerService {

    List<Partner> findAll(Map<String,Object> map);
    int count(Map<String,Object> map);
    int savePartner(Partner partner);

    /**
     * 分页查询好友列表
     * @param pager
     * @param param
     * @return
     */
    List<Partner> loadPartner(Pager pager, String param);

    Partner getPartnerById(Integer id);

    int deletePartner(Integer id);

    int updatePartner(Partner partner);

    void initPage(Pager pager);


    int batchRemove(Long[] ids);

    int updateState(Integer id, int status);
}
