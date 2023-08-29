package co.com.nisum.microservice.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;

import co.com.nisum.microservice.utility.Utilities;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private UUID idUser;
    private String name;
    @NotEmpty(message = "El email no puede estar vacio")
    private String email;
    @NotEmpty(message = "El password no puede estar vacio")
    private String password;
    private List<PhoneDTO> phones;
    
    @Override
    public String toString() {
    	return Utilities.toStringObjec(this);
    }
}
