package co.com.nisum.microservice.dto;

import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.com.nisum.microservice.utility.Utilities;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    @JsonProperty("id_user")
    private UUID idUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String created;
    private String modified;
    @JsonProperty("last_login")
    private String lastLogin;
    private String token;
    @JsonProperty("is_active")
    private boolean isActive;

    @Override
    public String toString() {
	return Utilities.toStringObjec(this);
    }
}
