package com.enigmacamp.warung_makan_bahari_api.service.impl;

import com.enigmacamp.warung_makan_bahari_api.entity.MenuImage;
import com.enigmacamp.warung_makan_bahari_api.repository.MenuImageRepository;
import com.enigmacamp.warung_makan_bahari_api.service.MenuImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class MenuImageServiceImpl implements MenuImageService {
    private final MenuImageRepository menuImageRepository;
    private final Path dirPath = Paths.get("assets/img");

    @Transactional(rollbackFor = Exception.class)
    @Override
    public MenuImage uploadImage(MultipartFile multipartFile) {
        if (multipartFile.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File not found");
        try {
            Files.createDirectories(dirPath);
            String fileName = String.format("%d,%s", System.currentTimeMillis(), multipartFile.getOriginalFilename());
            Path filePath = dirPath.resolve(fileName);
            Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            MenuImage menuImage = MenuImage.builder()
                    .name(fileName)
                    .contentType(multipartFile.getContentType())
                    .size(multipartFile.getSize())
                    .path(filePath.toString())
                    .build();
            return menuImageRepository.saveAndFlush(menuImage);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public Resource findByPath(String path) {
        try {
            Path filePath = Paths.get(path);
            return new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public void deleteFile(String path) {

    }
}
