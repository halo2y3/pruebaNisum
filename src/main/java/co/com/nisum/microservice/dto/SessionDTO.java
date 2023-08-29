package co.com.nisum.microservice.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import co.com.nisum.microservice.utility.Utilities;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionDTO {

    private UUID idSession;
    private String token;
    private boolean isActive;
    private LocalDateTime lastLogin;
    private UserRequestDTO user;
    
    @Override
    public String toString() {
    	return Utilities.toStringObjec(this);
    }
    
}
