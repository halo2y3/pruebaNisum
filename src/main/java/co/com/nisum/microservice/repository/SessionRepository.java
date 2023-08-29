package co.com.nisum.microservice.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.nisum.microservice.domain.Session;

public interface SessionRepository extends JpaRepository<Session, UUID>{

}
