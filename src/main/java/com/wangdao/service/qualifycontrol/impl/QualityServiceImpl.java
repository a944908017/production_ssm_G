package com.wangdao.service.qualifycontrol.impl;

import com.wangdao.bean.plan.Product;
import com.wangdao.bean.qualify.*;
import com.wangdao.dao.plan.ProductMapper;
import com.wangdao.dao.qualifycontrol.*;
import com.wangdao.service.plan.PlanService;
import com.wangdao.service.qualifycontrol.QualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service("QualityService")
public class QualityServiceImpl implements QualityService {

    @Autowired
    UnqualifyApplyMapper unqualifyApplyMapper;
    @Autowired
    FinalMeasuretCheckMapper finalMeasuretCheckMapper;
    @Autowired
    FinalCountCheckMapper finalCountCheckMapper;
    @Autowired
    ProcessMeasureCheckMapper processMeasureCheckMapper;
    @Autowired
    ProcessCountCheckMapper processCountCheckMapper;
    @Autowired
    ProductMapper productMapper;
    /***************************************不合格品管理******************************************************/

    /**
     * 查询指定条数的不合格品
     *
     * @param limit
     * @param offset
     * @return
     */
    @Override
    public List<UnqualifyApply> selectUnqualifyApplyByPage(Integer limit, Integer offset) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("limit", limit);
        map.put("offset", offset);
        return unqualifyApplyMapper.selectUnqualifyApplyByPage(map);
    }

    /**
     * 查询不合格品总数
     *
     * @return
     */
    @Override
    public Integer selectAllCount() {

        return unqualifyApplyMapper.selectAllCount();
    }

    /**
     * 根据不合格品申请编号，模糊查询
     *
     * @param searchValue
     * @param limit
     * @param offset
     * @return 返回满足条件的，不合格品的集合
     */
    @Override
    public List<UnqualifyApply> selectUnqualifyByIdCondition(String searchValue, Integer limit, Integer offset) {
        HashMap<String, Object> map = new HashMap<>();
        if (searchValue != null) {
            searchValue = "%" + searchValue + "%";
        }
        map.put("searchValue", searchValue);
        map.put("limit", limit);
        map.put("offset", offset);
        return unqualifyApplyMapper.selectUnqualifyByIdCondition(map);
    }

    /**
     * 根据不合格品申请编号，模糊查询
     *
     * @param searchValue
     * @return 返回满足条件的，不合格品的数量
     */
    @Override
    public Integer selectCountByIdCondition(String searchValue) {
        if (searchValue != null) {
            searchValue = "%" + searchValue + "%";
        }
        return unqualifyApplyMapper.selectCountByIdCondition(searchValue);
    }

    /**
     * 新增一个不合格品
     *
     * @param unqualifyApply
     * @return
     */
    @Override
    public boolean insertUnqualify(UnqualifyApply unqualifyApply) {
        UnqualifyApply unqualifyApply_old = unqualifyApplyMapper.selectByPrimaryKey(unqualifyApply.getUnqualifyApplyId());
        if (unqualifyApply_old != null) {
            return false;
        }
        int i = unqualifyApplyMapper.insert(unqualifyApply);
        return i == 1;
    }

    /**
     * 根据产品名模糊查询不合格品的集合
     *
     * @param searchValue
     * @param limit
     * @param offset
     * @return
     */
    @Override
    public List<UnqualifyApply> selectUnqualifyByPnameCondition(String searchValue, Integer limit, Integer offset) {
        List<String> productIds = productMapper.selectProductIdsByProductName("%" + searchValue + "%");
        UnqualifySelectByProductIds usbi = new UnqualifySelectByProductIds();
        usbi.setLimit(limit);
        usbi.setOffset(offset);
        usbi.setProductIds(productIds);
        return unqualifyApplyMapper.selectUnqualifyByProductIds(usbi);
    }

    /**
     * 根据产品名模糊查询不合格品的个数
     *
     * @param searchValue
     * @return
     */
    @Override
    public Integer selectCountByPNameCondition(String searchValue) {
        List<String> productIds = productMapper.selectProductIdsByProductName("%" + searchValue + "%");
        return unqualifyApplyMapper.selectCountByProductIds(productIds);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @Transactional
    @Override
    public boolean deleteByIds(String ids) {
        boolean flag = false;
        String[] split = ids.split(",");
        for (String s : split) {
            int i = unqualifyApplyMapper.deleteByPrimaryKey(s);
            flag = i == 1 ? true : false;
            if (flag == false) {
                throw new RuntimeException("删除失败");
            }
        }
        return flag;
    }

    /**
     * 更新不合格品
     *
     * @param unqualifyApply
     * @return
     */
    @Override
    public boolean updateUnqualifyApply(UnqualifyApply unqualifyApply) {
        int i = unqualifyApplyMapper.updateByPrimaryKeySelective(unqualifyApply);
        return i == 1;
    }


    /***************************************成品计量质检******************************************************/

    /**
     * 查询指定条数的成品计量质检
     *
     * @param limit
     * @param offset
     * @return
     */
    @Override
    public List<FinalMeasuretCheck> selectMeasureByPage(Integer limit, Integer offset) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("limit", limit);
        map.put("offset", offset);
        return finalMeasuretCheckMapper.selectMeasureByPage(map);
    }

    /**
     * 查询成品计量质检总数
     *
     * @return
     */
    @Override
    public Integer selectAllMeasureCount() {
        return finalMeasuretCheckMapper.selectAllMeasureCount();
    }

    /**
     * 根据成品计量质检编号，模糊查询
     *
     * @param searchValue
     * @param limit
     * @param offset
     * @return 返回满足条件的，成品计量质检的集合
     */
    @Override
    public List<FinalMeasuretCheck> selectMeasureByCheckIdCondition(String searchValue, Integer limit, Integer offset) {
        HashMap<String, Object> map = new HashMap<>();
        if (searchValue != null) {
            searchValue = "%" + searchValue + "%";
        }
        map.put("searchValue", searchValue);
        map.put("limit", limit);
        map.put("offset", offset);
        return finalMeasuretCheckMapper.selectMeasureByCheckIdCondition(map);
    }

    /**
     * 根据成品计量质检编号，模糊查询
     *
     * @param searchValue
     * @return 返回满足条件的，成品计量质检的数量
     */
    @Override
    public Integer selectMeasureCountByCheckIdCondition(String searchValue) {
        if (searchValue != null) {
            searchValue = "%" + searchValue + "%";
        }
        return finalMeasuretCheckMapper.selectMeasureCountByCheckIdCondition(searchValue);
    }

    /**
     * 根据订单编号，模糊查询
     *
     * @param searchValue
     * @param limit
     * @param offset
     * @return 返回满足条件的的集合
     */
    @Override
    public List<FinalMeasuretCheck> selectMeasureByOrderIdCondition(String searchValue, Integer limit, Integer offset) {
        HashMap<String, Object> map = new HashMap<>();
        if (searchValue != null) {
            searchValue = "%" + searchValue + "%";
        }
        map.put("searchValue", searchValue);
        map.put("limit", limit);
        map.put("offset", offset);
        return finalMeasuretCheckMapper.selectMeasureByOrderIdCondition(map);
    }

    /**
     * 根据订单编号，模糊查询
     *
     * @param searchValue
     * @return 返回满足条件的数量
     */
    @Override
    public Integer selectMeasureCountByOrderIdCondition(String searchValue) {
        if (searchValue != null) {
            searchValue = "%" + searchValue + "%";
        }
        return finalMeasuretCheckMapper.selectMeasureCountByOrderIdCondition(searchValue);
    }

    /**
     * 更新成品计量质检
     *
     * @param finalMeasuretCheck
     * @return
     */
    @Override
    public boolean updateFinalMeasureCheck(FinalMeasuretCheck finalMeasuretCheck) {
        int i = finalMeasuretCheckMapper.updateByPrimaryKeySelective(finalMeasuretCheck);
        return i == 1;
    }

    /**
     * [批量]删除成品计量质检
     *
     * @param ids
     * @return
     */
    @Transactional
    @Override
    public boolean deleteFMeasureCheckByIds(String ids) {
        boolean flag = false;
        String[] split = ids.split(",");
        for (String s : split) {
            int i = finalMeasuretCheckMapper.deleteByPrimaryKey(s);
            flag = i == 1 ? true : false;
            if (flag == false) {
                throw new RuntimeException("删除失败");
            }
        }
        return flag;
    }

    /**
     * 新增一个成品计量质检
     *
     * @param finalMeasuretCheck
     * @return
     */
    @Override
    public boolean insertFMeasureCheck(FinalMeasuretCheck finalMeasuretCheck) {
        FinalMeasuretCheck finalMeasuretCheck_old = finalMeasuretCheckMapper.selectByPrimaryKey(finalMeasuretCheck.getfMeasureCheckId());
        if (finalMeasuretCheck_old != null) {
            return false;
        }
        int i = finalMeasuretCheckMapper.insert(finalMeasuretCheck);
        return i == 1;
    }

    /***************************************成品计数质检******************************************************/
    /**
     * 查询指定条数的成品计量质检
     *
     * @param limit
     * @param offset
     * @return
     */
    @Override
    public List<FinalCountCheck> selectFCountCheckByPage(Integer limit, Integer offset) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("limit", limit);
        map.put("offset", offset);
        return finalCountCheckMapper.selectFCountByPage(map);
    }

    /**
     * 查询成品计数质检总数
     *
     * @return
     */
    @Override
    public Integer selectAllfCountCheck() {
        return finalCountCheckMapper.selectAllfCountCheck();
    }

    /**
     * 根据成品计数质检编号，模糊查询
     *
     * @param searchValue
     * @param limit
     * @param offset
     * @return 返回满足条件的，成品计数质检的集合
     */
    @Override
    public List<FinalCountCheck> selectFCountByCheckIdCondition(String searchValue, Integer limit, Integer offset) {
        HashMap<String, Object> map = new HashMap<>();
        if (searchValue != null) {
            searchValue = "%" + searchValue + "%";
        }
        map.put("searchValue", searchValue);
        map.put("limit", limit);
        map.put("offset", offset);
        return finalCountCheckMapper.selectFCountCheckByCheckIdCondition(map);
    }

    /**
     * 根据成品计数质检编号，模糊查询
     *
     * @param searchValue
     * @return 返回满足条件的，成品计数质检的数量
     */
    @Override
    public Integer selectFCountCheckCountByCheckIdCondition(String searchValue) {
        if (searchValue != null) {
            searchValue = "%" + searchValue + "%";
        }
        return finalCountCheckMapper.selectFCountCheckCountByCheckIdCondition(searchValue);
    }

    /**
     * 根据订单编号，模糊查询
     *
     * @param searchValue
     * @param limit
     * @param offset
     * @return 返回满足条件的的集合
     */
    @Override
    public List<FinalCountCheck> selectFCountCheckByOrderIdCondition(String searchValue, Integer limit, Integer offset) {
        HashMap<String, Object> map = new HashMap<>();
        if (searchValue != null) {
            searchValue = "%" + searchValue + "%";
        }
        map.put("searchValue", searchValue);
        map.put("limit", limit);
        map.put("offset", offset);
        return finalCountCheckMapper.selectFCountCheckByOrderIdCondition(map);
    }

    /**
     * 根据订单编号，模糊查询
     *
     * @param searchValue
     * @return 返回满足条件的数量
     */
    @Override
    public Integer selectFCountCheckCountByOrderIdCondition(String searchValue) {
        if (searchValue != null) {
            searchValue = "%" + searchValue + "%";
        }
        return finalCountCheckMapper.selectFCountCheckCountByOrderIdCondition(searchValue);
    }

    /**
     * 更新成品计数质检
     *
     * @param finalCountCheck
     * @return
     */
    @Override
    public boolean updateFinalCountCheck(FinalCountCheck finalCountCheck) {
        int i = finalCountCheckMapper.updateByPrimaryKeySelective(finalCountCheck);
        return i == 1;
    }

    /**
     * [批量]删除成品计数质检
     *
     * @param ids
     * @return
     */
    @Transactional
    @Override
    public boolean deleteFCountCheckByIds(String ids) {
        boolean flag = false;
        String[] split = ids.split(",");
        for (String s : split) {
            int i = finalCountCheckMapper.deleteByPrimaryKey(s);
            flag = i == 1 ? true : false;
            if (flag == false) {
                throw new RuntimeException("删除失败");
            }
        }
        return flag;
    }

    /**
     * 新增一个成品计数质检
     *
     * @param finalCountCheck
     * @return
     */
    @Override
    public boolean insertFCountCheck(FinalCountCheck finalCountCheck) {
        FinalCountCheck finalCountCheck_old = finalCountCheckMapper.selectByPrimaryKey(finalCountCheck.getfCountCheckId());
        if (finalCountCheck_old != null) {
            return false;
        }
        int i = finalCountCheckMapper.insert(finalCountCheck);
        return i == 1;
    }


    /***************************************工序计量质检******************************************************/

    /**
     * 查询指定条数的工序计量质检
     *
     * @param limit
     * @param offset
     * @return
     */
    @Override
    public List<ProcessMeasureCheck> selectPMeasureCheckByPage(Integer limit, Integer offset) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("limit", limit);
        map.put("offset", offset);
        return processMeasureCheckMapper.selectPMeasureCheckByPage(map);
    }

    /**
     * 查询工序计量质检总数
     *
     * @return
     */
    @Override
    public Integer selectAllPMeasureCheck() {
        return processMeasureCheckMapper.selectAllPMeasureCheck();
    }

    /**
     * 根据工序计量质检编号，模糊查询
     *
     * @param searchValue
     * @param limit
     * @param offset
     * @return 返回满足条件的，工序计量质检的集合
     */
    @Override
    public List<ProcessMeasureCheck> selectPMeasureByCheckIdCondition(String searchValue, Integer limit, Integer offset) {
        HashMap<String, Object> map = new HashMap<>();
        if (searchValue != null) {
            searchValue = "%" + searchValue + "%";
        }
        map.put("searchValue", searchValue);
        map.put("limit", limit);
        map.put("offset", offset);
        return processMeasureCheckMapper.selectPMeasureByCheckIdCondition(map);
    }

    /**
     * 根据工序计量质检编号，模糊查询
     *
     * @param searchValue
     * @return 返回满足条件的，工序计量质检的数量
     */
    @Override
    public Integer selectPMeasureCheckCountByCheckIdCondition(String searchValue) {
        if (searchValue != null) {
            searchValue = "%" + searchValue + "%";
        }
        return processMeasureCheckMapper.selectPMeasureCheckCountByCheckIdCondition(searchValue);
    }

    /**
     * 更新工序计量质检
     *
     * @param finalCountCheck
     * @return
     */
    @Override
    public boolean updateProcessMeasureCheck(ProcessMeasureCheck processMeasureCheck) {
        int i = processMeasureCheckMapper.updateByPrimaryKeySelective(processMeasureCheck);
        return i == 1;
    }

    /**
     * [批量]删除工序计量质检
     *
     * @param ids
     * @return
     */
    @Transactional
    @Override
    public boolean deletePmeasureCheckByIds(String ids) {
        boolean flag = false;
        String[] split = ids.split(",");
        for (String s : split) {
            int i = processMeasureCheckMapper.deleteByPrimaryKey(s);
            flag = i == 1 ? true : false;
            if (flag == false) {
                throw new RuntimeException("删除失败");
            }
        }
        return flag;
    }

    /**
     * 新增一个工序计量质检
     *
     * @param processMeasureCheck
     * @return
     */
    @Override
    public boolean insertPMeasureCheck(ProcessMeasureCheck processMeasureCheck) {
        ProcessMeasureCheck processMeasureCheck_old = processMeasureCheckMapper.selectByPrimaryKey(processMeasureCheck.getpMeasureCheckId());
        if (processMeasureCheck_old != null) {
            return false;
        }
        int i = processMeasureCheckMapper.insert(processMeasureCheck);
        return i == 1;
    }

    /***************************************工序计数质检******************************************************/
    /**
     * 查询指定条数的工序计数质检
     *
     * @param limit
     * @param offset
     * @return
     */
    @Override
    public List<ProcessCountCheck> selectPCountCheckByPage(Integer limit, Integer offset) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("limit", limit);
        map.put("offset", offset);
        return processCountCheckMapper.selectPCountCheckByPage(map);
    }

    /**
     * 查询工序计数质检总数
     *
     * @return
     */
    @Override
    public Integer selectAllPCountCheck() {
        return processCountCheckMapper.selectAllPCountCheck();
    }

    /**
     * 根据工序计数质检编号，模糊查询
     *
     * @param searchValue
     * @param limit
     * @param offset
     * @return 返回满足条件的，工序计数质检的集合
     */
    @Override
    public List<ProcessCountCheck> selectPCountByCheckIdCondition(String searchValue, Integer limit, Integer offset) {
        HashMap<String, Object> map = new HashMap<>();
        if (searchValue != null) {
            searchValue = "%" + searchValue + "%";
        }
        map.put("searchValue", searchValue);
        map.put("limit", limit);
        map.put("offset", offset);
        return processCountCheckMapper.selectPCountByCheckIdCondition(map);
    }

    /**
     * 根据工序计数质检编号，模糊查询
     *
     * @param searchValue
     * @return 返回满足条件的，工序计数质检的数量
     */
    @Override
    public Integer selectPCountCheckCountByCheckIdCondition(String searchValue) {
        if (searchValue != null) {
            searchValue = "%" + searchValue + "%";
        }
        return processCountCheckMapper.selectPCountCheckCountByCheckIdCondition(searchValue);
    }

    /**
     * 更新工序计数质检
     *
     * @param finalCountCheck
     * @return
     */
    @Override
    public boolean updateProcessCountCheck(ProcessCountCheck processCountCheck) {
        int i = processCountCheckMapper.updateByPrimaryKeySelective(processCountCheck);
        return i == 1;
    }

    /**
     * [批量]删除工序计数质检
     *
     * @param ids
     * @return
     */
    @Transactional
    @Override
    public boolean deletePCountCheckByIds(String ids) {
        boolean flag = false;
        String[] split = ids.split(",");
        for (String s : split) {
            int i = processCountCheckMapper.deleteByPrimaryKey(s);
            flag = i == 1 ? true : false;
            if (flag == false) {
                throw new RuntimeException("删除失败");
            }
        }
        return flag;
    }

    /**
     * 新增一个工序计数质检
     *
     * @param processCountCheck
     * @return
     */
    @Override
    public boolean insertPCountCheck(ProcessCountCheck processCountCheck) {
        ProcessCountCheck processCountCheck_old = processCountCheckMapper.selectByPrimaryKey(processCountCheck.getpCountCheckId());
        if (processCountCheck_old != null) {
            return false;
        }
        int i = processCountCheckMapper.insert(processCountCheck);
        return i == 1;
    }
}
