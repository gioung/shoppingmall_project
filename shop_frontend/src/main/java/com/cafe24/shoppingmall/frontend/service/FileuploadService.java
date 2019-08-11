package com.cafe24.shoppingmall.frontend.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileuploadService {
	private final static String SAVE_PATH = "/shop_uploads"; //physical URL
	private final static String URL = "/images"; //virtual URL
	
	public String restore(MultipartFile multipartFile) {
		String url="";
		try {
		if(multipartFile.isEmpty())
			return url;
		
		String originalFilename=multipartFile.getOriginalFilename();
		System.out.println("originalFilename = "+originalFilename);
		//확장자
		String extName = originalFilename.substring(originalFilename.lastIndexOf('.')+1);
		StringBuffer saveFileName = generateSaveFileName(extName); 
		File file = new File(SAVE_PATH);
		if(!file.exists()) {
			file.mkdir();
		}
		//long fileSize = multipartFile.getSize();
		
		/*System.out.println("##########" + originalFilename);
		System.out.println("##########" + extName);
		System.out.println("##########" + saveFileName);
		System.out.println("##########" + fileSize);*/
		
		byte[] fileData = multipartFile.getBytes();
		OutputStream outputStream = new FileOutputStream(file+"/"+JSONObject.escape(saveFileName.toString()));
		outputStream.write(fileData);
		outputStream.flush();
		outputStream.close();
		
		url = URL + "/" + saveFileName;
		
		} catch (IOException e) {
			throw new RuntimeException("Fileupload error : "+ e);
		}
		
		return url;
	}
	
	private StringBuffer generateSaveFileName(String extName) {
		StringBuffer filename = new StringBuffer("");
		Calendar calendar = Calendar.getInstance();
		filename.append(calendar.get(Calendar.YEAR));
		filename.append(calendar.get(Calendar.MONTH));
		filename.append(calendar.get(Calendar.DATE));
		filename.append(calendar.get(Calendar.HOUR));
		filename.append(calendar.get(Calendar.MINUTE));
		filename.append(calendar.get(Calendar.SECOND));
		filename.append(calendar.get(Calendar.MILLISECOND));
		filename.append(("."+extName));
		
		return filename;
	}
}
