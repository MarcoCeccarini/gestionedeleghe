package org.mce.gestionedeleghe.repository;

import org.mce.gestionedeleghe.domain.Delega;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Delega entity.
 */
public interface DelegaRepository extends MongoRepository<Delega,String> {

}
