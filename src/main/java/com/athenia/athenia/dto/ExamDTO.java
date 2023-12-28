package com.athenia.athenia.dto;

/**
 * @author Vitalii Vasylykha
 * @company UnitedThinkers
 * @since 2023/12/28
 */
public class ExamDTO {
	private String title;
	private String description;
	private String time;
	private String createDate;
	private String link;

	public String getTitle() {
		return title;
	}

	public ExamDTO setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public ExamDTO setDescription(String description) {
		this.description = description;
		return this;
	}

	public String getTime() {
		return time;
	}

	public ExamDTO setTime(String time) {
		this.time = time;
		return this;
	}

	public String getCreateDate() {
		return createDate;
	}

	public ExamDTO setCreateDate(String createDate) {
		this.createDate = createDate;
		return this;
	}

	public String getLink() {
		return link;
	}

	public ExamDTO setLink(String link) {
		this.link = link;
		return this;
	}
}
