package com.athenia.athenia.mapper;

import com.athenia.athenia.dto.UserDTO;
import com.athenia.athenia.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Vitalii Vasylykha
 * @company UnitedThinkers
 * @since 2023/11/12
 */
@Mapper
public interface UserMapper {
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	UserDTO userToUserDTO(User user);

	@Mapping(target = "password", ignore = true)
	User userDTOToUser(UserDTO userDTO);
}