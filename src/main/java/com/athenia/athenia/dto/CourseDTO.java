package com.athenia.athenia.dto;

import java.util.List;

/**
 * @author Vitalii Vasylykha
 * @company UnitedThinkers
 * @since 2023/12/18
 */
public class CourseDTO {

	private String id;
	private String title;
	private String preview;
	private String description;
	private List<String> tags;

	public String getId() {
		return id;
	}

	public CourseDTO setId(String id) {
		this.id = id;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public CourseDTO setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public CourseDTO setDescription(String description) {
		this.description = description;
		return this;
	}

	public List<String> getTags() {
		return tags;
	}

	public CourseDTO setTags(List<String> tags) {
		this.tags = tags;
		return this;
	}

	public String getPreview() {
		return preview;
	}

	public CourseDTO setPreview(String preview) {
		this.preview = preview;
		return this;
	}
}
