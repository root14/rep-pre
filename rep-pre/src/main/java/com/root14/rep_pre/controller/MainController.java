package com.root14.rep_pre.controller;

import com.root14.rep_pre.service.DeploymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MainController {
    private final DeploymentService deploymentService;

    @Autowired
    public MainController(DeploymentService deploymentService) {
        this.deploymentService = deploymentService;
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
    public ResponseEntity<String> downloadPackage(@PathVariable String packageName, @PathVariable String version) {
        String result = "packageName:" + packageName + " version:" + version;
        //todo add not use special chars for path-vars(xss ')
        return ResponseEntity.ok(result);
    }
}
