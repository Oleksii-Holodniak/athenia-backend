package com.athenia.athenia.model;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * @author Vitalii Vasylykha
 * @company UzhNU
 * @since 2023/12/17
 */

public class Course {
	@Id
	private String id;
	@NotBlank
	private String title;
	private String preview;
	private String description;
	@NotBlank
	@Indexed(unique = true)
	private String securityCode;
	private List<String> tags;
	@Transient
	private Double time;

	public String getPreview() {
		return preview;
	}

	public Course setPreview(String preview) {
		this.preview = preview;
		return this;
	}

	public String getId() {
		return id;
	}

	public Course setId(String id) {
		this.id = id;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public Course setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Course setDescription(String description) {
		this.description = description;
		return this;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public Course setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
		return this;
	}

	public List<String> getTags() {
		return tags;
	}

	public Course setTags(List<String> tags) {
		this.tags = tags;
		return this;
	}

	public Double getTime() {
		return time;
	}

	public Course setTime(Double time) {
		this.time = time;
		return this;
	}
}
