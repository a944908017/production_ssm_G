package com.wangdao.bean.material;

import com.wangdao.bean.plan.Work;

import java.util.Date;

public class MaterialConsume {

    /**
     * 物料消耗编号
     */
    //@NotBlank
    private String consumeId;

    /**
     * 外键，所属作业id
     */
    //@NotBlank
    private String workId;

    /**
     * 外键，物料信息ID
     */
    //@NotBlank
    private String materialId;

    /**
     * 消耗数量
     */

    private Integer consumeAmount;

    /**
     * 消耗日期
     */
   /* @NotNull(message = "消耗数量不为空")*/
    private Date consumeDate;

    /**
     * 发送者
     */
    //@NotBlank
    private String sender;

    /**
     * 接收者
     */
    //@NotBlank
    private String receiver;

    /**
     * 备注
     */

    private String note;

    /**
     * 所属作业
     */
    Work work;

    /**
     * 所属物料信息
     */
    Material material;


    public String getConsumeId() {
        return consumeId;
    }

    public void setConsumeId(String consumeId) {
        this.consumeId = consumeId;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public Integer getConsumeAmount() {
        return consumeAmount;
    }

    public void setConsumeAmount(Integer consumeAmount) {
        this.consumeAmount = consumeAmount;
    }

    public Date getConsumeDate() {
        return consumeDate;
    }

    public void setConsumeDate(Date consumeDate) {
        this.consumeDate = consumeDate;
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

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public String toString() {
        return "MaterialConsume{" +
                "consumeId='" + consumeId + '\'' +
                ", workId='" + workId + '\'' +
                ", materialId='" + materialId + '\'' +
                ", consumeAmount=" + consumeAmount +
                ", consumeDate=" + consumeDate +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", note='" + note + '\'' +
                ", work=" + work +
                ", material=" + material +
                '}';
    }
}