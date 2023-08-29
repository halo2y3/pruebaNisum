package co.com.nisum.microservice.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.com.nisum.microservice.domain.User;

public interface UserRepository extends JpaRepository<User, UUID>{

    @Query(value = "SELECT * FROM users where email=?1 ", nativeQuery = true)
    public Optional<User> getUserByEmail(String email);

}
