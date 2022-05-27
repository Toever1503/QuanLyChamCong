package com.service.impl;

import com.Util.FileUtilsService;
import com.config.WebConfiguration;
import com.entity.File;
import com.model.FileModel;
import com.repository.IFileRepository;
import com.service.IFileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileServiceImpl implements IFileService {
    private final IFileRepository imageRepository;

    public FileServiceImpl(IFileRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public List<File> findAll() {
        return null;
    }

    File toEntity(String fileName, MultipartFile file) {
        return File.builder()
                .fileGuid(fileName)
                .fileUrl(WebConfiguration.HOST + fileName)
                .fileType(file.getContentType())
                .build();
    }


    @Override
    public Page<File> findAll(Pageable page) {
        return this.imageRepository.findAll(page);
    }

    @Override
    public File findById(Long id) {
        return this.imageRepository.findById(id).orElseThrow(() -> new RuntimeException("Image not found"));
    }

    @Override
    public File add(FileModel model) {
        try {
            String imageFile = FileUtilsService.uploadFile(model.getFile());
            File imageEntity = toEntity(imageFile, model.getFile());
            return this.imageRepository.save(imageEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Image add failed");
    }

    @Override
    public List<File> add(List<FileModel> model) {
        return null;
    }

    @Override
    public File update(FileModel model) {
        try {
            File original = findById(model.getId());
            if (!FileUtilsService.deleteFile(original.getFileGuid()))
                throw new RuntimeException("Original File not found");
            else this.imageRepository.delete(original);

            String imageFile = FileUtilsService.uploadFile(model.getFile());
            File imageEntity = toEntity(imageFile, model.getFile());
            return this.imageRepository.save(imageEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Image add failed");
    }

    @Override
    public boolean deleteById(Long id) {
        File original = findById(id);
        FileUtilsService.deleteFile(original.getFileGuid());
        this.imageRepository.delete(original);
        return true;
    }

    @Override
    public boolean deleteByIds(List<Long> ids) {
        ids.forEach(id -> deleteById(id));
        return true;
    }
}
