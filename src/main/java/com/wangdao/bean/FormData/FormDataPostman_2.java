package com.wangdao.bean.FormData;

import java.util.List;

public class FormDataPostman_2 {
    String total;
    List<?> rows;

    public FormDataPostman_2() {
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    /*String json(List<Object> rows){
        String result="[{";
        for (Object o:rows) {
            result.a"\""
        }
        
        return result;
    }*/

    @Override
    public String toString() {
        return "{" +
                "\"total\":" + total  +
                ", \"rows\":" + rows +
                '}';
    }
}
