package com.triportreat.backend.file.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectMetadataRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.triportreat.backend.common.utils.UuidUtil;
import com.triportreat.backend.file.error.exception.ImageUploadFailedException;
import com.triportreat.backend.file.error.exception.NotImageFileException;
import com.triportreat.backend.file.service.FileService;
import java.io.IOException;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private static final String[] SUPPORTED_FILE_EXTENSIONS = {"image/jpeg", "image/png"};

    private final AmazonS3 amazonS3Client;

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Override
    public String deleteAndUploadFile(MultipartFile file, String existingFileUrl) {
        validateContentType(file.getContentType());
        deleteUploadedImage(existingFileUrl);
        return uploadImage(file);
    }

    @Override
    public String uploadImage(MultipartFile file) {
        ObjectMetadata objectMetadata = createObjectMetadata(file);
        String objectKey = createObjectKey(UuidUtil.createUuidUpperCase(), file.getOriginalFilename());
        uploadImageToS3(file, objectKey, objectMetadata);

        return amazonS3Client.getUrl(bucket, objectKey).toString();
    }

    @Override
    public void deleteUploadedImage(String url) {
        if (isUploadedImageExists(url)) {
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, extractObjectKey(url)));
        }
    }

    @Override
    public void validateContentType(String contentType) {
        if (!StringUtils.hasText(contentType) || Arrays.stream(SUPPORTED_FILE_EXTENSIONS).noneMatch(contentType::equals)) {
            throw new NotImageFileException();
        }
    }

    private ObjectMetadata createObjectMetadata(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());
        return objectMetadata;
    }

    private void uploadImageToS3(MultipartFile file, String objectKey, ObjectMetadata objectMetadata) {
        PutObjectRequest putObjectRequest = null;
        try {
            putObjectRequest = new PutObjectRequest(
                    bucket,
                    objectKey,
                    file.getInputStream(),
                    objectMetadata
            );
            amazonS3Client.putObject(putObjectRequest);
        } catch (IOException e) {
            throw new ImageUploadFailedException();
        }
    }

    private Boolean isUploadedImageExists(String url) {
        try {
            String objectKey = extractObjectKey(url);
            amazonS3Client.getObjectMetadata(new GetObjectMetadataRequest(bucket, objectKey));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String extractObjectKey(String url) {
        String[] parts = url.split("/");
        if (parts.length > 0) {
            return parts[parts.length - 1];
        }
        return "";
    }

    private String createObjectKey(String randomFilename, String originalFilename) {
        return randomFilename + "_" + originalFilename;
    }
}
