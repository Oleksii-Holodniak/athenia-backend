package com.athenia.athenia.service;

import com.athenia.athenia.dto.ExamDTO;
import com.athenia.athenia.mapper.ExamMapper;
import com.athenia.athenia.model.Course;
import com.athenia.athenia.model.Exam;
import com.athenia.athenia.model.LectureReference;
import com.athenia.athenia.repository.ExamRepository;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Vitalii Vasylykha
 * @company UnitedThinkers
 * @since 2023/12/28
 */
@Service
public class ExamService {

	@Autowired
	private ExamRepository examRepository;
	@Autowired
	private LectureReferenceService lectureReferenceService;

	public Exam create(ExamDTO examDTO, String courseId) {
		Exam exam = ExamMapper.INSTANCE.examDTOToExam(examDTO)
				.setCreateDate(new Date());
		exam = examRepository.save(exam);
		lectureReferenceService.create(exam, courseId);
		return exam;
	}

	public List<Exam> findLectures(Course course) {
		return lectureReferenceService.findByCourse(course).stream()
				.map(LectureReference::getExam)
				.filter(Objects::nonNull)
				.toList();
	}
}
