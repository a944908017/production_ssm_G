package com.wangdao.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Description: PictureService:
 * @author Wangxy013  
 * website：<a href="https://github.com/Wangxy013">GitHub:Wangxy013</a>  
 * @date 2018/9/3 12:40  
 * @version V1.0   
 */
public interface PictureService {

    Map<String, Object> deleteFile(String picName, HttpServletRequest request);

    Map<String, Object> uploadPicture(HttpServletRequest request, String file_path);
}
