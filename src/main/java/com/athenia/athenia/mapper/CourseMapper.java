package com.athenia.athenia.mapper;

import com.athenia.athenia.dto.CourseDTO;
import com.athenia.athenia.model.Course;
import org.mapstruct.Mapper;
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

	Course courseDTOToCourse(CourseDTO courseDTO);
}