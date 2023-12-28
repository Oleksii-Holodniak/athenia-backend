package com.athenia.athenia.service;

import com.athenia.athenia.dto.LectureDTO;
import com.athenia.athenia.mapper.LectureMapper;
import com.athenia.athenia.model.Course;
import com.athenia.athenia.model.File;
import com.athenia.athenia.model.Lecture;
import com.athenia.athenia.model.LectureReference;
import com.athenia.athenia.repository.LectureRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Vitalii Vasylykha
 * @company UnitedThinkers
 * @since 2023/12/27
 */
@Service
public class LectureService {

	@Autowired
	private FileService fileService;
	@Autowired
	private LectureRepository lectureRepository;
	@Autowired
	private LectureReferenceService lectureReferenceService;

	public List<Lecture> findLectures(Course course) {
		return lectureReferenceService.findByCourse(course).stream()
				.map(LectureReference::getLecture)
				.filter(Objects::nonNull)
				.toList();
	}

	public Lecture create(LectureDTO lectureDTO, String courseId, List<MultipartFile> files) throws IOException {
		Lecture lecture = LectureMapper.INSTANCE.lectureDTOToLecture(lectureDTO)
				.setFiles(createFiles(files))
				.setCreateDate(new Date());
		lecture = lectureRepository.save(lecture);
		lectureReferenceService.create(lecture, lectureDTO.getSerial(), courseId);
		return lecture;
	}

	private List<File> createFiles(List<MultipartFile> files) throws IOException {
		List<File> filesLecture = new ArrayList<>(files.size());
		for (MultipartFile file : files) {
			filesLecture.add(createFile(file));
		}
		return filesLecture;
	}

	private File createFile(MultipartFile file) throws IOException {
		String fileUrl = fileService.uploadFile(file);
		return new File().setName(file.getOriginalFilename())
				.setSize(file.getSize())
				.setLink(fileUrl);
	}

}
