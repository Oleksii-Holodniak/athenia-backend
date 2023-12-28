package com.athenia.athenia.controller;

import com.athenia.athenia.dto.LectureDTO;
import com.athenia.athenia.exception.EntityNotFoundException;
import com.athenia.athenia.exception.ExistObjectException;
import com.athenia.athenia.mapper.LectureMapper;
import com.athenia.athenia.model.Lecture;
import com.athenia.athenia.model.LectureReference;
import com.athenia.athenia.response.ListObjectResponse;
import com.athenia.athenia.service.LectureReferenceService;
import com.athenia.athenia.service.LectureService;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Vitalii Vasylykha
 * @company UnitedThinkers
 * @since 2023/12/27
 */
@RestController
@RequestMapping("/api/course/lecture")
public class LectureController {

	@Autowired
	private LectureService lectureService;
	@Autowired
	private LectureReferenceService lectureReferenceService;

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ListObjectResponse<LectureDTO> create(@RequestParam(value = "title") String title,
												 @RequestParam(value = "description") String description,
												 @RequestParam(value = "time") String time,
												 @RequestParam(value = "serial", required = false) String serial,
												 @RequestParam(value = "courseId") String courseId,
												 @RequestPart(value = "files") List<MultipartFile> files) {
		try {
			LectureDTO lectureDTO = new LectureDTO()
					.setTime(time)
					.setDescription(description)
					.setTitle(title)
					.setSerial(serial);
			Lecture lecture = lectureService.create(lectureDTO, courseId, files);
			LectureReference lectureReference = lectureReferenceService.findByLecture(lecture);
			return new ListObjectResponse<>(List.of(LectureMapper.INSTANCE.lectureToLectureDTO(lecture, lectureReference)));
		} catch (EntityNotFoundException | IOException | ExistObjectException exception) {
			return new ListObjectResponse<>(HttpStatus.BAD_REQUEST, exception);
		}
	}

}
