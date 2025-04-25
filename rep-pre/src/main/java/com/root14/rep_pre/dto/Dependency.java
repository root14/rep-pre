package com.root14.rep_pre.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Dependency {
    private String pkg;
    private String version;

    public String getPkg() {
        return pkg;
    }

    @JsonProperty("package")
    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
