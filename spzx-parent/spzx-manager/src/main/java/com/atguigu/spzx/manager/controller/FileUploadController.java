package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.FileUploadService;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * className:{FileUploadController}
 */
@RestController
@RequestMapping("/admin/system")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    /**
     //<input type="file" name="file" /> java html 中的基础知识 name="file"前端用的框架固定是 file 所以别改
     * */
    @PostMapping("/fileUpload")
    public Result fileUpload(@RequestParam("file") MultipartFile multipartFile){
        String url = fileUploadService.fileUpload( multipartFile);
        return Result.build(url, ResultCodeEnum.SUCCESS);
    }
}
