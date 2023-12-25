package com.athenia.athenia.mapper;

import com.athenia.athenia.dto.CourseDTO;
import com.athenia.athenia.dto.CourseFullDTO;
import com.athenia.athenia.model.Course;
import com.athenia.athenia.model.Tag;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Vitalii Vasylykha
 * @company UnitedThinkers
 * @since 2023/12/18
 */
@Mapper
public interface CourseMapper {
	CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

	CourseDTO courseToCourseDTO(Course course);
	CourseFullDTO courseToCourseFullDTO(Course course);

	@Mapping(target = "tags", ignore = true)
	Course courseDTOToCourse(CourseDTO courseDTO);

	default List<String> tagsToTagStrings(List<Tag> tags) {
		return tags.stream()
				.map(Tag::getTag)
				.collect(Collectors.toList());
	}
}
