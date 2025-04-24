package com.root14.object_storage.core;

import io.minio.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public class MinioStorageUtil {

    private MultipartFile convertToMultipartFile(byte[] fileData, String fileName) {
        String fileExtension = getFileExtension(fileName);

        String contentType = "application/octet-stream";
        if (fileExtension.equalsIgnoreCase(".rep")) {
            contentType = "application/octet-stream";
        } else if (fileExtension.equalsIgnoreCase(".json")) {
            contentType = "application/json";
        }

        return new CustomMultipartFile(fileData, fileName, contentType);
    }

    public String getFileExtension(String fileName) {
        if (fileName.lastIndexOf('.') > 0) {
            return fileName.substring(fileName.lastIndexOf('.'));
        }
        return "";
    }

    public MultipartFile downloadFile(MinioClient minioClient, String bucketName, String objectName) throws Exception {
        try (InputStream stream = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build())) {
            byte[] fileData = stream.readAllBytes();
            return convertToMultipartFile(fileData, objectName);
        }
    }

    public void uploadFile(MinioClient minioClient, MultipartFile file, String bucketName) throws Exception {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }

        minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(file.getOriginalFilename()).stream(file.getInputStream(), file.getSize(), -1).contentType(file.getContentType()).build());
    }
}
