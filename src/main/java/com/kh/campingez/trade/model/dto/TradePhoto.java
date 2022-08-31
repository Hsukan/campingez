package com.kh.campingez.trade.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class TradePhoto {
	
	private String tradePhotoNo;
	private String tdNo;
	@NonNull
	private String OriginalFilename;
	@NonNull
	private String renamedFilename;
}
