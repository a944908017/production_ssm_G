package com.wangdao.service.technology.impl;

import com.wangdao.bean.technology.Technology;
import com.wangdao.dao.technology.TechnologyMapper;
import com.wangdao.service.technology.TechnologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
@Service("technologyService")
public class TechnologyServiceImpl implements TechnologyService {

    @Autowired
    private TechnologyMapper technologyMapper;

    @Transactional(readOnly=true)
    @Override
    public List<Technology> findTechnology(Technology technology) {
        Map<String,Object> params=new HashMap<>();
        params.put("technology",technology);


        return technologyMapper.selectByPage(params);
    }

    @Override
    public void removeTechnologyById(String i) {
        technologyMapper.deleteByPrimaryKey(i);
    }

    @Override
    public void addTechnology(Technology technology) {
        technologyMapper.insert(technology);
    }

    @Transactional(readOnly=true)
    @Override
    public Technology findTechnologyById(String technologyId) {
        return technologyMapper.selectByPrimaryKey(technologyId);
    }

    @Override
    public void modifyTechnology(Technology technology) {
        technologyMapper.updateByPrimaryKeySelective(technology);
    }

    @Override
    public List<Technology> findTechnologyByName(String name) {
       return technologyMapper.searchName(name);
    }

    @Override
    public List<String> getDeviceIdsByDeviceName(String name) {
        return technologyMapper.searchNameReturnId(name);
    }


}
