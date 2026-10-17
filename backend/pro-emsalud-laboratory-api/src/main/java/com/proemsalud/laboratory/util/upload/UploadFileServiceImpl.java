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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServiceImpl implements IUploadFileService {

	private static final Logger log = LoggerFactory.getLogger(UploadFileServiceImpl.class);

	private final static String DIRECTORY_UPLOAD = "uploads";
	private final static String DEFAULT_IMAGE = "static/images/no-user.png";

	@Override
	public Resource load(String fileName) throws MalformedURLException {

		Path fileRoute = this.getPath(fileName);
		Resource resource = new UrlResource(fileRoute.toUri());

		if (!resource.exists() || !resource.isReadable()) {
			log.error("Error, can not load the image {}", fileName);
			return new ClassPathResource(DEFAULT_IMAGE);
		}
		return resource;
	}

	@Override
	public String copy(MultipartFile file) throws IOException {

		String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename().replace(" ", "");

		Path fileRoute = this.getPath(fileName);

		Files.createDirectories(fileRoute.getParent());
		Files.copy(file.getInputStream(), fileRoute);

		return fileName;
	}

	@Override
	public boolean delete(String fileName) {

		if (fileName != null && fileName.length() > 0) {
			File previousFilePicture = this.getPath(fileName).toFile();

			if (previousFilePicture.exists() && previousFilePicture.canRead()) {
				return previousFilePicture.delete();
			}
		}
		return false;
	}

	@Override
	public Path getPath(String fileName) {
		return Paths.get(DIRECTORY_UPLOAD).resolve(fileName).toAbsolutePath();
	}
}