package org.tzi.use.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.tzi.use.entities.ModelNTT;

public interface ModelRepo extends MongoRepository<ModelNTT, String> {
}
