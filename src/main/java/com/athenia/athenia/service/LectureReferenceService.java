package com.athenia.athenia.service;

import com.athenia.athenia.exception.EntityNotFoundException;
import com.athenia.athenia.exception.ExistObjectException;
import com.athenia.athenia.model.Course;
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
		try {
			LectureReference existLectureReference = findByLecture(lecture);
			if (existLectureReference != null) {
				throw new ExistObjectException(LectureReference.class, serialId);
			}
		} catch (EntityNotFoundException ex) {
		}
		LectureReference lectureReference = new LectureReference()
				.setCourse(course)
				.setLecture(lecture)
				.setSerial(Integer.valueOf(serialId));
		return lectureReferenceRepository.save(lectureReference);
	}

	public LectureReference findByLecture(Lecture lecture) {
		return lectureReferenceRepository.findByLecture(lecture)
				.orElseThrow(() -> new EntityNotFoundException(Course.class, lecture.getId()));
	}

	public List<LectureReference> findByCourse(Course course) {
		return lectureReferenceRepository.findByCourse(course);
	}
}
