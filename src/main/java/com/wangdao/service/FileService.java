package com.wangdao.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Wangxy013
 * website：<a href="https://github.com/Wangxy013">GitHub:Wangxy013</a>
 * @version V1.0
 * @Description: FileService:用于实现FileController中文件的操作方法
 * @date 2018/9/2 20:34
 */
public interface FileService {

    /**
     * 上传文件
     * @param request
     * @param filePath
     * @return
     */
    Map<String, Object> uploadFiles(HttpServletRequest request, String filePath);

    /**
     * 删除上传的文件
     * @param fileName
     * @return
     */
    Map<String, Object> deleteFile(String fileName, HttpServletRequest request);
}
