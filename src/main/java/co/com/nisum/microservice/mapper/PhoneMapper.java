package co.com.nisum.microservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import co.com.nisum.microservice.domain.Phone;
import co.com.nisum.microservice.dto.PhoneDTO;

@Mapper
public interface PhoneMapper {
    
    public PhoneDTO phoneToPhoneDTO(Phone phone);

    public Phone phoneDTOToPhone(PhoneDTO phoneDTO);

    public List<PhoneDTO> listPhoneToListPhoneDTO(List<Phone> phones);

    public List<Phone> listPhoneDTOToListPhone(List<PhoneDTO> phoneDTOs);

}
