package org.tzi.use.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.tzi.use.entities.ClassNTT;

public interface ClassRepo extends MongoRepository<ClassNTT, String> {
}
