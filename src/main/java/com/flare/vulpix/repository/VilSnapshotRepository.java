package com.flare.vulpix.repository;

import com.flare.vulpix.model.vilSnapshotTrigger.VilSnapshotTrigger;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface VilSnapshotRepository extends MongoRepository<VilSnapshotTrigger, Long> {

    Optional<VilSnapshotTrigger> findVilSnapshotTriggerById(String id);
}
