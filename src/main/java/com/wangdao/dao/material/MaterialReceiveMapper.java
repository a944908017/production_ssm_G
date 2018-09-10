package com.wangdao.dao.material;

import com.wangdao.bean.material.MaterialReceive;

import java.util.List;

public interface MaterialReceiveMapper {


    List<MaterialReceive> selectMaterialReceiveList();

    MaterialReceive selectMaterialReceiveById(String receiveId);

    void insertMaterialReceive(MaterialReceive materialReceive);

    int updateMaterialReceiveById(MaterialReceive materialReceive);

    void deleteMaterialReceive(String id);

    List<MaterialReceive> searchMateraiReceiveById(String searchValue);

    List<MaterialReceive> searchMaterialReceiveByMaterialId(String searchValue);

    int updateMaterialReceiveNote(MaterialReceive materialReceive);
}
