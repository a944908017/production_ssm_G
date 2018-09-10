package com.wangdao.service.device;

import com.github.pagehelper.PageInfo;
import com.wangdao.bean.device.*;

import java.util.List;


public interface DeviceService {


    PageInfo<DeviceCheck> selectDeviceCheckByPage(int page, int rows, DeviceCheckExample deviceCheckExample);

    PageInfo<Device> selectDeviceByPage(int page, int rows, DeviceExample deviceExample);

    PageInfo<DeviceType> selectDeviceTypeByPage(int page, int rows, DeviceTypeExample deviceTypeExample);

    DeviceType getDeviceTypeForDeviceList(String params);

    PageInfo<DeviceFault> selectDeviceFaultByPage(int page, int rows, DeviceFaultExample deviceFaultExample);

    PageInfo<DeviceMaintain> selectDeviceMaintainByPage(int page, int rows, DeviceMaintainExample deviceMaintainExample);

    Device getDeviceForDeviceCheck(String params);

    DeviceFault getDeviceFaultForDeviceMaintain(String params);


    List<Device> getDeviceData();

    List<DeviceType> getDeviceTypeData();

    boolean insertDevice(Device device);

    boolean updateDevice(Device device);

    boolean deleteDevice(String ids);

    boolean insertDeviceType(DeviceType deviceType);

    boolean deleteDeviceType(String ids);

    boolean updateDeviceType(DeviceType deviceType);

    boolean deleteDeviceCheck(String ids);

    boolean updateDeviceCheck(DeviceCheck deviceCheck);

    boolean insertDeviceCheck(DeviceCheck deviceCheck);

    boolean deleteDeviceFault(String ids);

    boolean updateDeviceFault(DeviceFault deviceFault);

    boolean insertDeviceFault(DeviceFault deviceFault);

    boolean deleteDeviceMaintain(String ids);

    boolean updateDeviceMaintain(DeviceMaintain deviceMaintain);

    boolean insertDeviceMaintain(DeviceMaintain deviceMaintain);

    List<String> getDevcieIdsByDeviceName(String deviceName);

    List<DeviceFault> getDeviceFaultData();
}
