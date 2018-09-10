package com.wangdao.service.material;

import com.wangdao.bean.material.Material;

import java.util.HashMap;
import java.util.List;

public interface MaterialService {

    List<Material> selectAllMaterial();

    HashMap insetMaterial(Material material);

    HashMap updateMaterial(Material material);

    HashMap deleteMaterial(String ids);

    Material selectMaterialByMaterialId(String materialId);

    HashMap searchMaterialByMaterialId(String searchValue,Integer page,Integer rows);

    HashMap searchMaterialByMaterialType(Integer page, Integer rows, String searchValue);

    HashMap updateMaterialNote(Material material);
}
