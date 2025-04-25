package com.root14.rep_pre.storage;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class StorageStrategy implements StorageBase {

    @Override
    public Boolean save(MultipartFile multipartFile, StorageType storageType) throws Exception {
        //todo implement libraries
        switch (storageType){
            case FILE_SYSTEM -> {}
            case OBJECT_STORAGE -> {}
            default -> {}
        }
        return true;
    }

    @Override
    public MultipartFile read(String fileName, StorageType storageType) throws Exception {
        //todo implement libraries
        switch (storageType){
            case FILE_SYSTEM -> {}
            case OBJECT_STORAGE -> {}
            default -> {}
        }
        return null;
    }
}


