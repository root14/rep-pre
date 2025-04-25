package com.root14.rep_pre.controller;

import com.root14.rep_pre.service.DeploymentService;
import com.root14.rep_pre.service.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MainController {
    private final DeploymentService deploymentService;
    private final DownloadService downloadService;

    @Autowired
    public MainController(DeploymentService deploymentService, DownloadService downloadService) {
        this.deploymentService = deploymentService;
        this.downloadService = downloadService;
    }

    /**
     * path variables are not validated due to
     * @see <a href="https://stackoverflow.com/a/78673113">this answer</a>
     */
    @PostMapping("/{packageName}/{version}")
    public ResponseEntity<?> deployPackage(@PathVariable String packageName, @PathVariable String version, @RequestParam MultipartFile packageFileRep, @RequestParam MultipartFile metadataJson) throws Exception {
       return deploymentService.deploy(packageName,version,packageFileRep,metadataJson);
    }

    @GetMapping("/{packageName}/{version}")
    public ResponseEntity<?> downloadPackage(@PathVariable String packageName, @PathVariable String version) throws Exception {
        String result = "packageName:" + packageName + " version:" + version;
        //todo add not use special chars for path-vars(xss ')
        return downloadService.download(packageName, version);
    }
}
