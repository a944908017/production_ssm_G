package com.wangdao.dao.material;

import com.wangdao.bean.material.Material;

import java.util.HashMap;
import java.util.List;

/**
 * 物料信息dao层实现
 */
public interface MaterialMapper {

    List<Material> selectAllMaterial();

    int insetMaterial(Material material);

    Material selectMaterialById(String materialId);

    int updateMaterial(Material material);

    void deleteMaterial(String id);

    int selectMaterialAmount(String materialId);

    void updateMaterialAmount(HashMap<String, Object> map);

    List<Material> searchMaterialByMaterialId(String searchValue);

    List<Material> searchMaterialByMaterialType(String searchValue);

    int updateMaterialNote(Material material);
}