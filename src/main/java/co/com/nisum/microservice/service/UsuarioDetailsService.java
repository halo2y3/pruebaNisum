package co.com.nisum.microservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.com.nisum.microservice.repository.UserRepository;

@Service
public class UsuarioDetailsService implements UserDetailsService  {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

	Optional<co.com.nisum.microservice.domain.User> userBD = userRepository.getUserByEmail(username);

	if(userBD.isPresent()) {
	    User.UserBuilder userBuilder = User.withUsername(username);
	    userBuilder.password(userBD.get().getPassword()).roles("USER");
	    return userBuilder.build();
	}else {
	    throw new UsernameNotFoundException(username);
	}
    }
}
