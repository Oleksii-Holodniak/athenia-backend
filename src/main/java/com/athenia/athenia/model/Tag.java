package com.athenia.athenia.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Vitalii Vasylykha
 * @company UnitedThinkers
 * @since 2023/12/17
 */

@Document(collection = "tag")
public class Tag {

	@Id
	@Indexed(unique = true)
	private String tag;

	public String getTag() {
		return tag;
	}

	public Tag setTag(String tag) {
		this.tag = tag;
		return this;
	}
}
