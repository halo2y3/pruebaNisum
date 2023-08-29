package co.com.nisum.microservice.dto;

import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import co.com.nisum.microservice.utility.Utilities;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneDTO implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private UUID idPhone;
    private String number;
    @JsonProperty("citycode")
    private String cityCode;
    @JsonProperty("countrycode")
    private String countryCode;
    
    @Override
    public String toString() {
    	return Utilities.toStringObjec(this);
    }

}
