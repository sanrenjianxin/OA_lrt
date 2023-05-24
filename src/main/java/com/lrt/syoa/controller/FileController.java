package com.lrt.syoa.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.lrt.syoa.common.Result;
import com.lrt.syoa.entity.FileDemo;
import com.lrt.syoa.mapper.FileMapper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 文件上传相关接口
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Autowired
    private FileMapper fileMapper;

    /**
     * 文件上传接口
     *
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        // 获取文件的信息
        String originalFilename = multipartFile.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        // 先存储到磁盘
        File uploadParentFile = new File(fileUploadPath);
        // 判断配置的文件目录是否存在，若不存在创建一个新的文件目录
        if (!uploadParentFile.exists()) {
            uploadParentFile.mkdir();
        }
        // 定义一个文件唯一的标识码
        String uuid = IdUtil.fastSimpleUUID();
        String fileUuid = uuid + StrUtil.DOT + type;
        File uploadFile = new File(fileUploadPath + fileUuid);
        // 把获取到的文件存储到磁盘目录
        multipartFile.transferTo(uploadFile);
        return Result.success(fileUuid);
    }

    /**
     * 文件下载接口 http://localhost:8080/file/{fileUuid}
     * @param fileUuid
     * @param response
     * @throws IOException
     */
    @GetMapping("/{fileUuid}")
    public void download(@PathVariable String fileUuid, HttpServletResponse response) throws IOException {
        // 根据文件的唯一标识码获取文件
        File uploadFile = new File(fileUploadPath + fileUuid);
        // 设置输出流的格式
        ServletOutputStream outputStream = response.getOutputStream();
        response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileUuid, "UTF-8"));
        response.setContentType("application/octet-stream");
        // 读取文件的字节流
        outputStream.write(FileUtil.readBytes(uploadFile));
        outputStream.flush();
        outputStream.close();

    }
}
