package com.wangdao.util;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Wangxy013
 * website：<a href="https://github.com/Wangxy013">GitHub:Wangxy013</a>
 * @version V1.0
 * @Description: DownloadUtil:
 * @date 2018/9/3 16:04
 */
public class DownloadUtil {

    /**
     * @param filePath   要下载的文件路径
     * @param returnName 返回的文件名
     * @param response   HttpServletResponse
     * @param delFlag    是否删除文件
     */
    public void download(String filePath, String returnName, HttpServletResponse response, boolean delFlag) throws Exception {
        this.fileDownload(new File(filePath), returnName, response, delFlag);
    }

    /**
     * @param file       要下载的文件
     * @param returnName 返回的文件名
     * @param response   HttpServletResponse
     * @param delFlag    是否删除文件
     */
    protected void download(File file, String returnName, HttpServletResponse response, boolean delFlag) throws Exception {
        this.fileDownload(file, returnName, response, delFlag);
    }

    /**
     * 下载文件
     * @param file       要下载的文件
     * @param returnName 返回的文件名
     * @param response   HttpServletResponse
     * @param delFlag    是否删除文件
     */
    public void fileDownload(File file, String returnName, HttpServletResponse response, boolean delFlag) throws Exception {

        FileInputStream inputStream = null;
        ServletOutputStream outputStream = null;
        //判断文件是否存在
        if (!file.exists()) {
            throw new Exception("文件不存在！");
        }
        try {
            //使用response.reset() 来清除首部的空白行：
            //一般用于下载文件excel操作时：
            //空白行的出现原因，jsp代码编译后产生。就是有jsp生成html文件的时候，html文件内部会出现很多空白行，下载后的文件内的空白行也是这样产生的。
            response.reset();
            //设置响应类型 PDF文件为"application/pdf"，WORD文件为："application/msword"， EXCEL文件为："application/vnd.ms-excel"。
            response.setContentType("application/octet-stream;charset=utf-8");

            //设置响应的文件名称,并转换成中文编码
            //returnName = URLEncoder.encode(returnName,"UTF-8");
            returnName = response.encodeURL(new String(returnName.getBytes("GBK"),"iso-8859-1"));    //保存的文件名,必须和页面编码一致,否则乱码

            //attachment作为附件下载；inline客户端机器有安装匹配程序，则直接打开；注意改变配置，清除缓存，否则可能不能看到效果
            //指示客户端保存文件（表示服务器端响应的文件，客户端应该如何处理）
            response.addHeader("Content-Disposition", "attachment;filename=" + returnName);

            //将文件读入响应流
            inputStream = new FileInputStream(file);
            outputStream = response.getOutputStream();
            int length = 1024;
            int readLength = 0;
            byte buf[] = new byte[1024];
            readLength = inputStream.read(buf, 0, length);
            while (readLength != -1) {
                outputStream.write(buf, 0, readLength);
                readLength = inputStream.read(buf, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //刷新流
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //判断是否下载后删除原文件，true表示完成下载后删除
            if (delFlag) {
                file.delete();
            }
        }
    }

    /**
     * @param byteArrayOutputStream 将文件内容写入ByteArrayOutputStream
     * @param response              HttpServletResponse 写入response
     * @param returnName            返回的文件名
     */
    public void download(ByteArrayOutputStream byteArrayOutputStream, HttpServletResponse response, String returnName) throws IOException {
        response.setContentType("application/octet-stream;charset=utf-8");
        returnName = response.encodeURL(new String(returnName.getBytes("GBK"), "iso-8859-1"));            //保存的文件名,必须和页面编码一致,否则乱码
        response.addHeader("Content-Disposition", "attachment;filename=" + returnName);
        response.setContentLength(byteArrayOutputStream.size());

        ServletOutputStream outputstream = response.getOutputStream();    //取得输出流
        byteArrayOutputStream.writeTo(outputstream);                    //写到输出流
        byteArrayOutputStream.close();                                    //关闭
        outputstream.flush();                                            //刷数据
    }
}
