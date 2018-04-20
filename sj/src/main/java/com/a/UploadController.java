package com.a;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class UploadController {
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public @ResponseBody String upLoad(MultipartFile file) {
        try {
            String name = new String(file.getOriginalFilename().getBytes("ISO-8859-1"), "UTF-8");
            FileUtils.writeByteArrayToFile(new File("e:/upload/"+name),file.getBytes());
            System.out.println("fileName："+name);
            return "ok";
        } catch (IOException e) {
            e.printStackTrace();
            return "wrong";
        }
    }
}
