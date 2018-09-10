package com.wangdao.bean.dataPostman;

import java.util.List;

/**
 * @Description: FormDataPostman:用户向前端转递数据，包含表示数据总数的total、包含数据的rows（list），此list对象中存放被前端需要的数据对象
 * @author Wangxy013  
 * website：<a href="https://github.com/Wangxy013">GitHub:Wangxy013</a>  
 * @date 2018/8/31 16:39  
 * @version V1.0   
 */
public class FormDataPostman {

    private long total;
    private List<?> rows;

    public FormDataPostman() {
    }

    public FormDataPostman(long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "FormDataPostman{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }
}
