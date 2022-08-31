package com.kh.campingez.inquire.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class Inquire extends InquireEntity {

	private int answerStatus;

	public Inquire(String inqNo, String categoryId, String inqWriter, String inqTitle, String inqContent,
			LocalDateTime inqDate, int answerStatus) {
		super(inqNo, categoryId, inqWriter, inqTitle, inqContent, inqDate);
		this.answerStatus = answerStatus;
	}
	
	
	
}
