package com.wangdao.service.technology.impl;

import com.wangdao.bean.technology.TechnologyRequirement;
import com.wangdao.dao.technology.TechnologyRequirementMapper;
import com.wangdao.service.technology.TechnologyRequirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
@Service("technologyRequirementService")
public class TechnologyRequirementServiceImpl implements TechnologyRequirementService {

    @Autowired
    private TechnologyRequirementMapper technologyRequirementMapper;

    @Override
    public List<TechnologyRequirement> findTechnologyRequirement(TechnologyRequirement technologyRequirement) {
        Map<String,Object> params=new HashMap<>();
        params.put("technologyRequirement",technologyRequirement);

        return technologyRequirementMapper.selectByPage(params);
    }

    @Override
    public void removeTechnologyRequirementById(String i) {
        technologyRequirementMapper.deleteByPrimaryKey(i);
    }

    @Override
    public void addTechnologyRequirement(TechnologyRequirement technologyRequirement) {
        technologyRequirementMapper.insert(technologyRequirement);
    }

    @Override
    public TechnologyRequirement findTechnologyRequirementById(String technologyRequirementId) {
        return technologyRequirementMapper.selectByPrimaryKey(technologyRequirementId);
    }

    @Override
    public void modifyTechnologyRequirement(TechnologyRequirement technologyRequirement) {
        technologyRequirementMapper.updateByPrimaryKey(technologyRequirement);
    }

    @Override
    public List<TechnologyRequirement> searchTechnologyRequirementByTid(String tid) {
        return technologyRequirementMapper.searchByTechnologyId(tid);
    }

    @Override
    public List<TechnologyRequirement> findTechnologyRequirementByTechnoList(List<String> tempTid) {
        return technologyRequirementMapper.seachByTechnologyIdList(tempTid);
    }
}
