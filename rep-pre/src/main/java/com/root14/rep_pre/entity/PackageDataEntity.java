package com.root14.rep_pre.entity;

import com.root14.rep_pre.dto.Dependency;
import com.root14.rep_pre.storage.StorageType;
import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class PackageDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String version;
    private String author;
    //this field holds with .rep name+version
    private String repName;
    //this field holds with .rep name+version
    private String metadataName;

    @Enumerated(EnumType.STRING)
    private StorageType storageType;

    public StorageType getStorageType() {
        return storageType;
    }

    public void setStorageType(StorageType storageType) {
        this.storageType = storageType;
    }

    @ElementCollection
    @CollectionTable(name = "package_dependencies", joinColumns = @JoinColumn(name = "package_id"))
    private List<DependencyEntity> dependencies;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getRepName() {
        return repName;
    }

    public void setRepName(String repName) {
        this.repName = repName;
    }

    public String getMetadataName() {
        return metadataName;
    }

    public void setMetadataName(String metadataName) {
        this.metadataName = metadataName;
    }

    public List<DependencyEntity> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<DependencyEntity> dependencies) {
        this.dependencies = dependencies;
    }

    public void setDependenciesFromDto(List<Dependency> dependencies) {
        List<DependencyEntity> entities = dependencies.stream().map(dep -> {
            DependencyEntity entity = new DependencyEntity();
            entity.setPkg(dep.getPkg());
            entity.setVersion(dep.getVersion());
            return entity;
        }).collect(Collectors.toList());

        this.setDependencies(entities);
    }

}
