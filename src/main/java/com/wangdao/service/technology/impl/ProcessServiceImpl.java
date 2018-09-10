package com.wangdao.service.technology.impl;

import com.wangdao.bean.technology.Process;
import com.wangdao.dao.technology.ProcessMapper;
import com.wangdao.service.technology.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
@Service("processService")
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    private ProcessMapper processMapper;

    @Transactional(readOnly=true)
    @Override
    public List<Process> findProcess(Process process) {
        Map<String,Object> params=new HashMap<>();
        params.put("process",process);


        return processMapper.selectByPage(params);


    }

    @Override
    public void removeProcessById(String i) {
        processMapper.deleteByPrimaryKey(i);
    }

    @Override
    public void addProcess(Process process) {
        processMapper.insert(process);
    }

    @Transactional(readOnly=true)
    @Override
    public Process findProcessById(String processId) {
        return processMapper.selectByPrimaryKey(processId);
    }

    @Override
    public void modifyProcess(Process process) {
        processMapper.updateByPrimaryKey(process);
    }
}
