package com.xueyuan.edu.controller;

import com.xueyuan.edu.R;
import com.xueyuan.edu.Utils.QcloudProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@CrossOrigin
@RequestMapping("/edu/teacher/cos")
public class UploadTeacherFile {
    @Autowired
    private QcloudProvider qcloudProvider;

    @PostMapping("img")
    public R uploadTeacherImg(MultipartFile file, String host) {
        try {
            InputStream inputStream = file.getInputStream();
            String url = qcloudProvider.FileToCos(inputStream, host);
            return R.ok().data("images", url);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }
}
