package com.wangdao.service.technology;

import com.wangdao.bean.technology.TechnologyPlan;

import java.util.List;

public interface TechnologyPlanService {
    List<TechnologyPlan> findTechnologyPlan(TechnologyPlan technologyPlan);

    void removeTechnologyPlanById(String i);

    void addTechnologyPlan(TechnologyPlan technologyPlan);

    TechnologyPlan findTechnologyPlanById(String technologyPlanId);

    void modifyTechnologyPlan(TechnologyPlan technologyPlan);

    List<TechnologyPlan> searchTechnologyPlanByTid(String tid);

    List<TechnologyPlan> findTechnologyPlanByTechnoList(List<String> tempTid);
}
