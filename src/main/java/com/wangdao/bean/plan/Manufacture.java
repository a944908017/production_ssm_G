package com.wangdao.bean.plan;

import com.wangdao.bean.technology.Technology;

import java.util.Date;

public class Manufacture {
    private String manufactureSn;

    private String orderId;

    private String technologyId;

    private Integer launchQuantity;

    private Date beginDate;

    private Date endDate;

    COrder cOrder;

    Technology technology;

    @Override
    public String toString() {
        return "Manufacture{" +
                "manufactureSn='" + manufactureSn + '\'' +
                ", orderId='" + orderId + '\'' +
                ", technologyId='" + technologyId + '\'' +
                ", launchQuantity=" + launchQuantity +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", cOrder=" + cOrder +
                ", technology=" + technology +
                '}';
    }

    public COrder getcOrder() {
        return cOrder;
    }

    public void setcOrder(COrder cOrder) {
        this.cOrder = cOrder;
    }

    public Technology getTechnology() {
        return technology;
    }

    public void setTechnology(Technology technology) {
        this.technology = technology;
    }

    public Manufacture() {
    }

    public Manufacture(String manufactureSn, String orderId, String technologyId, Integer launchQuantity, Date beginDate, Date endDate, COrder cOrder, Technology technology) {
        this.manufactureSn = manufactureSn;
        this.orderId = orderId;
        this.technologyId = technologyId;
        this.launchQuantity = launchQuantity;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.cOrder = cOrder;
        this.technology = technology;
    }

    public String getManufactureSn() {
        return manufactureSn;
    }

    public void setManufactureSn(String manufactureSn) {
        this.manufactureSn = manufactureSn == null ? null : manufactureSn.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getTechnologyId() {
        return technologyId;
    }

    public void setTechnologyId(String technologyId) {
        this.technologyId = technologyId == null ? null : technologyId.trim();
    }

    public Integer getLaunchQuantity() {
        return launchQuantity;
    }

    public void setLaunchQuantity(Integer launchQuantity) {
        this.launchQuantity = launchQuantity;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}