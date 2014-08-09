package com.pe.pgn.clubpgn.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UploadFileUtil {

	public static String uploadFile(HttpServletRequest request, String uploadDir) throws IOException
	{
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
			
		File dirPath = new File(uploadDir);
		if(!dirPath.exists())
		{
			dirPath.mkdirs();
		}
		
		InputStream stream = file.getInputStream();
		OutputStream bos = new FileOutputStream(uploadDir + file.getOriginalFilename());
		int bytesRead = 0;
		byte buffer[] = new byte[8192];
		while((bytesRead = stream.read(buffer, 0, 8192)) != -1) 
		{
			bos.write(buffer, 0, bytesRead);
		}
		bos.close();			
		stream.close();
		
		String path = uploadDir + file.getOriginalFilename();
		return path;
	}
}
