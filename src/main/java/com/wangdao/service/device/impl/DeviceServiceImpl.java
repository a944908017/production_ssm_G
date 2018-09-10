package com.wangdao.service.device.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangdao.bean.device.*;
import com.wangdao.dao.device.*;
import com.wangdao.service.device.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
@Service("deviceService")
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceCheckMapper deviceCheckMapper;
    @Autowired
    private DeviceFaultMapper deviceFaultMapper;
    @Autowired
    private DeviceMaintainMapper deviceMaintainMapper;
    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private DeviceTypeMapper deviceTypeMapper;



    /**
     * 利用分页插件 条件查询 Device
     * @param page
     * @param rows
     * @param deviceExample 带有deviceType具体查询数据
     * @return
     */
    @Override
    public PageInfo<Device> selectDeviceByPage(int page, int rows, DeviceExample deviceExample) {
        PageHelper.startPage(page, rows);
        List<Device> devices = deviceMapper.selectByExample(deviceExample);
        PageInfo<Device> pageInfo = new PageInfo<>(devices);
        return pageInfo;
    }


    /**
     * 利用分页插件条件查询 DeviceType
     * @param page
     * @param rows
     * @param deviceTypeExample
     * @return
     */
    @Override
    public PageInfo<DeviceType> selectDeviceTypeByPage(int page, int rows, DeviceTypeExample deviceTypeExample) {
        PageHelper.startPage(page, rows);
        List<DeviceType> deviceTypes = deviceTypeMapper.selectByExample(deviceTypeExample);
        PageInfo<DeviceType> pageInfo = new PageInfo<>(deviceTypes);
        return pageInfo;
    }


    /**
     * 利用分页插件条件查询 DeviceFault
     * @param page
     * @param rows
     * @param deviceFaultExample
     * @return
     */
    @Override
    public PageInfo<DeviceFault> selectDeviceFaultByPage(int page, int rows, DeviceFaultExample deviceFaultExample) {
        PageHelper.startPage(page, rows);
        List<DeviceFault> deviceFaults = deviceFaultMapper.selectByExample(deviceFaultExample);
        PageInfo<DeviceFault> pageInfo = new PageInfo<>(deviceFaults);
        return pageInfo;
    }

    /**
     * 利用分页插件条件查询 DeviceMaintain
     * @param page
     * @param rows
     * @param deviceMaintainExample
     * @return
     */
    @Override
    public PageInfo<DeviceMaintain> selectDeviceMaintainByPage(int page, int rows, DeviceMaintainExample deviceMaintainExample) {
        PageHelper.startPage(page, rows);
        List<DeviceMaintain> deviceMaintains = deviceMaintainMapper.selectByExample(deviceMaintainExample);
        PageInfo<DeviceMaintain> pageInfo = new PageInfo<>(deviceMaintains);
        return pageInfo;
    }

    /**
     * 利用分页插件条件查询 DeviceCheck
     * @param page
     * @param rows
     * @return
     */
    @Override
    public PageInfo<DeviceCheck> selectDeviceCheckByPage(int page, int rows, DeviceCheckExample deviceCheckExample) {
        PageHelper.startPage(page, rows);
        List<DeviceCheck> deviceChecks = deviceCheckMapper.selectByExample(deviceCheckExample);
        PageInfo<DeviceCheck> pageInfo = new PageInfo<>(deviceChecks);
        return pageInfo;
    }

    /**
     * 在DeviceList中显示DeviceType信息
     * @param params
     * @return
     */
    @Override
    public DeviceType getDeviceTypeForDeviceList(String params) {
        DeviceType deviceType = deviceTypeMapper.selectByPrimaryKey(params);
        return deviceType;
    }

    /**
     * 在DeviceCheck中显示Device信息
     * @param params
     * @return
     */
    @Override
    public Device getDeviceForDeviceCheck(String params){
        Device device = deviceMapper.selectByPrimaryKey(params);
        return device;
    }

    @Override
    public DeviceFault getDeviceFaultForDeviceMaintain(String params){
        DeviceFault deviceFault=deviceFaultMapper.selectByPrimaryKey(params);
        return deviceFault;
    }

    /**
     * 这个查找到所有的device数据
     * @return
     */
    @Override
    public List<Device> getDeviceData() {
        List<Device> devices = deviceMapper.selectByExample(null);
        return devices;
    }

    /**
     * 查找所有的deviceType数据
     * @return
     */
    @Override
    public List<DeviceType> getDeviceTypeData() {
       List<DeviceType> deviceTypes=deviceTypeMapper.selectByExample(null);
       return deviceTypes;
    }

    /**
     * 新增设备台账 Device
     * @param device
     */
    @Override
    public boolean insertDevice(Device device) {
        //选择性插入，只插入device里面不为null的字段
        int i=0;
        try {
            i = deviceMapper.insertSelective(device);
        }catch (Exception e){
            return false;
        }
        if (i>0){
            return true;
        }
        return false;
    }

    /**
     * 更新设备台账 Device
     * @param device
     * @return
     */
    @Override
    public boolean updateDevice(Device device) {
        int i = deviceMapper.updateByPrimaryKeySelective(device);
        if (i>0){
            return true;
        }
        return false;
    }

    /**
     * 删除设备台账 device
     * @param ids
     * @return
     */
    //Todo 这里的触发事务进行回滚的方式是否可以再优化？
    @Override
    public boolean deleteDevice(String ids){
        //返回标志
        String[] idArray=ids.split(",");
        for (String id:idArray){
            //根据id删除设备台账
            int i = deviceMapper.deleteByPrimaryKey(id);
            if (i==0){
                //有一个没有删除成功，就人为制造错误让其回滚
                int m=1/0;
            }
        }
       return true;
    }

    /**
     * 新增设备种类操作 devicetype
     * @param deviceType
     * @return
     */
    @Override
    public boolean insertDeviceType(DeviceType deviceType) {
        int i = deviceTypeMapper.insertSelective(deviceType);
        if (i>0){
            return true;
        }
        return false;
    }

    /**
     * 删除设备种类操作 devicetype
     * @param ids
     * @return
     */
    @Override
    public boolean deleteDeviceType(String ids) {
        String[] idArray=ids.split(",");
        for (String id:idArray){
            //根据id删除设备台账
            int i = deviceTypeMapper.deleteByPrimaryKey(id);
            if (i==0){
                //有一个没有删除成功，就人为制造错误让其回滚
                int m=1/0;
            }
        }
        return true;
    }

    /**
     * 更新设备种类 DeviceType
     * @param deviceType
     * @return
     */
    @Override
    public boolean updateDeviceType(DeviceType deviceType) {
        int i = deviceTypeMapper.updateByPrimaryKeySelective(deviceType);
        if (i>0){
            return true;
        }
        return false;
    }

    /**
     * 删除设备例检
     * @param ids
     * @return
     */
    @Override
    public boolean deleteDeviceCheck(String ids) {
        String[] idArray=ids.split(",");
        for (String id:idArray){
            //根据id删除设备台账
            int i = deviceCheckMapper.deleteByPrimaryKey(id);
            if (i==0){
                //有一个没有删除成功，就人为制造错误让其回滚
                int m=1/0;
            }
        }
        return true;
    }

    /**
     * 更新设备例检
     * @return
     */
    @Override
    public boolean updateDeviceCheck(DeviceCheck deviceCheck) {
        int i = deviceCheckMapper.updateByPrimaryKeySelective(deviceCheck);
        if (i>0){
            return true;
        }
        return false;
    }

    /**
     * 新增设备例检
     * @param deviceCheck
     * @return
     */
    @Override
    public boolean insertDeviceCheck(DeviceCheck deviceCheck) {
        int i = deviceCheckMapper.insertSelective(deviceCheck);
        if (i>0){
            return true;
        }
        return false;
    }

    /**
     * 删除设备故障
     * @param ids
     * @return
     */
    @Override
    public boolean deleteDeviceFault(String ids) {
        String[] idArray=ids.split(",");
        for (String id:idArray){
            //根据id删除设备故障
            int i = deviceFaultMapper.deleteByPrimaryKey(id);
            if (i==0){
                //有一个没有删除成功，就人为制造错误让其回滚
                int m=1/0;
            }
        }
        return true;
    }

    /**
     * 更新设备故障
     * @param deviceFault
     * @return
     */
    @Override
    public boolean updateDeviceFault(DeviceFault deviceFault) {
        int i = deviceFaultMapper.updateByPrimaryKeySelective(deviceFault);
        if(i>0){
            return true;
        }
        return false;


    }

    /**
     * 新增设备故障
     * @param deviceFault
     * @return
     */
    @Override
    public boolean insertDeviceFault(DeviceFault deviceFault) {
        int i = deviceFaultMapper.insertSelective(deviceFault);
        if (i>0){
            return true;
        }
        return false;
    }

    /**
     * 删除设备维修
     * @param ids
     * @return
     */
    @Override
    public boolean deleteDeviceMaintain(String ids) {
        String[] idArray=ids.split(",");
        for (String id:idArray){
            //根据id删除设备维修
            int i = deviceMaintainMapper.deleteByPrimaryKey(id);
            if (i==0){
                //有一个没有删除成功，就人为制造错误让其回滚
                int m=1/0;
            }
        }
        return true;
    }

    /**
     * 更新设备维修
     * @param deviceMaintain
     * @return
     */
    @Override
    public boolean updateDeviceMaintain(DeviceMaintain deviceMaintain) {
        int i = deviceMaintainMapper.updateByPrimaryKeySelective(deviceMaintain);
        if (i>0){
            return true;
        }
        return false;
    }

    /**
     * 新增设备维修
     * @param deviceMaintain
     * @return
     */
    @Override
    public boolean insertDeviceMaintain(DeviceMaintain deviceMaintain) {
        int i = deviceMaintainMapper.insertSelective(deviceMaintain);
        if (i>0){
            return true;
        }
        return false;
    }

    /**给其他模块的方法调用**/

    /**
     * 根据Devciename查询到对应的ProductIds
     * @param deviceName
     * @return
     */
    @Override
    public List<String> getDevcieIdsByDeviceName(String deviceName){
        List<String> devcieIdsByDeviceName = deviceMapper.getDeviceIdsByDeviceName(deviceName);
        return devcieIdsByDeviceName;
    }

    @Override
    public List<DeviceFault> getDeviceFaultData() {
        return deviceFaultMapper.selectByExample(null);
    }
}
