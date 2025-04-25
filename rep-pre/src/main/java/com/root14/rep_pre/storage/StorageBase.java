package com.root14.rep_pre.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageBase {
    /**
     * @return saved file path
     */
    Boolean save(MultipartFile multipartFile,StorageType storageType) throws Exception;

    MultipartFile read(String fileName,StorageType storageType) throws Exception;
}
