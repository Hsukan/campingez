package com.kh.campingez.data.model.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kh.campingez.data.model.dto.Response;
import com.kh.campingez.data.model.exception.DataProcessingException;

@Service
public class DataServiceImpl implements DataService {

	private static final String WEATHER_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst";
	private static final String WEATHER_SERVICE_KEY = "nkedH2GBDF%2BTCm2VLMxTXfjbK5uG7xtbtLDOXdsDlb%2F4S5NAykAK5f4zhhjMpTM7GUf1pmqRcrC7nTOPF4iAgw%3D%3D";
	
	@Override
	public Response getWeather(LocalDate date, String time) {
		Response response = null;
		ObjectMapper xmlMapper = new XmlMapper().registerModule(new JavaTimeModule());
		try {
			response = xmlMapper.readValue(urlFormatter(date, time), Response.class);
		} catch (IOException e) {
			throw new DataProcessingException(WEATHER_URL, e);
		}
		return response;
	}
	
	private URL urlFormatter(LocalDate date, String time) throws MalformedURLException {
		String url = WEATHER_URL + "?serviceKey=" + WEATHER_SERVICE_KEY 
				  + "&numOfRows=10"
				  + "&pageNo=1"
				  + "&base_date=" + date.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
				  + "&base_time=" + time
				  + "&nx=55&ny=127";
//		String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst?serviceKey=nkedH2GBDF%2BTCm2VLMxTXfjbK5uG7xtbtLDOXdsDlb%2F4S5NAykAK5f4zhhjMpTM7GUf1pmqRcrC7nTOPF4iAgw%3D%3D&numOfRows=10&pageNo=1&base_date=20220901&base_time=0600&nx=55&ny=127";
		System.out.println("url = " + url);
		return new URL(url);
	}
	
}



