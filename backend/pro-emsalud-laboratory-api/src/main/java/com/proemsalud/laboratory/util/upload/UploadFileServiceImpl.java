package com.proemsalud.laboratory.util.upload;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServiceImpl implements IUploadFileService {

	private static final Logger log = LoggerFactory.getLogger(UploadFileServiceImpl.class);
	
	private final static String DIRECTORY_UPLOAD = "uploads";
	
	@Override
	public Resource load(String fileName) throws MalformedURLException {
		
		Path fileRoute = this.getPath(fileName);
		Resource resource = new UrlResource(fileRoute.toUri());
		
		
		if(!resource.exists() && !resource.isReadable()) {
			fileRoute = Paths.get("src/main/resources/static/images").resolve("no-user.png").toAbsolutePath();
			
			resource = new UrlResource(fileRoute.toUri());
			
			log.error("Error, can not load the image " + fileName);

		}
		return resource;
	}

	@Override
	public String copy(MultipartFile file) throws IOException {
		
		String fileName = UUID.randomUUID().toString()+"_"+file.getOriginalFilename()
		.replace(" ", "");
		
		Path fileRoute = this.getPath(fileName);
		
		
		Files.copy(file.getInputStream(), fileRoute);
	
		return fileName;
	}

	@Override
	public boolean delete(String fileName) {
		
		if(fileName != null && fileName.length() > 0) {
			Path previousRoutePicture = Paths.get("uploads").resolve(fileName).toAbsolutePath();
			File previousFilePicture = previousRoutePicture.toFile();
			
			if(previousFilePicture.exists() && previousFilePicture.canRead()) {
				
				previousFilePicture.delete();
				return true;
			}
		}
		return false;
	}

	@Override
	public Path getPath(String fileName) {
		// TODO Auto-generated method stub
		return Paths.get(DIRECTORY_UPLOAD).resolve(fileName).toAbsolutePath();
	}

}
