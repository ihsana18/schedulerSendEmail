package com.myproject.MyProject1.utility;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileHelper {
    public static String uploadProductPhoto(MultipartFile multipartFile,String type) throws IOException {
        var uploadDirectory = "src/main/resources/static/resources/image/product/";
        var uploadPath = Paths.get(uploadDirectory);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(multipartFile.getOriginalFilename());
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("berhasil : "+multipartFile.getOriginalFilename());
        } catch (Exception exception) {
        }
        return uploadDirectory+multipartFile.getOriginalFilename();
    }
}
