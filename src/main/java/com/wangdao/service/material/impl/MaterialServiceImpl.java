package com.wangdao.service.material.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangdao.bean.material.Material;
import com.wangdao.dao.material.MaterialMapper;
import com.wangdao.service.material.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
public class MaterialServiceImpl implements MaterialService {

    /**
     * 自动注入dao层对象
     */
    @Autowired
    private MaterialMapper materialMapper;

    //查询所有物料信息记录
    @Override
    public List<Material> selectAllMaterial() {

        List<Material> materials = materialMapper.selectAllMaterial();
        return materials;
    }

    /**
     * 增加物料信息
     * @param material
     */
    @Override
    public HashMap insetMaterial(Material material) {

        HashMap<String, Object> hashMap = new HashMap<>();
       Material ret = materialMapper.selectMaterialById(material.getMaterialId());

       if (ret ==null){
           //物料信息编号不存在，执行添加操作
            int i = materialMapper.insetMaterial(material);
            if (i==1){
                hashMap.put("status",200);
            }else{
                hashMap.put("msg","添加失败");
            }

       }else{
           //物料信息编号已经存在，提示信息
           hashMap.put("msg","物料编号已经已经存在");
       }
        return hashMap;
    }

    /**
     * 更新物料信息
     * @param material
     * @return
     */
    @Override
    public HashMap updateMaterial(Material material) {

        HashMap<String, Object> hashMap = new HashMap<>();
       int i = materialMapper.updateMaterial(material);
       if (i ==1){
           hashMap.put("status",200);
       }else{
           hashMap.put("msg","更新失败");
       }
        return hashMap;
    }

    /**
     * 删除物料信息
     * @param ids
     * @return
     */
    @Override
    public HashMap deleteMaterial(String ids) {

        String[] split = ids.split(",");
        for (String id:split){
            materialMapper.deleteMaterial(id);
        }

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status",200);
        return hashMap;
    }

    /**
     * 物料收入页面打开物料信息框
     * @param materialId
     * @return
     */
    @Transactional(readOnly=true)
    @Override
    public Material selectMaterialByMaterialId(String materialId) {

        Material material = materialMapper.selectMaterialById(materialId);

        return material;
    }


    /**
     * 根据物料编号模糊搜索
     * @param searchValue
     * @return
     */
    @Override
    public HashMap searchMaterialByMaterialId(String searchValue, Integer page, Integer rows) {
        searchValue = "%"+searchValue+"%";

        PageHelper.startPage(page,rows);
        List<Material> materialList = materialMapper.searchMaterialByMaterialId(searchValue);
        PageInfo<Material> pageInfo = new PageInfo<>(materialList);
        long total = pageInfo.getTotal();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total",total);
        hashMap.put("rows",materialList);
        return hashMap;
    }

    /**
     * 根据物料类型模糊搜索
     * @param page
     * @param rows
     * @param searchValue
     * @return
     */
    @Override
    public HashMap searchMaterialByMaterialType(Integer page, Integer rows, String searchValue) {
        searchValue="%"+searchValue+"%";

        PageHelper.startPage(page,rows);
        List<Material> materialList = materialMapper.searchMaterialByMaterialType(searchValue);

        PageInfo<Material> pageInfo = new PageInfo<>(materialList);
        long total = pageInfo.getTotal();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total",total);
        hashMap.put("rows",materialList);
        return hashMap;

    }

    /**
     * 修改备注
     * @param material
     * @return
     */
    @Override
    public HashMap updateMaterialNote(Material material) {
        HashMap<String, Object> hashMap = new HashMap<>();

        if(material.getNote() !=null && !material.getNote().isEmpty()){
           int i= materialMapper.updateMaterialNote(material);
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

}
