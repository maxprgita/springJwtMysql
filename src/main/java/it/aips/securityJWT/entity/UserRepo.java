package it.aips.securityJWT.entity;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User,Integer> {
    Optional<User> findByEmail(String email);

}
