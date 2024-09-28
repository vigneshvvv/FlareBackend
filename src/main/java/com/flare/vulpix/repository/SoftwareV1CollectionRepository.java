package com.flare.vulpix.repository;

import com.flare.vulpix.model.software.SoftwarePacakgeDetailCollection;
import com.flare.vulpix.model.software.SoftwarePackagePrimaryKeys;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoftwareV1CollectionRepository extends MongoRepository<SoftwarePacakgeDetailCollection, Long> {
    SoftwarePacakgeDetailCollection findSoftwarePacakgeDetailCollectionsBySoftwarePackagePrimaryKeys(SoftwarePackagePrimaryKeys softwarePackagePrimaryKeys);
}
