package com.kh.campingez.inquire.model.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class Inquire extends InquireEntity {

	private int answerStatus;
	private Answer answer;
	private String categoryName;

	public Inquire(String inqNo, String categoryId, String inqWriter, String inqTitle, String inqContent,
			LocalDateTime inqDate, LocalDateTime inqUpdatedDate, int answerStatus, String categoryName) {
		super(inqNo, categoryId, inqWriter, inqTitle, inqContent, inqDate, inqUpdatedDate);
		this.answerStatus = answerStatus;
		this.categoryName = categoryName;
	}

	
}
