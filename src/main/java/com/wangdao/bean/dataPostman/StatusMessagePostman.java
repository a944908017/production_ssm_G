package com.wangdao.bean.dataPostman;
/**   
 * @Description: StatusMessagePostman:
 * @author Wangxy013  
 * website：<a href="https://github.com/Wangxy013">GitHub:Wangxy013</a>  
 * @date 2018/9/1 20:24  
 * @version V1.0   
 */
public class StatusMessagePostman {

    // 返回业务的状态
    private Integer status;

    // 返回提示的消息
    private String msg;

    // 返回的数据
    private Object data;

    public StatusMessagePostman() {
    }

    public StatusMessagePostman(Object data) {
        this.data = data;
    }

    public StatusMessagePostman(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public StatusMessagePostman(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "StatusMessagePostman{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
