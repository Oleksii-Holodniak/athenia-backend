package com.athenia.athenia.service;

import com.athenia.athenia.exception.EntityNotFoundException;
import com.athenia.athenia.model.Course;
import com.athenia.athenia.model.Exam;
import com.athenia.athenia.model.Lecture;
import com.athenia.athenia.model.LectureReference;
import com.athenia.athenia.repository.LectureReferenceRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Vitalii Vasylykha
 * @company UnitedThinkers
 * @since 2023/12/27
 */
@Service
public class LectureReferenceService {
	@Autowired
	private CourseService courseService;
	@Autowired
	private LectureReferenceRepository lectureReferenceRepository;

	public LectureReference create(Lecture lecture, String serialId, String courseId) {
		Course course = courseService.findById(courseId);
		LectureReference lectureReference = new LectureReference()
				.setCourse(course)
				.setLecture(lecture)
				.setSerial(Integer.valueOf(serialId));
		return lectureReferenceRepository.save(lectureReference);
	}

	public LectureReference create(Exam exam, String courseId) {
		Course course = courseService.findById(courseId);
		LectureReference lectureReference = new LectureReference()
				.setCourse(course)
				.setExam(exam);
		return lectureReferenceRepository.save(lectureReference);
	}

	public LectureReference findByLecture(Lecture lecture) {
		return lectureReferenceRepository.findByLecture(lecture)
				.orElseThrow(() -> new EntityNotFoundException(Course.class, lecture.getId()));
	}

	public List<LectureReference> findByCourse(Course course) {
		return lectureReferenceRepository.findByCourse(course);
	}

	public Double findTime(Course course) {
		List<LectureReference> lectureReferences = findByCourse(course);
		Double timeLecture = lectureReferences.stream()
				.filter(lectureReference -> lectureReference.getLecture() != null)
				.mapToDouble(lectureReference -> lectureReference.getLecture().getTime())
				.sum();
		Double examLecture = lectureReferences.stream()
				.filter(lectureReference -> lectureReference.getExam() != null)
				.mapToDouble(lectureReference -> lectureReference.getExam().getTime())
				.sum();
		return timeLecture + examLecture;
	}
}
