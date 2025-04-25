package com.root14.rep_pre.config;

import com.root14.file_system_storage.core.FileStorage;
import com.root14.object_storage.core.MinioStorageUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageLibraryConfig {

    @Bean
    public FileStorage provideFileStorage(){
        return new FileStorage();
    }

    @Bean
    public MinioStorageUtil provideMinioStorageUtil(){
        return new MinioStorageUtil();
    }

}
