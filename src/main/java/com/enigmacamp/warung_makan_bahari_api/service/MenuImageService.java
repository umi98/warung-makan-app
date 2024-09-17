package com.enigmacamp.warung_makan_bahari_api.service;

import com.enigmacamp.warung_makan_bahari_api.entity.MenuImage;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface MenuImageService {
    MenuImage uploadImage(MultipartFile multipartFile);
    Resource findByPath(String path);
    void deleteFile(String path);
}
