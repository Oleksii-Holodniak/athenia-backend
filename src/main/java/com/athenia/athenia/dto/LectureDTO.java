package com.athenia.athenia.dto;

import java.util.List;

/**
 * @author Vitalii Vasylykha
 * @company UzhNU
 * @since 2023/12/27
 */
public class LectureDTO {
	private String id;
	private String title;
	private String description;
	private String time;
	private String serial;
	private String createDate;
	private List<FileDto> files;

	public String getId() {
		return id;
	}

	public LectureDTO setId(String id) {
		this.id = id;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public LectureDTO setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public LectureDTO setDescription(String description) {
		this.description = description;
		return this;
	}

	public String getTime() {
		return time;
	}

	public LectureDTO setTime(String time) {
		this.time = time;
		return this;
	}

	public String getSerial() {
		return serial;
	}

	public LectureDTO setSerial(String serial) {
		this.serial = serial;
		return this;
	}

	public String getCreateDate() {
		return createDate;
	}

	public LectureDTO setCreateDate(String createDate) {
		this.createDate = createDate;
		return this;
	}

	public List<FileDto> getFiles() {
		return files;
	}

	public LectureDTO setFiles(List<FileDto> files) {
		this.files = files;
		return this;
	}
}
