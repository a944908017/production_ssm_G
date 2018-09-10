package com.wangdao.service.material;

import com.wangdao.bean.material.MaterialReceive;

import java.util.HashMap;

public interface MaterialReceiveService {


    HashMap selectMaterialReceiveList(Integer page, Integer rows);

    HashMap insertMaterialReceive(MaterialReceive materialReceive);

    HashMap updateMaterialReceive(MaterialReceive materialReceive);

    HashMap deleteMaterialReceive(String ids);

    HashMap searchMaterialReceiveByReceiveId(String searchValue, Integer page, Integer rows);

    HashMap searchMaterialReceiveByMaterialId(String searchValue, Integer page, Integer rows);

    HashMap updateMaterialReceiveNote(MaterialReceive materialReceive);
}
