package com.root14.rep_pre.service;

import com.root14.rep_pre.entity.PackageDataEntity;
import com.root14.rep_pre.repository.PackageDataRepository;
import com.root14.rep_pre.storage.StorageStrategy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                ZipOutputStream zipOut = new ZipOutputStream(byteArrayOutputStream);

                //throws if it null
                zipOut.putNextEntry(new ZipEntry(Objects.requireNonNull(metadataJson.getOriginalFilename())));
                zipOut.write(metadataJson.getBytes());
                zipOut.closeEntry();

                //throws if it null
                zipOut.putNextEntry(new ZipEntry(Objects.requireNonNull(packageRep.getOriginalFilename())));
                zipOut.write(metadataJson.getBytes());
                zipOut.closeEntry();

                zipOut.finish();

                byte[] zipBytes = byteArrayOutputStream.toByteArray();

                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                httpHeaders.setContentDispositionFormData("attachment", "files.zip");

                return new ResponseEntity<>(zipBytes, httpHeaders, HttpStatus.OK);
            } catch (Exception e) {
                return ResponseEntity.internalServerError().body("error at zip process. exception is ->" + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }

    }

}
