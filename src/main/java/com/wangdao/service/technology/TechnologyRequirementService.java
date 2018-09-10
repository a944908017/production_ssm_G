package com.wangdao.service.technology;

import com.wangdao.bean.technology.TechnologyRequirement;

import java.util.List;

public interface TechnologyRequirementService {
    List<TechnologyRequirement> findTechnologyRequirement(TechnologyRequirement technologyRequirement);

    void removeTechnologyRequirementById(String i);

    void addTechnologyRequirement(TechnologyRequirement technologyRequirement);

    TechnologyRequirement findTechnologyRequirementById(String technologyRequirementId);

    void modifyTechnologyRequirement(TechnologyRequirement technologyRequirement);

    List<TechnologyRequirement> searchTechnologyRequirementByTid(String tid);

    List<TechnologyRequirement> findTechnologyRequirementByTechnoList(List<String> tempTid);
}
