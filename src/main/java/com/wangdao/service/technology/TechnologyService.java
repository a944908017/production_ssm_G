package com.wangdao.service.technology;

import com.wangdao.bean.technology.Technology;

import java.util.List;

public interface TechnologyService {
    List<Technology> findTechnology(Technology technology);

    void removeTechnologyById(String i);

    void addTechnology(Technology technology);

    Technology findTechnologyById(String technologyId);

    void modifyTechnology(Technology technology);

    List<Technology> findTechnologyByName(String name);

    List<String> getDeviceIdsByDeviceName(String name);
}
