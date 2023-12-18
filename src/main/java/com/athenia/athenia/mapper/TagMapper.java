package com.athenia.athenia.mapper;

import com.athenia.athenia.dto.TagDTO;
import com.athenia.athenia.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Vitalii Vasylykha
 * @company UnitedThinkers
 * @since 2023/12/17
 */
@Mapper
public interface TagMapper {
	TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

	TagDTO tagToTagDTO(Tag tag);

	Tag tagDTOToTag(TagDTO tagDTO);
}
