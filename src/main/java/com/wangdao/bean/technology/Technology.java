package com.wangdao.bean.technology;

import java.math.BigDecimal;

public class Technology {
    private String technologyId;

    private String technologyName;

    private BigDecimal price;

    private String vitalProcessPeriod;

    private Integer standardCapacity;

    private Integer overtimeStandardCapacity;

    private Integer overtimeOverfulfilCapacity;

    private Integer doubleCapacity;

    private Integer overfulfilCapacity;

    public void setTechnologyId(String technologyId) {
        this.technologyId = technologyId;
    }

    public void setTechnologyName(String technologyName) {
        this.technologyName = technologyName;
    }

    public void setVitalProcessPeriod(String vitalProcessPeriod) {
        this.vitalProcessPeriod = vitalProcessPeriod;
    }

    public void setStandardCapacity(Integer standardCapacity) {
        this.standardCapacity = standardCapacity;
    }

    public void setOvertimeStandardCapacity(Integer overtimeStandardCapacity) {
        this.overtimeStandardCapacity = overtimeStandardCapacity;
    }

    public void setOvertimeOverfulfilCapacity(Integer overtimeOverfulfilCapacity) {
        this.overtimeOverfulfilCapacity = overtimeOverfulfilCapacity;
    }

    public void setDoubleCapacity(Integer doubleCapacity) {
        this.doubleCapacity = doubleCapacity;
    }

    public void setOverfulfilCapacity(Integer overfulfilCapacity) {
        this.overfulfilCapacity = overfulfilCapacity;
    }

    public String getTechnologyId() {
        return technologyId;
    }

    public void setTechnology_Id(String technologyId) {
        this.technologyId = technologyId == null ? null : technologyId.trim();
    }

    public String getTechnologyName() {
        return technologyName;
    }

    public void setTechnology_Name(String technologyName) {
        this.technologyName = technologyName == null ? null : technologyName.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getVitalProcessPeriod() {
        return vitalProcessPeriod;
    }

    public void setVital_Process_Period(String vitalProcessPeriod) {
        this.vitalProcessPeriod = vitalProcessPeriod == null ? null : vitalProcessPeriod.trim();
    }

    public Integer getStandardCapacity() {
        return standardCapacity;
    }

    public void setStandard_Capacity(Integer standardCapacity) {
        this.standardCapacity = standardCapacity;
    }

    public Integer getOvertimeStandardCapacity() {
        return overtimeStandardCapacity;
    }

    public void setOvertime_Standard_Capacity(Integer overtimeStandardCapacity) {
        this.overtimeStandardCapacity = overtimeStandardCapacity;
    }

    public Integer getOvertimeOverfulfilCapacity() {
        return overtimeOverfulfilCapacity;
    }

    public void setOvertime_Overfulfil_Capacity(Integer overtimeOverfulfilCapacity) {
        this.overtimeOverfulfilCapacity = overtimeOverfulfilCapacity;
    }

    public Integer getDoubleCapacity() {
        return doubleCapacity;
    }

    public void setDouble_Capacity(Integer doubleCapacity) {
        this.doubleCapacity = doubleCapacity;
    }

    public Integer getOverfulfilCapacity() {
        return overfulfilCapacity;
    }

    public void setOverfulfil_Capacity(Integer overfulfilCapacity) {
        this.overfulfilCapacity = overfulfilCapacity;
    }

    @Override
    public String toString() {
        return "{" +
                "\"technologyId\":\"" + technologyId + '\"' +
                ",\"technologyName\":\"" + technologyName + '\"' +
                ",\"price\":\"" + price +'\"' +
                ",\"vitalProcessPeriod\":\"" + vitalProcessPeriod + '\"' +
                ",\"standardCapacity\":\"" + standardCapacity + '\"'+
                ",\"overtimeStandardCapacity\":\"" + overtimeStandardCapacity +'\"'+
                ",\"overtimeOverfulfilCapacity\":\"" + overtimeOverfulfilCapacity +'\"'+
                ",\"doubleCapacity\":\"" + doubleCapacity +'\"'+
                ",\"overfulfilCapacity\":\"" + overfulfilCapacity +'\"'+
                '}';
    }
}