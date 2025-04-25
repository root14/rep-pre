package com.root14.rep_pre.core;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Component
public class ExtensionValidator {
    /**
     * @param multipartFile should not be null.
     * @param expected      expected  extension.
     * @return Boolean value.
     */
    public Boolean validateExtension(MultipartFile multipartFile, String expected) {
        return Objects.requireNonNull(multipartFile.getOriginalFilename()).endsWith(expected);
    }
}
