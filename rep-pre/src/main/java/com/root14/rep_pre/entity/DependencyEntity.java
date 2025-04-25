package com.root14.rep_pre.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class DependencyEntity {

    private String pkg;
    private String version;


    public String getPkg() {
        return pkg;
    }

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
