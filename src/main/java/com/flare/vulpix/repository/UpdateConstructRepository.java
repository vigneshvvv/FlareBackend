package com.flare.vulpix.repository;

import com.flare.vulpix.model.updateConstruct.UpdateConstructDetailCollection;
import com.flare.vulpix.model.updateConstruct.UpdateConstructPrimaryKeys;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpdateConstructRepository extends MongoRepository<UpdateConstructDetailCollection, Long> {

    UpdateConstructDetailCollection findUpdateConstructDetailCollectionByUpdateConstructPrimaryKeys(UpdateConstructPrimaryKeys updateConstructPrimaryKeys);
}
