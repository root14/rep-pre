package com.root14.rep_pre.service;

import com.root14.rep_pre.entity.PackageDataEntity;
import com.root14.rep_pre.repository.PackageDataRepository;
import com.root14.rep_pre.storage.StorageStrategy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DownloadService {

    private final StorageStrategy storageStrategy;
    private final PackageDataRepository packageDataRepository;

    public DownloadService(PackageDataRepository packageDataRepository, StorageStrategy storageStrategy) {
        this.packageDataRepository = packageDataRepository;
        this.storageStrategy = storageStrategy;
    }

    public ResponseEntity<?> download(String name, String version) throws Exception {

        PackageDataEntity packageDataEntity = packageDataRepository.findByNameAndVersion(name, version);

        if (packageDataEntity == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            MultipartFile metadataJson = storageStrategy.read(packageDataEntity.getMetadataName(), packageDataEntity.getStorageType());
            MultipartFile packageRep = storageStrategy.read(packageDataEntity.getRepName(), packageDataEntity.getStorageType());

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }

    }

}
