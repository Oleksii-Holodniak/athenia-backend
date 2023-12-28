package com.athenia.athenia.service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Vitalii Vasylykha
 * @company UzhNU
 * @since 2023/12/25
 */
@Service
public class FileService {
	private AmazonS3 s3client;
	@Value("${amazonProperties.endpointUrl}")
	private String endpointUrl;
	@Value("${amazonProperties.bucketName}")
	private String bucketName;
	@Value("${amazonProperties.accessKey}")
	private String accessKey;
	@Value("${amazonProperties.secretKey}")
	private String secretKey;

	@PostConstruct
	private void initializeAmazon() {
		this.s3client = AmazonS3ClientBuilder.standard()
				.withRegion(Regions.EU_CENTRAL_1)
				.build();
	}

	public String uploadFile(MultipartFile multipartFile) throws IOException {
		String fileUrl = "";
		File file = convertMultiPartToFile(multipartFile);
		String fileName = generateFileName(multipartFile);
		fileUrl = endpointUrl + "/" + fileName;
		uploadFileTos3bucket(fileName, file);
		file.delete();
		return fileUrl;
	}

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	private String generateFileName(MultipartFile multiPart) {
		return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
	}

	private void uploadFileTos3bucket(String fileName, File file) {
		s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
				.withCannedAcl(CannedAccessControlList.PublicRead));
	}
}