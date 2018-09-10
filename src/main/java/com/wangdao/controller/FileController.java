package com.wangdao.controller;

import com.wangdao.service.FileService;
import com.wangdao.util.DownloadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Wangxy013
 * website：<a href="https://github.com/Wangxy013">GitHub:Wangxy013</a>
 * @version V1.0
 * @Description: FileController:用于处理前端提交的表单数据中文件的上传、修改请求
 * @date 2018/9/2 20:32
 */
@Controller
@RequestMapping("/file")
public class FileController {

    /**
     * 自动注入FileService
     */
    @Autowired
    @Qualifier("FileService")
    private FileService fileService;

    public static final String FILE_PATH = "upload/files/"; //相对路径


    /**
     * 处理文件上传请求
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    //多文件上传：利用MultipartHttpServletRequest来解析Request中的文件
    public Map<String, Object> handleFileUpload(HttpServletRequest request) throws Exception {

        Map<String, Object> result = fileService.uploadFiles(request, FILE_PATH);
        //System.out.println("w//com.wangdao.controller.FileController.handleFileUpload/result=" + result);
        return result;
    }

    /**
     * 处理文件上传后删除的请求
     * @param fileName
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Map<String, Object> handleFileDelete(@RequestParam String fileName,HttpServletRequest request) throws Exception{
        //System.out.println("w//com.wangdao.controller.FileController.handleFileDelete/fileName=" + fileName);

        Map<String,Object> result = fileService.deleteFile(fileName,request);

        return result;
    }

    @RequestMapping(value="/download")
    public void handleFileDownload(@RequestParam String fileName, HttpServletResponse response,HttpServletRequest request) throws Exception{

        //fileName = fileName.substring(fileName.lastIndexOf("/")+1);

        //获得物理路径webapp所在路径
        String pathRoot = request.getSession().getServletContext().getRealPath("");
        String filePath =  pathRoot + fileName;

        DownloadUtil du = new DownloadUtil();

        du.download(filePath, fileName, response, false);
    }
}
