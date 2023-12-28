package com.athenia.athenia.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.Date;
import org.springframework.data.annotation.Id;

/**
 * @author Vitalii Vasylykha
 * @company UzhNU
 * @since 2023/12/28
 */
public class Exam {
	@Id
	private String id;
	@NotBlank
	private String title;
	@Min(0)
	private Double time;
	private Date createDate;
	private String description;
	private String link;

	public String getId() {
		return id;
	}

	public Exam setId(String id) {
		this.id = id;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public Exam setTitle(String title) {
		this.title = title;
		return this;
	}

	public Double getTime() {
		return time;
	}

	public Exam setTime(Double time) {
		this.time = time;
		return this;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Exam setCreateDate(Date createDate) {
		this.createDate = createDate;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Exam setDescription(String description) {
		this.description = description;
		return this;
	}

	public String getLink() {
		return link;
	}

	public Exam setLink(String link) {
		this.link = link;
		return this;
	}
}
