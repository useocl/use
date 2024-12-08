package org.tzi.use.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.tzi.use.model.UseClass;

public interface ClassRepo extends MongoRepository<UseClass, String> {
}
