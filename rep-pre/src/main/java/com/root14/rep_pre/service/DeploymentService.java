package com.root14.rep_pre.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.root14.rep_pre.core.ExtensionValidator;
import com.root14.rep_pre.dto.PackageInfo;
import com.root14.rep_pre.entity.PackageDataEntity;
import com.root14.rep_pre.repository.PackageDataRepository;
import com.root14.rep_pre.storage.StorageStrategy;
import com.root14.rep_pre.storage.StorageType;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DeploymentService {

    private final StorageStrategy storageStrategy;
    private final ExtensionValidator extensionValidator;
    private final PackageDataRepository packageDataRepository;

    // 0|1 -> object-storage | file-system
    @Value("${storage.strategy}")
    private String storageFlag;

    ObjectMapper objectMapper = new ObjectMapper();

    public DeploymentService(StorageStrategy storageStrategy, ExtensionValidator extensionValidator, PackageDataRepository packageDataRepository, MinioClient minioClient) {
        this.storageStrategy = storageStrategy;
        this.extensionValidator = extensionValidator;
        this.packageDataRepository = packageDataRepository;
    }

    public ResponseEntity<?> deploy(String packageName, String version, MultipartFile packageFileRep, MultipartFile metadataJson) throws Exception {

        if (packageFileRep == null && !extensionValidator.validateExtension(packageFileRep, ".rep")) {
            return ResponseEntity.status(406).body("there should be .rep file.");
        }

        if (metadataJson == null && !extensionValidator.validateExtension(metadataJson, ".json")) {
            return ResponseEntity.status(406).body("there should be .json file.");
        }

        if (checkIfNameAndVersionExist(packageName, version)) {
            return ResponseEntity.status(409).body("package name already exist.");
        }

        String content = new String(metadataJson.getBytes());
        //save to postgresql to track packages && deserialize json
        PackageInfo packageInfo = objectMapper.readValue(content, PackageInfo.class);

        PackageDataEntity packageDataEntity = new PackageDataEntity();

        //check name by packageName
        packageDataEntity.setName(packageName);
        packageDataEntity.setAuthor(packageInfo.getAuthor());
        packageDataEntity.setVersion(version);
        packageDataEntity.setDependenciesFromDto(packageInfo.getDependencies());
        packageDataEntity.setStorageType(getStorageType());


        packageDataEntity.setRepName(packageFileRep.getOriginalFilename());
        storageStrategy.save(metadataJson, getStorageType());

        packageDataEntity.setMetadataName(metadataJson.getOriginalFilename());
        storageStrategy.save(metadataJson, getStorageType());

        //save to postgres for track the dependency
        packageDataRepository.save(packageDataEntity);

        return ResponseEntity.status(201).build();
    }

    private Boolean checkIfNameAndVersionExist(String name, String version) {
        return packageDataRepository.existsByNameAndVersion(name, version);
    }

    public StorageType getStorageType() {
        if (storageFlag.equals("0")) {
            return StorageType.OBJECT_STORAGE;
        } else if (storageFlag.equals("1")) {
            return StorageType.FILE_SYSTEM;
        } else {
            //todo throw error?
            return null;
        }
    }

}