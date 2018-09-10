package com.wangdao.service.technology;

import com.wangdao.bean.technology.Process;

import java.util.List;

public interface ProcessService {

    /**
     * 获取分页的process集合结果
     * @param process
     * @return
     */
    List<Process> findProcess(Process process);

    void removeProcessById(String i);

    void addProcess(Process process);

    Process findProcessById(String processId);

    void modifyProcess(Process process);
}
