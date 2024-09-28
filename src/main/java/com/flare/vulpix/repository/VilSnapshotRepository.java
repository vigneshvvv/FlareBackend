package com.flare.vulpix.repository;

import com.flare.vulpix.model.vilSnapshotTrigger.VilSnapshotTrigger;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VilSnapshotRepository extends MongoRepository<VilSnapshotTrigger, Long> {
}
