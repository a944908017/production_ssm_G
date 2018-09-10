package com.wangdao.bean.technology;

import java.util.Date;

public class TechnologyRequirement {
    private String technologyRequirementId;

    private String technologyId;

    private String requirement;

    private Date addTime;

    private Date reviseTime;

    private Technology technology;

    public void setTechnologyRequirementId(String technologyRequirementId) {
        this.technologyRequirementId = technologyRequirementId;
    }

    public void setTechnologyId(String technologyId) {
        this.technologyId = technologyId;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public void setReviseTime(Date reviseTime) {
        this.reviseTime = reviseTime;
    }

    public Technology getTechnology() {
        return technology;
    }

    public void setTechnology(Technology technology) {
        this.technology = technology;
    }

    public String getTechnologyRequirementId() {
        return technologyRequirementId;
    }

    public void setTechnology_Requirement_Id(String technologyRequirementId) {
        this.technologyRequirementId = technologyRequirementId == null ? null : technologyRequirementId.trim();
    }

    public String getTechnologyId() {
        return technologyId;
    }

    public void setTechnology_Id(String technologyId) {
        this.technologyId = technologyId == null ? null : technologyId.trim();
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement == null ? null : requirement.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAdd_Time(Date addTime) {
        this.addTime = addTime;
    }

    public Date getReviseTime() {
        return reviseTime;
    }

    public void setRevise_Time(Date reviseTime) {
        this.reviseTime = reviseTime;
    }

    @Override
    public String toString() {
        return "{" +
                "\"technologyRequirementId\":\"" + technologyRequirementId + '\"' +
                ",\"technologyId\":\"" + technology.getTechnologyId() + '\"' +
                ",\"technologyName\":\"" + technology.getTechnologyName() + '\"' +
                ",\"requirement\":\"" + requirement + '\"' +
                ",\"addTime\":\"" + addTime +'\"' +
                ",\"reviseTime\":\"" + reviseTime +'\"' +
                '}';
    }
}