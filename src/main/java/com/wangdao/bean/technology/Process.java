package com.wangdao.bean.technology;

public class Process {
    private String processId;

    private String technologyPlanId;

    private Integer sequence;

    private Integer quota;

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public void setTechnologyPlanId(String technologyPlanId) {
        this.technologyPlanId = technologyPlanId;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcess_Id(String processId) {
        this.processId = processId == null ? null : processId.trim();
    }

    public String getTechnologyPlanId() {
        return technologyPlanId;
    }

    public void setTechnology_Plan_Id(String technologyPlanId) {
        this.technologyPlanId = technologyPlanId == null ? null : technologyPlanId.trim();
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getQuota() {
        return quota;
    }

    public void setQuota(Integer quota) {
        this.quota = quota;
    }

    @Override
    public String toString() {
        return "{" +
                "\"processId\":\"'" + processId +'\"' +
                ",\"technologyPlanId\":\"'" + technologyPlanId +'\"' +
                ",\"sequence\":\"" + sequence +'\"' +
                ",\"quota\":\"" + quota +'\"' +
                '}';
    }
}