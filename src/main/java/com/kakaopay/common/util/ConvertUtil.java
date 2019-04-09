package com.kakaopay.common.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;

@Service
public class ConvertUtil {
	
	public List<String[]> convertCSV(String fileName, String charset) {
		
		// 리소스 파일 가져오기
		ClassPathResource resource = new ClassPathResource("docs/csv/"+fileName);
		
		List<String[]> list = null;
		
		try {
			InputStreamReader isr = new InputStreamReader(resource.getInputStream(), charset);
			CSVReader reader = new CSVReader(isr);
			
			// 1행 패스
			reader.readNext(); 
			
			// 파일 읽기
			list = reader.readAll();
				
			reader.close();
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
