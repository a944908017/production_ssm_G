package com.wangdao.controller;

import com.wangdao.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Description: PictureController:用于处理前端提交的表单数据中图片的上传、修改请求
 * @author Wangxy013  
 * website：<a href="https://github.com/Wangxy013">GitHub:Wangxy013</a>  
 * @date 2018/9/3 12:39  
 * @version V1.0   
 */
@RestController
@RequestMapping("/pic")
public class PictureController {

    @Autowired
    @Qualifier("PictureService")
    private PictureService pictureService;

    public static final String FILE_PATH = "upload/images/"; //相对路径

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> handlePictureUpload(HttpServletRequest request) throws Exception{

        Map<String, Object> result = pictureService.uploadPicture(request, FILE_PATH);
        //System.out.println("w//com.wangdao.controller.FileController.handlePictureUpload/result=" + result);
        return result;

    }

    @RequestMapping("/delete")
    @ResponseBody
    public Map<String,Object> handlePictureDelete(@RequestParam String picName,HttpServletRequest request) throws Exception{
        //System.out.println("w//com.wangdao.controller.FileController.handlePictureDelete/picName=" + picName);

        Map<String,Object> result = pictureService.deleteFile(picName,request);

        return result;
    }
}