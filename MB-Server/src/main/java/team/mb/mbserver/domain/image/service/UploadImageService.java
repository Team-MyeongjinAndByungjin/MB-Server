package team.mb.mbserver.domain.image.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import team.mb.mbserver.domain.image.model.ImageUrlResponse;
import team.mb.mbserver.infrastructure.storage.S3Facade;

@RequiredArgsConstructor
@Service
public class UploadImageService {

    private final S3Facade s3Facade;

    public ImageUrlResponse execute(MultipartFile image) {

        return new ImageUrlResponse(s3Facade.uploadImage(image));
    }
}
