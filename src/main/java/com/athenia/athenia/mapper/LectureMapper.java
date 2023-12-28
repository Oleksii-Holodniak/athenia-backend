package com.athenia.athenia.mapper;

import com.athenia.athenia.dto.LectureDTO;
import com.athenia.athenia.model.Lecture;
import com.athenia.athenia.model.LectureReference;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * @author Vitalii Vasylykha
 * @company UzhNU
 * @since 2023/12/27
 */
@Mapper
public interface LectureMapper {
	LectureMapper INSTANCE = Mappers.getMapper(LectureMapper.class);

	@Mapping(target = "serial", ignore = true)
	LectureDTO lectureToLectureDTO(Lecture course, LectureReference lectureReference);

	Lecture lectureDTOToLecture(LectureDTO courseDTO);

	@Mapping(target = "serial", expression = "java(lectureReference.getSerial())")
	LectureReference lectureDTOToLectureReference(LectureDTO lectureDTO);

	@AfterMapping
	default void mapSerialFromLectureReference(LectureReference lectureReference, @MappingTarget LectureDTO lectureDTO) {
		lectureDTO.setSerial(String.valueOf(lectureReference.getSerial()));
	}
}
