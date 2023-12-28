package com.athenia.athenia.controller;

import com.athenia.athenia.dto.ExamDTO;
import com.athenia.athenia.exception.EntityNotFoundException;
import com.athenia.athenia.exception.ExistObjectException;
import com.athenia.athenia.mapper.ExamMapper;
import com.athenia.athenia.model.Exam;
import com.athenia.athenia.response.ListObjectResponse;
import com.athenia.athenia.service.ExamService;
import com.athenia.athenia.service.LectureReferenceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vitalii Vasylykha
 * @company UnitedThinkers
 * @since 2023/12/28
 */
@RestController
@RequestMapping("/api/course/exam")
public class ExamController {
	@Autowired
	private ExamService examService;
	@Autowired
	private LectureReferenceService lectureReferenceService;

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ListObjectResponse<ExamDTO> create(@RequestParam(value = "title") String title,
											  @RequestParam(value = "description") String description,
											  @RequestParam(value = "time") String time,
											  @RequestParam(value = "courseId") String courseId,
											  @RequestPart(value = "link") String link) {
		try {
			ExamDTO examDTO = new ExamDTO()
					.setTime(time)
					.setLink(link)
					.setTitle(title)
					.setDescription(description);
			Exam exam = examService.create(examDTO, courseId);
			return new ListObjectResponse<>(List.of(ExamMapper.INSTANCE.examToExamDTO(exam)));
		} catch (EntityNotFoundException | ExistObjectException exception) {
			return new ListObjectResponse<>(HttpStatus.BAD_REQUEST, exception);
		}
	}
}
