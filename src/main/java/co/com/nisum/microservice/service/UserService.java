package co.com.nisum.microservice.service;

import java.util.Optional;
import java.util.UUID;

import co.com.nisum.microservice.domain.User;
import co.com.nisum.microservice.dto.LoginRequestDTO;
import co.com.nisum.microservice.dto.LoginResponseDTO;

public interface UserService extends GenericService<User, UUID> {

    public Optional<User> getUserByEmail(String email);
    public LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO);
    
}
