package co.com.nisum.microservice.dto;

import java.io.Serializable;

import co.com.nisum.microservice.utility.Utilities;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private String token;
    
    @Override
    public String toString() {
    	return Utilities.toStringObjec(this);
    }

}
