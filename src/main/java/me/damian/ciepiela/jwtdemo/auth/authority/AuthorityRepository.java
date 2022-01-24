package me.damian.ciepiela.jwtdemo.auth.authority;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.Set;

public interface AuthorityRepository extends MongoRepository<Authority, String> {
    Optional<Authority> findByName(String name);
    Set<Authority> findAllByNameIn(Set<String> names);
}
