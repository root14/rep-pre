package com.root14.rep_pre.storage;

import com.root14.file_system_storage.core.FileStorage;
import com.root14.object_storage.core.MinioStorageUtil;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class StorageStrategy implements StorageBase {

    private final MinioStorageUtil minioStorageUtil;
    private final FileStorage fileStorage;

    @Value("${storage.default.path}")
    private String defaultPath;

    @Value("${storage.default.bucketName}")
    private String defaultBucketName;

    private final MinioClient minioClient;

    public StorageStrategy(MinioStorageUtil minioStorageUtil, FileStorage fileStorage, MinioClient minioClient) {
        this.minioStorageUtil = minioStorageUtil;
        this.fileStorage = fileStorage;
        this.minioClient = minioClient;
    }

    @Override
    public Boolean save(MultipartFile multipartFile, StorageType storageType) throws Exception {
        switch (storageType) {
            case FILE_SYSTEM -> {
                fileStorage.saveFile(multipartFile, defaultPath);
                return true;
            }
            case OBJECT_STORAGE -> {
                minioStorageUtil.uploadFile(minioClient, multipartFile, defaultBucketName);
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    @Override
    public MultipartFile read(String fileName, StorageType storageType) throws Exception {
        switch (storageType) {
            case FILE_SYSTEM -> {
                return fileStorage.readFile(defaultPath, fileName);
            }
            case OBJECT_STORAGE -> {
                return minioStorageUtil.downloadFile(minioClient, defaultBucketName, fileName);
            }
            default -> {
                return null;
            }
        }
    }
}


