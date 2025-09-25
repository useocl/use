package org.tzi.use.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.tzi.use.DTO.ModelDTO;

public interface ModelRepo extends MongoRepository<ModelDTO, String> {
}
