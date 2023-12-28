package com.athenia.athenia.mapper;

import com.athenia.athenia.dto.ExamDTO;
import com.athenia.athenia.model.Exam;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Vitalii Vasylykha
 * @company UzhNU
 * @since 2023/12/28
 */
@Mapper
public interface ExamMapper {
	ExamMapper INSTANCE = Mappers.getMapper(ExamMapper.class);

	ExamDTO examToExamDTO(Exam exam);

	Exam examDTOToExam(ExamDTO examDTO);
}
