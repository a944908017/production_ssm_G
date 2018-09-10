package com.wangdao.bean.technology;

import java.util.Date;

public class TechnologyPlan {
    private String technologyPlanId;

    private String technologyId;

    private Integer batchAmount;

    private Date startPlan;

    private Date endPlan;

    private Date commitPlan;

    private Date technologyPlanStart;

    private Date technologyPlanEnd;

    private Technology technology;

    public void setTechnologyPlanId(String technologyPlanId) {
        this.technologyPlanId = technologyPlanId;
    }

    public void setTechnologyId(String technologyId) {
        this.technologyId = technologyId;
    }

    public void setBatchAmount(Integer batchAmount) {
        this.batchAmount = batchAmount;
    }

    public void setStartPlan(Date startPlan) {
        this.startPlan = startPlan;
    }

    public void setEndPlan(Date endPlan) {
        this.endPlan = endPlan;
    }

    public void setCommitPlan(Date commitPlan) {
        this.commitPlan = commitPlan;
    }

    public void setTechnologyStartPlan(Date technologyPlanStart) {
        this.technologyPlanStart = technologyPlanStart;
    }

    public void setTechnologyEndPlan(Date technologyPlanEnd) {
        this.technologyPlanEnd = technologyPlanEnd;
    }

    public Technology getTechnology() {
        return technology;
    }

    public void setTechnology(Technology technology) {
        this.technology = technology;
    }

    public String getTechnologyPlanId() {
        return technologyPlanId;
    }

    public void setTechnology_Plan_Id(String technologyPlanId) {
        this.technologyPlanId = technologyPlanId == null ? null : technologyPlanId.trim();
    }

    public String getTechnologyId() {
        return technologyId;
    }

    public void setTechnology_Id(String technologyId) {
        this.technologyId = technologyId == null ? null : technologyId.trim();
    }

    public Integer getBatchAmount() {
        return batchAmount;
    }

    public void setBatch_Amount(Integer batchAmount) {
        this.batchAmount = batchAmount;
    }

    public Date getStartPlan() {
        return startPlan;
    }

    public void setStart_Plan(Date startPlan) {
        this.startPlan = startPlan;
    }

    public Date getEndPlan() {
        return endPlan;
    }

    public void setEnd_Plan(Date endPlan) {
        this.endPlan = endPlan;
    }

    public Date getCommitPlan() {
        return commitPlan;
    }

    public void setCommit_Plan(Date commitPlan) {
        this.commitPlan = commitPlan;
    }

    public Date getTechnologyPlanStart() {
        return technologyPlanStart;
    }

    public void setTechnology_Plan_Start(Date technologyPlanStart) {
        this.technologyPlanStart = technologyPlanStart;
    }

    public Date getTechnologyPlanEnd() {
        return technologyPlanEnd;
    }

    public void setTechnology_Plan_End(Date technologyPlanEnd) {
        this.technologyPlanEnd = technologyPlanEnd;
    }

    @Override
    public String toString() {
        String id=null;
        String name=null;
        if(technology==null){
            id=technologyId;
        }else{
            name=technology.getTechnologyName();
            id=technology.getTechnologyId();
        }

        return "{" +
                "\"technologyPlanId\":\"" + technologyPlanId + '\"' +
                ",\"technologyId\":\"" + id + '\"' +
                ",\"technologyName\":\"" + name + '\"' +
                ",\"batchAmount\":\"" + batchAmount +'\"' +
                ",\"startPlan\":\"" + startPlan +'\"' +
                ",\"endPlan\":\"" + endPlan +'\"' +
                ",\"commitPlan\":\"" + commitPlan +'\"' +
                ",\"technologyPlanStart\":\"" + technologyPlanStart +'\"' +
                ",\"technologyPlanEnd\":\"" + technologyPlanEnd +'\"' +
                '}';
    }
}