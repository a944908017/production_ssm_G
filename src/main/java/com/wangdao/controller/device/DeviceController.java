package com.wangdao.controller.device;


import com.github.pagehelper.PageInfo;
import com.wangdao.bean.device.*;
import com.wangdao.service.device.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.*;


@Controller
public class DeviceController {

    @Autowired
    @Qualifier("deviceService")
    private DeviceService deviceService;

    /**
     * 对时间格式进行转换处理
     * @param binder
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }


    /****************************以下是Device的相关内容****************************/

    /**
     * 返回分页查找数据
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("deviceList/list")
    @ResponseBody
    private  Map findDeviceList(String page, String rows, DeviceExample deviceExample){
        Map map=new HashMap();
        if (page==null||page.equals("")){
            page="1";
        }
        if (rows==null||rows.equals("")){
            rows="30";
        }
        PageInfo<Device> devicePageInfo = deviceService.selectDeviceByPage(Integer.parseInt(page),
                Integer.parseInt(rows),deviceExample);
        map.put("total",devicePageInfo.getTotal());
        map.put("rows",devicePageInfo.getList());
        return map;
    }


    /**
     * 用来跳转到deviceList.jsp页面
     * @param mv
     * @return
     */
    @RequestMapping("device/deviceList")
    public ModelAndView deviceListFind(ModelAndView mv){

        mv.setViewName("deviceList");
        return  mv;
    }

    /**
     * 新增设备台账前的预判断
     * @return
     */
    @RequestMapping("deviceList/add_judge")
    @ResponseBody
    public String addDeviceJudge(){
        return null;
    }

    /**
     * 跳转到新增设备页面
     * @return
     */
    @RequestMapping("deviceList/add")
    public ModelAndView addDevice(ModelAndView mv){
        mv.setViewName("deviceList_add");
        return mv;
    }

    /**
     * 执行新增操作
     */
    @RequestMapping("deviceList/insert")
    @ResponseBody
    public Map insertDevice(@ModelAttribute Device device){
        Map map=new HashMap();
        boolean b = deviceService.insertDevice(device);
        if (b) {
            map.put("status", 200);
        }else{
            map.put("msg","修改失败");
        }
        return map;
    }

    /**
     * 编辑设备台账前的预判断
     * @return
     */
    @RequestMapping("deviceList/edit_judge")
    @ResponseBody
    public String editDeviceJudge(){
        return null;
    }

    /**
     * 跳转到编辑设备页面
     * @return
     */
    @RequestMapping("deviceList/edit")
    public ModelAndView editDevice(ModelAndView mv){
        mv.setViewName("deviceList_edit");
        return mv;
    }

    /**
     * 执行更新操作
     * @param
     */
    @RequestMapping(value = {"deviceList/update","deviceList/update_all","deviceList/update_note"})
    @ResponseBody
    public Map updateDevice(@ModelAttribute Device device){
        Map map=new HashMap();
        boolean b = deviceService.updateDevice(device);
        if (b) {
            map.put("status", 200);
        }else{
            map.put("msg","修改失败");
        }
        return map;
    }

    /**
     * 删除设备台账前的预判断
     * @return
     */
    @RequestMapping("deviceList/delete_judge")
    @ResponseBody
    public String deleteDeviceJudge(){
        return null;
    }

    /**
     * 执行删除操作
     */
    @RequestMapping("deviceList/delete_batch")
    @ResponseBody
    public Map deleteDevice(String ids){
        Map map=new HashMap();
        boolean b = deviceService.deleteDevice(ids);
        if (b){
            map.put("status", 200);
        }else{
            map.put("msg","删除失败！");
        }
        return map;
    }

    /**
     * 通过设备id来模糊搜索
     * @param page
     * @param rows
     * @param searchValue
     * @return
     */
    @RequestMapping("deviceList/search_device_by_deviceId")
    @ResponseBody
    public Map findDeviceListByDeviceId(String page, String rows,String searchValue){

        DeviceExample deviceExample=new DeviceExample();
        DeviceExample.Criteria criteria = deviceExample.createCriteria();
        criteria.andDeviceIdLike("%"+searchValue+"%");
        return findDeviceList(page,rows,deviceExample);

    }

    /**
     * 通过设备名称来模糊搜索
     * @param page
     * @param rows
     * @param searchValue
     * @return
     */
    @RequestMapping("deviceList/search_device_by_deviceName")
    @ResponseBody
    public Map findDeviceListByDeviceName(String page, String rows,String searchValue){
        DeviceExample deviceExample=new DeviceExample();
        DeviceExample.Criteria criteria = deviceExample.createCriteria();
        criteria.andDeviceNameLike("%"+searchValue+"%");
        return findDeviceList(page,rows,deviceExample);
    }

    /**
     * 通过设备类型名称来模糊搜索
     * @param page
     * @param rows
     * @param searchValue
     * @return
     */
    @RequestMapping("deviceList/search_device_by_deviceTypeName")
    @ResponseBody
    public Map findDeviceListByDeviceTypeName(String page, String rows,String searchValue){
        //获取所搜索的种类名称对应的id
        Map deviceTypeByDeviceTypeName = findDeviceTypeByDeviceTypeName(page, rows, searchValue);
        List<DeviceType> deviceTypes = (List<DeviceType>) deviceTypeByDeviceTypeName.get("rows");
        List<String> conditions=new ArrayList<String>();
        Iterator<DeviceType> iterator;
        for(iterator=deviceTypes.iterator();iterator.hasNext();){
            DeviceType next = iterator.next();
            String deviceTypeId = next.getDeviceTypeId();
            conditions.add(deviceTypeId);
        }

        //如果取不到对应搜索的deviceTypeId的值 构建一个返回Map形式
        if (conditions.size()==0){
            Map map=new HashMap();
            map.put("total",0);
            map.put("rows",conditions);
            return map;
        }

        //现在要把id数据都查出来，作为下一步查询条件
        DeviceExample deviceExample=new DeviceExample();
        DeviceExample.Criteria criteria = deviceExample.createCriteria();
        criteria.andDeviceTypeIdIn(conditions);
        return findDeviceList(page,rows,deviceExample);
    }

    /**
     * 根据DeviceTypeId获取对应的DeviceType数据
     * @param params
     * @return
     */
    @RequestMapping({"deviceType/get/{params}"})
    @ResponseBody
    public DeviceType getDeviceTypeForDeviceList(@PathVariable String params){

        DeviceType deviceType = deviceService.getDeviceTypeForDeviceList(params);

        return deviceType;
    }



    /**
     * 获取device的全部数据
     * @return
     */
    @RequestMapping("deviceList/get_data")
    @ResponseBody
    public List<Device> getDeviceData(){
        List<Device> devcies=deviceService.getDeviceData();
        System.out.println("getDeviceData/devcies="+devcies);
        return devcies;
    }



    /****************************以下是deviceCheck的相关内容****************************/

    /**
     * 用来跳转到deviceCheck.jsp页面
     * @param mv
     * @return
     */
    @RequestMapping("device/deviceCheck")
    public ModelAndView deviceCheckFind(ModelAndView mv){

        mv.setViewName("deviceCheck");
        return  mv;
    }

    /**
     * 返回分页查找数据
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("deviceCheck/list")
    @ResponseBody
    public Map findDeviceCheck(String page, String rows,DeviceCheckExample deviceCheckExample){

        Map map=new HashMap();
        if (page==null||page.equals("")){
            page="1";
        }
        if (rows==null||rows.equals("")){
            rows="30";
        }
        PageInfo<DeviceCheck> devicePageInfo = deviceService.selectDeviceCheckByPage(Integer.parseInt(page),
                Integer.parseInt(rows),deviceCheckExample);
        map.put("total",devicePageInfo.getTotal());
        map.put("rows",devicePageInfo.getList());
        return map;
    }


    /**
     * 新增设备例检前的预判断
     * @return
     */
    @RequestMapping("deviceCheck/add_judge")
    @ResponseBody
    public String addDeviceCheckJudge(){
        return null;
    }

    /**
     * 跳转到设备例检页面
     * @return
     */
    @RequestMapping("deviceCheck/add")
    public ModelAndView addDeviceCheck(ModelAndView mv){
        mv.setViewName("deviceCheck_add");
        return mv;
    }

    /**
     * 执行新增设备例检操作
     */
    @RequestMapping("deviceCheck/insert")
    @ResponseBody
    public Map insertDeviceCheck(@ModelAttribute DeviceCheck deviceCheck){
        Map map=new HashMap();
        boolean b = deviceService.insertDeviceCheck(deviceCheck);
        if (b) {
            map.put("status", 200);
        }else{
            map.put("msg","修改失败");
        }
        return map;
    }

    /**
     * 编辑设备例检前的预判断
     * @return
     */
    @RequestMapping("deviceCheck/edit_judge")
    @ResponseBody
    public String editDeviceCheckJudge(){
        return null;
    }


    /**
     * 跳转到编辑设备例检页面
     * @return
     */
    @RequestMapping("deviceCheck/edit")
    public ModelAndView editDeviceCheck(ModelAndView mv){
        mv.setViewName("deviceCheck_edit");
        return mv;
    }

    /**
     * 执行更新设备例检操作
     * @param
     */
    @RequestMapping(value = {"deviceCheck/update","deviceCheck/update_note"})
    @ResponseBody
    public Map updateDeviceCheck(@ModelAttribute DeviceCheck deviceCheck){
        Map map=new HashMap();
        boolean b = deviceService.updateDeviceCheck(deviceCheck);
        if (b) {
            map.put("status", 200);
        }else{
            map.put("msg","修改失败");
        }
        return map;
    }
    /**
     * 删除设备例检前的预判断
     * @return
     */
    @RequestMapping("deviceCheck/delete_judge")
    @ResponseBody
    public String deleteDeviceCheckJudge(){
        return null;
    }

    /**
     * 执行删除设备例检操作
     */
    @RequestMapping("deviceCheck/delete_batch")
    @ResponseBody
    public Map deleteDeviceCheck(String ids){
        Map map=new HashMap();
        boolean b = deviceService.deleteDeviceCheck(ids);
        if (b){
            map.put("status", 200);
        }else{
            map.put("msg","删除失败！");
        }
        return map;
    }
    /**
     * 根据DeviceTypeId获取对应的DeviceType数据
     * @param params
     * @return
     */
    @RequestMapping({"deviceType/get_data/{params}"})
    @ResponseBody
    public DeviceType getDeviceType(@PathVariable String params){

        DeviceType deviceType = deviceService.getDeviceTypeForDeviceList(params);

        return deviceType;
    }

    /**
     * 根据DeviceId获取对应的Device数据
     * @param params
     * @return
     */
    @RequestMapping({"deviceList/get/{params}"})
    @ResponseBody
    public Device getDevice(@PathVariable String params){

        Device device = deviceService.getDeviceForDeviceCheck(params);

        return device;
    }

    /**
     * 通过设备deviceCheckId来模糊搜索DeviceCheck信息
     * @param page
     * @param rows
     * @param searchValue
     * @return
     */
    @RequestMapping("deviceCheck/search_deviceCheck_by_deviceCheckId")
    @ResponseBody
    public Map searchDeviceCheckByDeviceCheckId(String page, String rows,String searchValue){

        DeviceCheckExample deviceCheckExample=new DeviceCheckExample();
        DeviceCheckExample.Criteria criteria = deviceCheckExample.createCriteria();
        criteria.andDeviceCheckIdLike("%"+searchValue+"%");
        return findDeviceCheck(page,rows,deviceCheckExample);
    }

    /**
     * 通过设备名称来模糊搜索 search_deviceCheck_by_deviceName
     * @param page
     * @param rows
     * @param searchValue
     * @return
     */
    @RequestMapping("deviceCheck/search_deviceCheck_by_deviceName")
    @ResponseBody
    public Map searchDeviceCheckByDeviceName(String page, String rows,String searchValue){
        //获取所搜索的设备名称对应的id
        Map deviceListByDeviceName = findDeviceListByDeviceName(page, rows, searchValue);
        List<Device> devices = (List<Device>) deviceListByDeviceName.get("rows");
        List<String> conditions=new ArrayList<String>();
        Iterator<Device> iterator;
        for(iterator=devices.iterator();iterator.hasNext();){
            Device next = iterator.next();
            String deviceId = next.getDeviceId();
            conditions.add(deviceId);
        }
        //如果取不到对应搜索的deviceId的值 构建一个返回Map形式
        if (conditions.size()==0){
            Map map=new HashMap();
            map.put("total",0);
            map.put("rows",conditions);
            return map;
        }
        //现在要把id数据都查出来，作为下一步查询条件
        DeviceCheckExample deviceCheckExample=new DeviceCheckExample();
        DeviceCheckExample.Criteria criteria = deviceCheckExample.createCriteria();
        criteria.andDeviceIdIn(conditions);
        return findDeviceCheck(page,rows,deviceCheckExample);
    }



    /****************************以下是deviceType的相关内容****************************/

    /**
     * 用来跳转到deviceType.jsp页面
     * @param mv
     * @return
     */
    @RequestMapping("device/deviceType")
    public ModelAndView deviceDeviceType(ModelAndView mv){

        mv.setViewName("deviceType");
        return  mv;
    }

    /**
     * 返回分页查找数据
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("deviceType/list")
    @ResponseBody
    public Map findDeviceType(String page, String rows,DeviceTypeExample deviceTypeExample){

        Map map=new HashMap();
        if (page==null||page.equals("")){
            page="1";
        }
        if (rows==null||rows.equals("")){
            rows="30";
        }
        PageInfo<DeviceType> devicePageInfo = deviceService.selectDeviceTypeByPage(Integer.parseInt(page),
                Integer.parseInt(rows),deviceTypeExample);
        map.put("total",devicePageInfo.getTotal());
        map.put("rows",devicePageInfo.getList());
        return map;
    }

    /**
     * 新增设备种类前的预判断
     * @return
     */
    @RequestMapping("deviceType/add_judge")
    @ResponseBody
    public String addDeviceTypeJudge(){
        return null;
    }

    /**
     * 跳转到新增设备页面
     * @return
     */
    @RequestMapping("deviceType/add")
    public ModelAndView addDeviceType(ModelAndView mv){
        mv.setViewName("deviceType_add");
        return mv;
    }

    /**
     * 执行新增操作
     */
    @RequestMapping("deviceType/insert")
    @ResponseBody
    public Map insertDeviceType(@ModelAttribute DeviceType deviceType){
        Map map=new HashMap();
        boolean b = deviceService.insertDeviceType(deviceType);
        if (b) {
            map.put("status", 200);
        }else{
            map.put("msg","修改失败");
        }
        return map;
    }

    /**
     * 编辑设备台账前的预判断
     * @return
     */
    @RequestMapping("deviceType/edit_judge")
    @ResponseBody
    public String editDeviceTypeJudge(){
        return null;
    }


    /**
     * 跳转到编辑设备页面
     * @return
     */
    @RequestMapping("deviceType/edit")
    public ModelAndView editDeviceType(ModelAndView mv){
        mv.setViewName("deviceType_edit");
        return mv;
    }

    /**
     * 执行更新操作
     * @param
     */
    @RequestMapping(value = {"deviceType/update","deviceType/update_all","deviceType/update_note"})
    @ResponseBody
    public Map updateDeviceType(@ModelAttribute DeviceType deviceType){
        Map map=new HashMap();
        boolean b = deviceService.updateDeviceType(deviceType);
        if (b) {
            map.put("status", 200);
        }else{
            map.put("msg","修改失败");
        }
        return map;
    }


    /**
     * 删除设备台账前的预判断
     * @return
     */
    @RequestMapping("deviceType/delete_judge")
    @ResponseBody
    public String deleteDeviceTypeJudge(){
        return null;
    }

    /**
     * 执行删除操作
     */
    @RequestMapping("deviceType/delete_batch")
    @ResponseBody
    public Map deleteDeviceType(String ids){
        Map map=new HashMap();
        boolean b = deviceService.deleteDeviceType(ids);
        if (b){
            map.put("status", 200);
        }else{
            map.put("msg","删除失败！");
        }
        return map;
    }

    /**
     * 获取所有的deviceType数据
     * @return
     */
    @RequestMapping("deviceType/get_data")
    @ResponseBody
    public List<DeviceType> getDeviceTypeData(){
        List<DeviceType> deviceTypes=deviceService.getDeviceTypeData();
        return deviceTypes;
    }

    /**
     * 获取所有的deviceType数据
     * @return
     */
    @RequestMapping("deviceFault/get_data")
    @ResponseBody
    public List<DeviceFault> getDeviceFaultData(){
        List<DeviceFault> deviceFaults=deviceService.getDeviceFaultData();
        return deviceFaults;
    }

    //搜索内容

    /**
     *  search_deviceType_by_deviceTypeName
     * @param page
     * @param rows
     * @param searchValue
     * @return
     */
    @RequestMapping("deviceType/search_deviceType_by_deviceTypeName")
    @ResponseBody
    public Map findDeviceTypeByDeviceTypeName(String page, String rows,String searchValue){
        DeviceTypeExample deviceTypeExample=new DeviceTypeExample();
        DeviceTypeExample.Criteria criteria = deviceTypeExample.createCriteria();
        criteria.andDeviceTypeNameLike("%"+searchValue+"%");
        Map deviceType = findDeviceType(page, rows, deviceTypeExample);
        return deviceType;
    }

    /**
     * 通过设备DeviceTypeId来模糊搜索DeviceType信息
     * @param page
     * @param rows
     * @param searchValue
     * @return
     */
    @RequestMapping("deviceType/search_deviceType_by_deviceTypeId")
    @ResponseBody
    public Map searchDeviceTypeByDeviceTypeId(String page, String rows,String searchValue){

        DeviceTypeExample deviceTypeExample=new DeviceTypeExample();
        DeviceTypeExample.Criteria criteria = deviceTypeExample.createCriteria();
        criteria.andDeviceTypeIdLike("%"+searchValue+"%");
        return findDeviceType(page,rows,deviceTypeExample);
    }
    /****************************以下是DeviceFault的相关内容****************************/

    /**
     * 用来跳转到deviceType.jsp页面
     * @param mv
     * @return
     */
    @RequestMapping("device/deviceFault")
    public ModelAndView deviceDeviceFault(ModelAndView mv){

        mv.setViewName("deviceFault");
        return  mv;
    }

    /**
     * 返回分页查找数据
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("deviceFault/list")
    @ResponseBody
    public Map findDeviceFault(String page, String rows,DeviceFaultExample deviceFaultExample){

        Map map=new HashMap();
        if (page==null||page.equals("")){
            page="1";
        }
        if (rows==null||rows.equals("")){
            rows="30";
        }
        PageInfo<DeviceFault> devicePageInfo = deviceService.selectDeviceFaultByPage(Integer.parseInt(page),
                Integer.parseInt(rows),deviceFaultExample);
        map.put("total",devicePageInfo.getTotal());
        map.put("rows",devicePageInfo.getList());
        return map;
    }

    /**
     * 新增设备故障前的预判断
     * @return
     */
    @RequestMapping("deviceFault/add_judge")
    @ResponseBody
    public String addDeviceFaultJudge(){
        return null;
    }

    /**
     * 跳转到设备故障页面
     * @return
     */
    @RequestMapping("deviceFault/add")
    public ModelAndView addDeviceFault(ModelAndView mv){
        mv.setViewName("deviceFault_add");
        return mv;
    }

    /**
     * 执行新增设备故障操作
     */
    @RequestMapping("deviceFault/insert")
    @ResponseBody
    public Map insertDeviceFault(@ModelAttribute DeviceFault deviceFault){
        Map map=new HashMap();
        boolean b = deviceService.insertDeviceFault(deviceFault);
        if (b) {
            map.put("status", 200);
        }else{
            map.put("msg","修改失败");
        }
        return map;
    }

    /**
     * 编辑设备故障前的预判断
     * @return
     */
    @RequestMapping("deviceFault/edit_judge")
    @ResponseBody
    public String editDeviceFaultJudge(){
        return null;
    }


    /**
     * 跳转到更新设备故障页面
     * @return
     */
    @RequestMapping("deviceFault/edit")
    public ModelAndView editDeviceFault(ModelAndView mv){
        mv.setViewName("deviceFault_edit");
        return mv;
    }

    /**
     * 执行更新设备故障操作
     * @param
     */
    @RequestMapping(value ={"deviceFault/update","deviceFault/update_all","deviceFault/update_note"})
    @ResponseBody
    public Map updateDeviceFault(@ModelAttribute DeviceFault deviceFault){
        Map map=new HashMap();
        boolean b = deviceService.updateDeviceFault(deviceFault);
        if (b) {
            map.put("status", 200);
        }else{
            map.put("msg","修改失败");
        }
        return map;
    }
    /**
     * 删除设备故障前的预判断
     * @return
     */
    @RequestMapping("deviceFault/delete_judge")
    @ResponseBody
    public String deleteDeviceFaultJudge(){
        return null;
    }

    /**
     * 执行删除设备故障操作
     */
    @RequestMapping("deviceFault/delete_batch")
    @ResponseBody
    public Map deleteDeviceFault(String ids){
        Map map=new HashMap();
        boolean b = deviceService.deleteDeviceFault(ids);
        if (b){
            map.put("status", 200);
        }else{
            map.put("msg","删除失败！");
        }
        return map;
    }

    /**
     * 通过设备DeviceFaultId来模糊搜索DeviceFault信息
     * search_deviceFault_by_deviceFaultId
     * @param page
     * @param rows
     * @param searchValue
     * @return
     */
    @RequestMapping("deviceFault/search_deviceFault_by_deviceFaultId")
    @ResponseBody
    public Map searchDeviceFaultByDeviceFaultId(String page, String rows,String searchValue){

        DeviceFaultExample deviceFaultExample=new DeviceFaultExample();
        DeviceFaultExample.Criteria criteria = deviceFaultExample.createCriteria();
        criteria.andDeviceFaultIdLike("%"+searchValue+"%");
        return findDeviceFault(page,rows,deviceFaultExample);
    }

    /**
     * 通过设备名称来模糊搜索 search_deviceFault_by_deviceName
     * @param page
     * @param rows
     * @param searchValue
     * @return
     */
    @RequestMapping("deviceFault/search_deviceFault_by_deviceName")
    @ResponseBody
    public Map searchDeviceFaultByDeviceName(String page, String rows,String searchValue){
        //获取所搜索的设备名称对应的id
        Map deviceListByDeviceName = findDeviceListByDeviceName(page, rows, searchValue);
        List<Device> devices = (List<Device>) deviceListByDeviceName.get("rows");
        List<String> conditions=new ArrayList<String>();
        Iterator<Device> iterator;
        for(iterator=devices.iterator();iterator.hasNext();){
            Device next = iterator.next();
            String deviceId = next.getDeviceId();
            conditions.add(deviceId);
        }

        //如果取不到对应搜索的deviceId的值 构建一个返回Map形式
        if (conditions.size()==0){
            Map map=new HashMap();
            map.put("total",0);
            map.put("rows",conditions);
            return map;
        }
        //现在要把id数据都查出来，作为下一步查询条件
        DeviceFaultExample deviceFaultExample=new DeviceFaultExample();
        DeviceFaultExample.Criteria criteria = deviceFaultExample.createCriteria();
        criteria.andDeviceIdIn(conditions);
        return findDeviceFault(page,rows,deviceFaultExample);
    }


    /****************************以下是DeviceMaintain的相关内容****************************/

    /**
     * 用来跳转到DeviceMaintain.jsp页面
     * @param mv
     * @return
     */
    @RequestMapping("device/deviceMaintain")
    public ModelAndView deviceDeviceMaintain(ModelAndView mv){

        mv.setViewName("deviceMaintain");
        return  mv;
    }

    /**
     * 返回分页查找数据
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("deviceMaintain/list")
    @ResponseBody
    public Map findDeviceMaintain(String page, String rows,DeviceMaintainExample deviceMaintainExample){

        Map map=new HashMap();
        if (page==null||page.equals("")){
            page="1";
        }
        if (rows==null||rows.equals("")){
            rows="30";
        }
        PageInfo<DeviceMaintain> devicePageInfo = deviceService.selectDeviceMaintainByPage(Integer.parseInt(page),
                Integer.parseInt(rows),deviceMaintainExample);
        map.put("total",devicePageInfo.getTotal());
        map.put("rows",devicePageInfo.getList());
        return map;
    }

    /**
     * 新增设备维修前的预判断
     * @return
     */
    @RequestMapping("deviceMaintain/add_judge")
    @ResponseBody
    public String addDeviceMaintainJudge(){
        return null;
    }

    /**
     * 跳转到设备维修页面
     * @return
     */
    @RequestMapping("deviceMaintain/add")
    public ModelAndView addDeviceMaintain(ModelAndView mv){
        mv.setViewName("deviceMaintain_add");
        return mv;
    }

    /**
     * 执行新增设备维修操作
     */
    @RequestMapping("deviceMaintain/insert")
    @ResponseBody
    public Map insertdeviceMaintain(@ModelAttribute DeviceMaintain deviceMaintain){
        Map map=new HashMap();
        boolean b = deviceService.insertDeviceMaintain(deviceMaintain);
        if (b) {
            map.put("status", 200);
        }else{
            map.put("msg","修改失败");
        }
        return map;
    }

    /**
     * 编辑设备维修前的预判断
     * @return
     */
    @RequestMapping("deviceMaintain/edit_judge")
    @ResponseBody
    public String editDeviceMaintainJudge(){
        return null;
    }


    /**
     * 跳转到更新设备维修页面
     * @return
     */
    @RequestMapping("deviceMaintain/edit")
    public ModelAndView editDeviceMaintain(ModelAndView mv){
        mv.setViewName("deviceMaintain_edit");
        return mv;
    }

    /**
     * 执行更新设备维修操作
     * @param
     */
    @RequestMapping(value = {"deviceMaintain/update","deviceMaintain/update_note"})
    @ResponseBody
    public Map updateDeviceMaintain(@ModelAttribute DeviceMaintain deviceMaintain){
        Map map=new HashMap();
        boolean b = deviceService.updateDeviceMaintain(deviceMaintain);
        if (b) {
            map.put("status", 200);
        }else{
            map.put("msg","修改失败");
        }
        return map;
    }
    /**
     * 删除设备维修前的预判断
     * @return
     */
    @RequestMapping("deviceMaintain/delete_judge")
    @ResponseBody
    public String deleteDeviceMaintainJudge(){
        return null;
    }

    /**
     * 执行删除设备维修操作
     */
    @RequestMapping("deviceMaintain/delete_batch")
    @ResponseBody
    public Map deleteDeviceMaintain(String ids){
        Map map=new HashMap();
        boolean b = deviceService.deleteDeviceMaintain(ids);
        if (b){
            map.put("status", 200);
        }else{
            map.put("msg","删除失败！");
        }
        return map;
    }

    /**
     * 通过设备deviceMaintainId来模糊搜索deviceMaintain信息
     * @param page
     * @param rows
     * @param searchValue
     * @return
     */
    @RequestMapping("deviceMaintain/search_deviceMaintain_by_deviceMaintainId")
    @ResponseBody
    public Map searchDeviceMaintainByDeviceMaintainId(String page, String rows,String searchValue){

        DeviceMaintainExample deviceMaintainExample=new DeviceMaintainExample();
        DeviceMaintainExample.Criteria criteria = deviceMaintainExample.createCriteria();
        criteria.andDeviceMaintainIdLike("%"+searchValue+"%");
        return findDeviceMaintain(page,rows,deviceMaintainExample);
    }

    /**
     * 通过设备deviceFaultId来模糊搜索deviceMaintain信息
     * @param page
     * @param rows
     * @param searchValue
     * @return
     */
    @RequestMapping("deviceMaintain/search_deviceMaintain_by_deviceFaultId")
    @ResponseBody
    public Map searchDeviceMaintainByDeviceFaultId(String page, String rows,String searchValue){

        DeviceMaintainExample deviceMaintainExample=new DeviceMaintainExample();
        DeviceMaintainExample.Criteria criteria = deviceMaintainExample.createCriteria();
        criteria.andDeviceFaultIdLike("%"+searchValue+"%");
        return findDeviceMaintain(page,rows,deviceMaintainExample);
    }

    /**
     * 根据deviceMaintain获取对应的deviceFault数据
     * @param params
     * @return
     */
    @RequestMapping("deviceFault/get/{params}")
    @ResponseBody
    public DeviceFault getDeviceFaultForDeviceMaintain(@PathVariable String params){

        DeviceFault deviceFault = deviceService.getDeviceFaultForDeviceMaintain(params);

        return deviceFault;
    }

}
