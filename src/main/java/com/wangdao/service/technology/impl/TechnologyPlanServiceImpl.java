package com.wangdao.service.technology.impl;


import com.wangdao.bean.technology.TechnologyPlan;
import com.wangdao.dao.technology.TechnologyPlanMapper;
import com.wangdao.service.technology.TechnologyPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
@Service("technologyPlanService")
public class TechnologyPlanServiceImpl implements TechnologyPlanService {

    @Autowired
    private TechnologyPlanMapper technologyPlanMapper;

    @Transactional(readOnly=true)
    @Override
    public List<TechnologyPlan> findTechnologyPlan(TechnologyPlan technologyPlan) {
        Map<String,Object> params=new HashMap<>();
        params.put("technologyPlan",technologyPlan);


        return technologyPlanMapper.selectByPage(params);
    }

    @Override
    public void removeTechnologyPlanById(String i) {
        technologyPlanMapper.deleteByPrimaryKey(i);
    }

    @Override
    public void addTechnologyPlan(TechnologyPlan technologyPlan) {
        technologyPlanMapper.insert(technologyPlan);
    }

    @Transactional(readOnly=true)
    @Override
    public TechnologyPlan findTechnologyPlanById(String technologyPlanId) {
        return technologyPlanMapper.selectByPrimaryKey(technologyPlanId);
    }

    @Override
    public void modifyTechnologyPlan(TechnologyPlan technologyPlan) {
        technologyPlanMapper.updateByPrimaryKey(technologyPlan);
    }

    @Override
    public List<TechnologyPlan> searchTechnologyPlanByTid(String tid) {
        return technologyPlanMapper.searchByTechnologyId(tid);
    }

    @Override
    public List<TechnologyPlan> findTechnologyPlanByTechnoList(List<String> tempTid) {
        return technologyPlanMapper.seachByTechnologyIdList(tempTid);
    }
}
