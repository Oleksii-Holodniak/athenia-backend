package com.athenia.athenia.dto;

/**
 * @author Vitalii Vasylykha
 * @company UnitedThinkers
 * @since 2023/12/28
 */
public class FileDto {
	private String link;
	private String name;
	private String size;

	public String getLink() {
		return link;
	}

	public FileDto setLink(String link) {
		this.link = link;
		return this;
	}

	public String getName() {
		return name;
	}

	public FileDto setName(String name) {
		this.name = name;
		return this;
	}

	public String getSize() {
		return size;
	}

	public FileDto setSize(String size) {
		this.size = size;
		return this;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("{\n");
		sb.append("link: \"").append(link).append("\", \n");
		sb.append("name: \"").append(name).append("\", \n");
		sb.append("size: \"").append(size).append("\" \n");
		sb.append('}');
		return sb.toString();
	}
}
