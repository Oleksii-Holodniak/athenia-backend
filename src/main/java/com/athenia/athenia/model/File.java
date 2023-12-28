package com.athenia.athenia.model;

/**
 * @author Vitalii Vasylykha
 * @company UzhNU
 * @since 2023/12/28
 */
public class File {
	private String link;
	private String name;
	private Long size;

	public String getLink() {
		return link;
	}

	public File setLink(String link) {
		this.link = link;
		return this;
	}

	public String getName() {
		return name;
	}

	public File setName(String name) {
		this.name = name;
		return this;
	}

	public Long getSize() {
		return size;
	}

	public File setSize(Long size) {
		this.size = size;
		return this;
	}
}
