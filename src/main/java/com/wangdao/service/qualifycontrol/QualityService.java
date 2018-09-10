package com.wangdao.service.qualifycontrol;

import com.wangdao.bean.qualify.*;

import java.util.List;

public interface QualityService {

    List<UnqualifyApply> selectUnqualifyApplyByPage(Integer limit, Integer offset);

    Integer selectAllCount();

    List<UnqualifyApply> selectUnqualifyByIdCondition(String searchValue, Integer limit, Integer offset);

    Integer selectCountByIdCondition(String searchValue);

    List<UnqualifyApply> selectUnqualifyByPnameCondition(String searchValue, Integer limit, Integer offset);

    Integer selectCountByPNameCondition(String searchValue);

    boolean deleteByIds(String ids);

    List<FinalMeasuretCheck> selectMeasureByPage(Integer limit, Integer offset);

    Integer selectAllMeasureCount();

    List<FinalMeasuretCheck> selectMeasureByCheckIdCondition(String searchValue, Integer limit, Integer offset);

    Integer selectMeasureCountByCheckIdCondition(String searchValue);

    List<FinalMeasuretCheck> selectMeasureByOrderIdCondition(String searchValue, Integer limit, Integer offset);

    Integer selectMeasureCountByOrderIdCondition(String searchValue);

    boolean updateUnqualifyApply(UnqualifyApply unqualifyApply);

    boolean updateFinalMeasureCheck(FinalMeasuretCheck finalMeasuretCheck);

    boolean deleteFMeasureCheckByIds(String ids);

    List<FinalCountCheck> selectFCountCheckByPage(Integer limit, Integer offset);

    Integer selectAllfCountCheck();

    List<FinalCountCheck> selectFCountByCheckIdCondition(String searchValue, Integer limit, Integer offset);

    Integer selectFCountCheckCountByCheckIdCondition(String searchValue);

    List<FinalCountCheck> selectFCountCheckByOrderIdCondition(String searchValue, Integer limit, Integer offset);

    Integer selectFCountCheckCountByOrderIdCondition(String searchValue);

    boolean updateFinalCountCheck(FinalCountCheck finalCountCheck);

    boolean deleteFCountCheckByIds(String ids);

    List<ProcessMeasureCheck> selectPMeasureCheckByPage(Integer limit, Integer offset);

    Integer selectAllPMeasureCheck();

    List<ProcessMeasureCheck> selectPMeasureByCheckIdCondition(String searchValue, Integer limit, Integer offset);

    Integer selectPMeasureCheckCountByCheckIdCondition(String searchValue);

    boolean updateProcessMeasureCheck(ProcessMeasureCheck processMeasureCheck);

    boolean deletePmeasureCheckByIds(String ids);

    List<ProcessCountCheck> selectPCountCheckByPage(Integer limit, Integer offset);

    Integer selectAllPCountCheck();

    List<ProcessCountCheck> selectPCountByCheckIdCondition(String searchValue, Integer limit, Integer offset);

    Integer selectPCountCheckCountByCheckIdCondition(String searchValue);

    boolean updateProcessCountCheck(ProcessCountCheck processCountCheck);

    boolean deletePCountCheckByIds(String ids);

    boolean insertFMeasureCheck(FinalMeasuretCheck finalMeasuretCheck);

    boolean insertFCountCheck(FinalCountCheck finalCountCheck);

    boolean insertPMeasureCheck(ProcessMeasureCheck processMeasureCheck);

    boolean insertPCountCheck(ProcessCountCheck processCountCheck);

    boolean insertUnqualify(UnqualifyApply unqualifyApply);
}
