package com.wangdao.dao.material;

import com.wangdao.bean.material.MaterialConsume;

import java.util.List;

public interface MaterialConsumeMapper {

    List<MaterialConsume> selectMaterialConsumeList();

    MaterialConsume selectMaterialConsumeById(String consumeId);

    void insertMaterialConsume(MaterialConsume materialConsume);

    void updateMaterialConsume(MaterialConsume materialConsume);

    void deleteMaterialConsumeById(String id);

    List<MaterialConsume> searchMaterialConsumeByConsumeId(String searchValue);

    List<MaterialConsume> searchMaterialConsumeByWorkId(String searchValue);

    List<MaterialConsume> searchMaterialConsumeByMaterialId(String searchValue);

    int updateMaterialReceiveNote(MaterialConsume materialConsume);
}
