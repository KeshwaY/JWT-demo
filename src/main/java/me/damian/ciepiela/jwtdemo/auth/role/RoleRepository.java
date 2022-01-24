package me.damian.ciepiela.jwtdemo.auth.role;

import me.damian.ciepiela.jwtdemo.auth.authority.Authority;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(String name);

    @Aggregation(pipeline = {
            "{$lookup: {from: 'authorities', localField: 'authorities', foreignField: '_id', as: 'authoritiesDoc'}}",
            "{$match: {'authoritiesDoc.name': ?0}}",
            "{$project: {authoritiesDoc: 0}}"
    })
    Optional<Role> findByAuthorityName(String name);

    Set<Role> findByAuthoritiesIsContaining(@NotNull Set<Authority> authorities);
}
