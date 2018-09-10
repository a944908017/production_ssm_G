package com.wangdao.service.material;

import com.wangdao.bean.material.MaterialConsume;

import java.util.HashMap;

public interface MaterialConsumeService {


    HashMap selectMaterialConsumeList(Integer page, Integer rows);

/*    Work selectWorkByWorkId(String workId);

    List<Work> selectWorkList();*/

    HashMap insertMaterialConsume(MaterialConsume materialConsume);

    HashMap updateMaterialConsume(MaterialConsume materialConsume);

    HashMap deleteMaterialConsume(String ids);

    HashMap searchMaterialConsumeByConsumeId(String searchValue, Integer page, Integer rows);

    HashMap searchMaterialConsumeByWorkId(String searchValue, Integer page, Integer rows);

    HashMap searchMaterialConsumeByMaterialId(String searchValue, Integer page, Integer rows);

    HashMap updateMaterialConsumeNote(MaterialConsume materialConsume);
}
