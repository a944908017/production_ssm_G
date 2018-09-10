package com.wangdao.bean.plan;

import com.wangdao.bean.device.Device;
import com.wangdao.bean.technology.Process;

public class Work {
    private String workId;

    private String processNumber;

    private String productId;

    private String processId;

    private String deviceId;

    private Integer rating;

    Device device;

    Product product;

    Process process;

    public Work() {
    }

    public Work(String workId, String processNumber, String productId, String processId, String deviceId, Integer rating, Device device, Product product, Process process) {
        this.workId = workId;
        this.processNumber = processNumber;
        this.productId = productId;
        this.processId = processId;
        this.deviceId = deviceId;
        this.rating = rating;
        this.device = device;
        this.product = product;
        this.process = process;
    }

    @Override
    public String toString() {
        return "Work{" +
                "workId='" + workId + '\'' +
                ", processNumber='" + processNumber + '\'' +
                ", productId='" + productId + '\'' +
                ", processId='" + processId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", rating=" + rating +
                ", device=" + device +
                ", product=" + product +
                ", process=" + process +
                '}';
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

    public String getProcessNumber() {
        return processNumber;
    }

    public void setProcessNumber(String processNumber) {
        this.processNumber = processNumber == null ? null : processNumber.trim();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId == null ? null : processId.trim();
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}