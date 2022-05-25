package com.Util;

import com.config.WebConfiguration;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class FileUtilsService implements WebMvcConfigurer {

    public static String uploadFile(MultipartFile file) throws IOException {
        String fileName = ASCIIConverter.removeAccent(file.getOriginalFilename());
        StringBuffer folder = new StringBuffer();

        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        folder.append(WebConfiguration.ROOT_CONTENT_SYS)
                .append("/").append(year)
                .append("/").append(month)
                .append("/");
        File refreshFolder = new File(folder.toString());
        if (!refreshFolder.exists())
            refreshFolder.mkdirs();
        String uploadFile = folder + fileName.toString();
        int i = 0;
        while (true) {
            if (!new File(uploadFile).exists())
                break;
            else
                uploadFile = folder + String.valueOf(i++).concat("-").concat(fileName);
        }
        file.transferTo(new File(uploadFile.toString()));

        uploadFile = uploadFile.replace(WebConfiguration.ROOT_CONTENT_SYS, "");
        System.out.println("uploadFile");
        System.out.println(uploadFile);
        return uploadFile;
    }

    public static boolean deleteFile(String fileName) {
        return new File(WebConfiguration.ROOT_CONTENT_SYS + fileName).delete();
    }

}
