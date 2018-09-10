package com.wangdao.service.material.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangdao.bean.material.Material;
import com.wangdao.bean.material.MaterialReceive;
import com.wangdao.dao.material.MaterialMapper;
import com.wangdao.dao.material.MaterialReceiveMapper;
import com.wangdao.service.material.MaterialReceiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
public class MaterialReceiveServiceImpl implements MaterialReceiveService {

    //自动注入dao层对象
    @Autowired
    private MaterialReceiveMapper materialReceiveMapper;
    @Autowired
    private MaterialMapper materialMapper;

    /**
     * 查询所有物料收入信息
     * @param page
     * @param rows
     * @return
     */
    @Override
    public HashMap selectMaterialReceiveList(Integer page, Integer rows) {

        PageHelper.startPage(page,rows);
       List<MaterialReceive> materialReceives = materialReceiveMapper.selectMaterialReceiveList();

        HashMap hashMap = pageHelper(materialReceives);
        return hashMap;
    }

    /**
     * 新增操作
     * @param materialReceive
     * @return
     */
    @Override
    public HashMap insertMaterialReceive(MaterialReceive materialReceive) {

        HashMap<String, Object> hashMap = new HashMap<>();

       MaterialReceive materialReceive1 = materialReceiveMapper.selectMaterialReceiveById(materialReceive.getReceiveId());

       if (materialReceive1 == null){
           //物料收入编号不存在，执行新增操作
           materialReceiveMapper.insertMaterialReceive(materialReceive);

           //物料信息剩余数量增加
           Integer amount = materialReceive.getAmount();
           String materialId = materialReceive.getMaterialId();
           int i = materialMapper.selectMaterialAmount(materialId);
           int remaining=i+amount;

           updateMaterialRemaing(remaining,materialId);

           hashMap.put("status",200);
       }else{
           hashMap.put("msg","物料收入编号已存在");
       }
       return hashMap;
    }

    /**
     * 编辑物料信息
     * @param materialReceive
     * @return
     */
    @Transactional
    @Override
    public HashMap updateMaterialReceive(MaterialReceive materialReceive) {
        HashMap<String, Object> hashMap = new HashMap<>();

        String receiveId = materialReceive.getReceiveId();
        //获取修改前的物料收入编号
        MaterialReceive materialReceive1 =materialReceiveMapper.selectMaterialReceiveById(receiveId);
        String materialId = materialReceive1.getMaterialId();
        //修改前的物料收入数量
        Integer amount = materialReceive1.getAmount();

        //修改后的物料编号
        String newMaterialId = materialReceive.getMaterialId();
        Integer newAmount = materialReceive.getAmount();

        //恢复之前的物料信息的剩余数量
        Material material = materialMapper.selectMaterialById(materialId);
        Integer remaining =material.getRemaining()-amount;
        updateMaterialRemaing(remaining,materialId);

            //更新物料收入
            materialReceiveMapper.updateMaterialReceiveById(materialReceive);
            // 更新物料信息的剩余数量
            int i = materialMapper.selectMaterialAmount(newMaterialId);
            int newMaterialRemaining = i+newAmount;

            updateMaterialRemaing(newMaterialRemaining,newMaterialId);

            hashMap.put("status",200);

        return hashMap;
    }

    /**
     * 删除物料收入
     * @param ids
     * @return
     */
    @Transactional
    @Override
    public HashMap deleteMaterialReceive(String ids) {

        //分割字符串
        String[] split = ids.split(",");

        for (String id:split) {
            //执行删除操作
            materialReceiveMapper.deleteMaterialReceive(id);
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status",200);

        return hashMap;
    }

    /**
     * 根据物料收入编号模糊搜索
     * @param searchValue
     * @param page
     * @param rows
     * @return
     */
    @Override
    public HashMap searchMaterialReceiveByReceiveId(String searchValue, Integer page, Integer rows) {
        searchValue="%"+searchValue+"%";

        PageHelper.startPage(page,rows);
        List<MaterialReceive> materialReceives = materialReceiveMapper.searchMateraiReceiveById(searchValue);

        HashMap hashMap = pageHelper(materialReceives);
        return hashMap;
    }

    /**
     * 根据物料编号模糊搜索
     * @param searchValue
     * @param page
     * @param rows
     * @return
     */
    @Override
    public HashMap searchMaterialReceiveByMaterialId(String searchValue, Integer page, Integer rows) {
        searchValue="%"+searchValue+"%";

        PageHelper.startPage(page,rows);
        List<MaterialReceive> materialReceives = materialReceiveMapper.searchMaterialReceiveByMaterialId(searchValue);

        HashMap hashMap = pageHelper(materialReceives);
        return hashMap;
    }

    /**
     * 编辑备注
     * @param materialReceive
     * @return
     */
    @Override
    public HashMap updateMaterialReceiveNote(MaterialReceive materialReceive) {
        HashMap<String, Object> hashMap = new HashMap<>();

        if(materialReceive.getNote() !=null && !materialReceive.getNote().isEmpty()){
           int i=materialReceiveMapper.updateMaterialReceiveNote(materialReceive);
            if (i==1){
                hashMap.put("status",200);
            }else{
                hashMap.put("msg","更新备注失败");
            }
        }else{
            hashMap.put("msg","备注为空");
        }
        return hashMap;
    }

    /**
     * 提取分页
     */
    private static HashMap pageHelper(List<MaterialReceive> list){

        PageInfo<MaterialReceive> pageInfo = new PageInfo<>(list);
        long total = pageInfo.getTotal();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total",total);
        hashMap.put("rows",list);
        return hashMap;
    }

    /**
     * 更新物料信息剩余数量
     */
    private  void updateMaterialRemaing(Integer remaining,String materialId){

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("remaining",remaining);
        hashMap.put("materialId",materialId);
        materialMapper.updateMaterialAmount(hashMap);

    }
}
