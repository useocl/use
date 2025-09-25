package org.tzi.use.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.tzi.use.DTO.ClassDTO;

public interface ClassRepo extends MongoRepository<ClassDTO, String> {
}
