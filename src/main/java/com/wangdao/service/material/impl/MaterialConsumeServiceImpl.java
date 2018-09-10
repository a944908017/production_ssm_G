package com.wangdao.service.material.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangdao.bean.material.Material;
import com.wangdao.bean.material.MaterialConsume;
import com.wangdao.bean.plan.Work;
import com.wangdao.dao.material.MaterialConsumeMapper;
import com.wangdao.dao.material.MaterialMapper;
import com.wangdao.dao.plan.WorkMapper;
import com.wangdao.service.material.MaterialConsumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
public class MaterialConsumeServiceImpl implements MaterialConsumeService {

    /**
     * 自动注入dao层
     */
    @Autowired
    private MaterialConsumeMapper consumeMapper;
    @Autowired
    private WorkMapper workMapper;
    @Autowired
    private MaterialMapper materialMapper;

    /**
     * 查询所有物料消耗记录
     *
     * @param page
     * @param rows
     * @return
     */
    @Override
    public HashMap selectMaterialConsumeList(Integer page, Integer rows) {

        PageHelper.startPage(page, rows);
        List<MaterialConsume> materialConsumes = consumeMapper.selectMaterialConsumeList();

        HashMap hashMap = myPageHelper(materialConsumes);
        return hashMap;
    }

    /**
     * 增加物料消耗
     *
     * @param materialConsume
     * @return
     */
    @Transactional
    @Override
    public HashMap insertMaterialConsume(MaterialConsume materialConsume) {
        HashMap<String, Object> hashMap = new HashMap<>();

        String consumeId = materialConsume.getConsumeId();
        String materialId = materialConsume.getMaterialId();
        String workId = materialConsume.getWorkId();
        MaterialConsume consume = consumeMapper.selectMaterialConsumeById(consumeId);

        //判断作业id
        Work work = workMapper.selectByPrimaryKey(workId);
        if (work==null){
            hashMap.put("msg","所属作业不存在");
            return hashMap;
        }

        if (consume == null) {
            //物料消耗信息不存在
            int amount = materialMapper.selectMaterialAmount(materialId);
            //判断物料剩余数量是否大于即将消耗数量
            if (amount >= materialConsume.getConsumeAmount()) {
                //执行新增操作
                consumeMapper.insertMaterialConsume(materialConsume);
                hashMap.put("status", 200);
                //相对应的物料剩余数量减少
                int remaining = amount - materialConsume.getConsumeAmount();
                updateMaterialRemaining(remaining, materialId);

            } else {
                hashMap.put("msg", "物料剩余数量不足");
            }
        } else {
            hashMap.put("msg", "物料消耗编号已存在");
        }
        return hashMap;
    }

    /**
     * 编辑物料消耗信息
     *
     * @param materialConsume
     * @return
     */
    @Transactional
    @Override
    public HashMap updateMaterialConsume(MaterialConsume materialConsume) {

        HashMap<String, Object> hashMap = new HashMap<>();

        //原物料信息ID
        MaterialConsume consume = consumeMapper.selectMaterialConsumeById(materialConsume.getConsumeId());
        String materialId = consume.getMaterialId();
        //原物料消耗数量
        Integer amount1 = consume.getConsumeAmount();
        //原作业id
        String workId = consume.getWorkId();

        //修改后的物料信息id
        String consumeMaterialId = materialConsume.getMaterialId();
        //修改后的作业id
        String consumeWorkId = materialConsume.getWorkId();

        if (!workId.equals(consumeWorkId)) {
            //如果修改了作业id，判断新的workID是否存在
            Work work = workMapper.selectByPrimaryKey(consumeWorkId);
            if (work == null) {
                //不存在
                hashMap.put("msg", "所属作业不存在");
                return hashMap;
            }
        }


        //恢复原物料信息的剩余数量
        Integer amount = materialMapper.selectMaterialAmount(materialId);
        int preAmount = amount1 + amount;
        updateMaterialRemaining(preAmount, materialId);

        //判断新的物料id是否存在
        Material material = materialMapper.selectMaterialById(consumeMaterialId);
        if (material == null) {
            //物料不存在
            hashMap.put("msg", "物料信息不存在");
        } else {
            //存在

            //新的物料信息的剩余数量
            Integer i = materialMapper.selectMaterialAmount(consumeMaterialId);
            if (i >= materialConsume.getConsumeAmount()) {
                //新物料信息的剩余数量大于等于物料消耗数量，更新物料消耗
                consumeMapper.updateMaterialConsume(materialConsume);
                //新的物料信息剩余数量减少
                int proAmount = i - materialConsume.getConsumeAmount();
                updateMaterialRemaining(proAmount, consumeMaterialId);
                hashMap.put("status", 200);
            } else {

                //剩余数量小于物料消耗数量
                hashMap.put("msg", "物料信息的剩余数量不足");
            }
        }
        return hashMap;
    }

        /**
         * 删除物料消耗
         * @param ids
         * @return
         */
        @Transactional
        @Override
        public HashMap deleteMaterialConsume (String ids){
            //分割字符串
            String[] split = ids.split(",");
            for (String id : split) {
                consumeMapper.deleteMaterialConsumeById(id);
            }
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("status", 200);
            return hashMap;
        }

        /**
         * 根据物料消耗编号模糊搜索
         * @param searchValue
         * @param page
         * @param rows
         * @return
         */
        @Override
        public HashMap searchMaterialConsumeByConsumeId (String searchValue, Integer page, Integer rows){
            searchValue = "%" + searchValue + "%";

            PageHelper.startPage(page, rows);
            List<MaterialConsume> materialConsumes = consumeMapper.searchMaterialConsumeByConsumeId(searchValue);
            HashMap hashMap = myPageHelper(materialConsumes);
            return hashMap;
        }

        /**
         * 根据作业编号模糊搜索
         * @param searchValue
         * @param page
         * @param rows
         * @return
         */
        @Override
        public HashMap searchMaterialConsumeByWorkId (String searchValue, Integer page, Integer rows){
            searchValue = "%" + searchValue + "%";

            PageHelper.startPage(page, rows);
            List<MaterialConsume> materialConsumes = consumeMapper.searchMaterialConsumeByWorkId(searchValue);
            HashMap hashMap = myPageHelper(materialConsumes);
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
        public HashMap searchMaterialConsumeByMaterialId (String searchValue, Integer page, Integer rows){
            searchValue = "%" + searchValue + "%";

            PageHelper.startPage(page, rows);
            List<MaterialConsume> materialConsumes = consumeMapper.searchMaterialConsumeByMaterialId(searchValue);
            HashMap hashMap = myPageHelper(materialConsumes);
            return hashMap;
        }


    /**
     * 更新备注
      * @param materialConsume
     * @return
     */
    @Override
    public HashMap updateMaterialConsumeNote(MaterialConsume materialConsume) {
        HashMap<String, Object> hashMap = new HashMap<>();

        if(materialConsume.getNote() !=null && !materialConsume.getNote().isEmpty()){
            int i=consumeMapper.updateMaterialReceiveNote(materialConsume);
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
        private static HashMap myPageHelper (List <MaterialConsume> list) {

            PageInfo<MaterialConsume> pageInfo = new PageInfo<>(list);
            long total = pageInfo.getTotal();

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("total", total);
            hashMap.put("rows", list);
            return hashMap;
        }

        /**
         * 更新物料信息剩余数量
         */
        private void updateMaterialRemaining (Integer remaining, String materialId){

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("remaining", remaining);
            hashMap.put("materialId", materialId);
            materialMapper.updateMaterialAmount(hashMap);
        }

}
