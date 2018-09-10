package com.wangdao.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wangxy013
 * website：<a href="https://github.com/Wangxy013">GitHub:Wangxy013</a>
 * @version V1.0
 * @Description: FileServiceImpl:
 * @date 2018/9/2 20:36
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
@Service("FileService")
public class FileServiceImpl implements FileService {

    /**
     * 上传文件
     *
     * @param request
     * @param FILE_PATH
     * @return
     */
    @Override
    public Map<String, Object> uploadFiles(HttpServletRequest request, String FILE_PATH) {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        // 先实例化一个文件解析器
        CommonsMultipartResolver coMultiResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        // 判断request请求中是否有文件上传
        if (coMultiResolver.isMultipart(request)) {

            //用来保存文件路径，用来jsp页面遍历回显
            //List<String> fileUrlList = new ArrayList<String>();

            // 转换Request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 获得文件
            List<MultipartFile> files = multiRequest.getFiles("file");
            //循环遍历，取出单个文件，下面的操作和单个文件就一样了
            for (MultipartFile file : files) {
                if (!file.isEmpty()) { //这个判断必须要加

                    // 获得原始文件名
                    String fileName = file.getOriginalFilename();
                    Date date = new Date();
                    String newfileName = String.valueOf(date.getHours()) + String.valueOf(date.getMinutes()) + String.valueOf(date.getSeconds()) + "_" + String.valueOf(fileName);
                    //获得物理路径webapp所在路径
                    String pathRoot = request.getSession().getServletContext().getRealPath("");
                    //获取当前开发路径下的webapp文件夹的路径
                    //String pathRoot=Thread.currentThread().getContextClassLoader().getResource("/").getPath().replaceAll("target/ERP_Project/classes/","src/main/webapp/");
                    //String pathRoot = "W:/Programs_W/workspace/production_ssm_resources/";
                    // 项目下相对路径
                    String path = FILE_PATH + newfileName;
                    // 创建文件实例
                    //文件保存路径为pathRoot + path
                    String realFileName = pathRoot + path;
                    File tempFile = new File(realFileName);

                    if (!tempFile.getParentFile().exists()) {
                        tempFile.getParentFile().mkdir();
                    }

                    if (!tempFile.exists()) {
                        tempFile.mkdir();
                    }

                    try {
                        //进行文件传输
                        file.transferTo(tempFile);
                    } catch (Exception e) {
                        //文件上传失败的时候提示错误
                        resultMap.put("error", 1);
                        resultMap.put("message", "文件上传中发生异常");
                        return resultMap;
                    }
                    //图片上传成功后，将图片的地址写回
                    resultMap.put("error", 0);
                    resultMap.put("url", "/upload/files/" + newfileName);
                    return resultMap;
                } else {//如果文件为空
                    //返回结果
                    resultMap.put("error", 1);
                    resultMap.put("message", "文件为空");
                    return resultMap;
                }

            }

        } else {//如果请求中不包含文件
            //返回结果
            resultMap.put("error", 1);
            resultMap.put("message", "请求中不包含文件数据");
            return resultMap;
        }
        //返回结果
        resultMap.put("error", 1);
        resultMap.put("message", "存在问题，检查返回值");
        return resultMap;
    }

    /**
     * 删除上传的文件
     *
     * @param fileName
     * @return
     */
    @Override
    public Map<String, Object> deleteFile(String fileName, HttpServletRequest request) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        fileName = fileName.substring(fileName.lastIndexOf("/") + 1);

        //获得物理路径webapp所在路径
        String pathRoot = request.getSession().getServletContext().getRealPath("");
        fileName = pathRoot + "/upload/files/" + fileName;

        //删除给定的文件
        File file = new File(fileName);
        //删除文件
        file.delete();

        resultMap.put("data", "success");

        return resultMap;

    }

}
