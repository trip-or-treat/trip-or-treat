package com.triportreat.backend.file.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.triportreat.backend.common.utils.DecodeUtil;
import com.triportreat.backend.common.utils.UuidUtil;
import com.triportreat.backend.file.error.exception.ImageUploadFailedException;
import com.triportreat.backend.file.error.exception.NotImageFileException;
import com.triportreat.backend.file.service.FileService;
import java.io.IOException;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
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
        log.info("originalFilename: {}", file.getOriginalFilename());
        uploadImageToS3(file, objectKey, objectMetadata);
        log.info("사진을 업로드합니다!");

        return amazonS3Client.getUrl(bucket, objectKey).toString();
    }

    @Override
    public void deleteUploadedImage(String url) {
        if (isUploadedImageExists(url)) {
            log.info("기존 사진을 삭제합니다!");
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
            String objectKey = DecodeUtil.decodeUrl(extractObjectKey(url));
            amazonS3Client.getObject(bucket, objectKey);
            return true;
        } catch (Exception e) {
            log.info("사진이 존재하지 않습니다!");
            return false;
        }
    }

    private String extractObjectKey(String url) {
        String[] parts = url.split("/");
        if (parts.length > 0) {
            return DecodeUtil.decodeUrl(parts[parts.length - 1]);
        }
        return "";
    }

    private String createObjectKey(String randomFilename, String originalFilename) {
        return randomFilename + "_" + originalFilename;
    }
}
