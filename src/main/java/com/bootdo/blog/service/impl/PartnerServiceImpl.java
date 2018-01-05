package com.bootdo.blog.service.impl;


import com.bootdo.blog.dao.PartnerMapper;
import com.bootdo.blog.service.PartnerService;
import com.bootdo.blog.domain.Pager;
import com.bootdo.blog.domain.Partner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by GeneratorFx on 2017-04-10.
 */
@Service
@Transactional
public class PartnerServiceImpl implements PartnerService {

    @Resource
    private PartnerMapper partnerMapper;


    @Override
    public List<Partner> findAll(Map<String,Object> map) {
        return partnerMapper.findAll(map);
    }

    @Override
    public int count(Map<String,Object> map) {
        return partnerMapper.count(map);
    }

    @Override
    public int savePartner(Partner partner) {
        return partnerMapper.savePartner(partner);
    }

    @Override
    public List<Partner> loadPartner(Pager pager, String param) {
        pager.setStart(pager.getStart());
        return partnerMapper.loadPartner(pager,param);
    }

    @Override
    public Partner getPartnerById(Integer id) {
        return partnerMapper.getPartnerById(id);
    }

    @Override
    public int deletePartner(Integer id) {
        return partnerMapper.deletePartner(id);
    }

    @Override
    public int updatePartner(Partner partner) {
        return  partnerMapper.updatePartner(partner);
    }

    @Override
    public void initPage(Pager pager) {
        int count = partnerMapper.initPage(pager);
        pager.setTotalCount(count);
    }
    @Override
    public int batchRemove(Long[] ids){
        return  partnerMapper.batchRemove(ids);
    }

    public int updateState(Integer id, int status){
        return   partnerMapper.updateState(id,status);
    }



}
