package com.root14.rep_pre.repository;

import com.root14.rep_pre.entity.PackageDataEntity;
import org.springframework.data.repository.CrudRepository;

public interface PackageDataRepository extends CrudRepository<PackageDataEntity,Long> {
   public Boolean existsByNameAndVersion(String name, String version);
}
