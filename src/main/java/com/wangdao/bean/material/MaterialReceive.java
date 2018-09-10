package com.wangdao.bean.material;

import java.util.Date;

public class MaterialReceive {

    /**
     * 物料收入编号
     */
    private String receiveId;

    /**
     * 物料
     */
    private String materialId;;

    /**
     * 收入数量
     */
    private Integer amount;

    /**
     * 收入日期
     */
    private Date receiveDate;

    /**
     * 发送者
     */
    private String sender;

    /**
     * 接受者
     */
    private String receiver;

    /**
     * 备注
     */
    private String note;

    /**
     * 关联的物料信息
     */
    Material material;

    //无参
    public MaterialReceive() {
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "MaterialReceive{" +
                "receiveId='" + receiveId + '\'' +
                ", materialId='" + materialId + '\'' +
                ", amount=" + amount +
                ", receiveDate=" + receiveDate +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", note='" + note + '\'' +
                ", material=" + material +
                '}';
    }
}
