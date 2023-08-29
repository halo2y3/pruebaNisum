package co.com.nisum.microservice.service;

import java.util.Optional;
import java.util.UUID;

import co.com.nisum.microservice.domain.User;

public interface UserService extends GenericService<User, UUID> {

    public Optional<User> getUserByEmail(String email);
    
}
