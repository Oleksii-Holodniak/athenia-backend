package com.athenia.athenia.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import org.springframework.data.annotation.Id;

/**
 * @author Vitalii Vasylykha
 * @company UzhNU
 * @since 2023/12/27
 */
public class Lecture {
	@Id
	private String id;
	@NotBlank
	private String title;
	@Min(0)
	private Double time;
	private Date createDate;
	private String description;
	private List<File> files;

	public String getId() {
		return id;
	}

	public Lecture setId(String id) {
		this.id = id;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public Lecture setTitle(String title) {
		this.title = title;
		return this;
	}

	public Double getTime() {
		return time;
	}

	public Lecture setTime(Double time) {
		this.time = time;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Lecture setDescription(String description) {
		this.description = description;
		return this;
	}

	public List<File> getFiles() {
		return files;
	}

	public Lecture setFiles(List<File> files) {
		this.files = files;
		return this;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Lecture setCreateDate(Date createDate) {
		this.createDate = createDate;
		return this;
	}
}
