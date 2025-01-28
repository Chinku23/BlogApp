package com.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.services.FileService;
@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
	    // Original file name
	    String name = file.getOriginalFilename();

	    // Generate a random ID and append the file extension
	    String randomID = UUID.randomUUID().toString();
	    String fileExtension = name.substring(name.lastIndexOf("."));
	    String fileName = randomID.concat(fileExtension);

	    // Full file path
	    String filePath = path + File.separator + fileName;

	    // Create directory if not exists
	    File f = new File(path);
	    if (!f.exists()) {
	        f.mkdirs(); // Create directories
	    }

	    Files.copy(file.getInputStream(), Paths.get(filePath));
	    

	    // Return the file name
	    return fileName;
	}


	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullpath =  path + File.separator + fileName;
		InputStream is = new FileInputStream(fullpath);
		
		return is;
	}

}
