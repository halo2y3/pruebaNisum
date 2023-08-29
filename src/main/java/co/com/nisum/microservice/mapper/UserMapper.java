package co.com.nisum.microservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.com.nisum.microservice.domain.User;
import co.com.nisum.microservice.dto.UserRequestDTO;

@Mapper(imports = co.com.nisum.microservice.utility.Utilities.class)
public interface UserMapper {
    
    public UserRequestDTO userToUserDTO(User user);

    @Mapping(target = "phones", expression = "java((Utilities.mappPhones(userDTO.getPhones(), userDTO.getIdUser())))")
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    public User userDTOToUser(UserRequestDTO userDTO);

    public List<UserRequestDTO> listUserToListUserDTO(List<User> users);

    public List<User> listUserDTOToListUser(List<UserRequestDTO> userDTOs);

}
