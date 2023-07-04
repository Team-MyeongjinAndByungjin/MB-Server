package team.mb.mbserver.domain.image.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import team.mb.mbserver.domain.image.model.ImageUrlResponse;
import team.mb.mbserver.domain.image.service.UploadImageService;

@Tag(name = "이미지", description = "이미지 업로드 API 입니다.")
@RequiredArgsConstructor
@RestController
public class ImageController {

    private final UploadImageService uploadImageService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            value = "/images",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "이미지 업로드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "이미지 업로드 성공",
                    content = @Content(schema = @Schema(implementation = MultipartFile.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    public ImageUrlResponse uploadImage(
            @Parameter(
                    name = "image",
                    description = "multipart/form-data 형식의 이미지 리스트를 input으로 받습니다. 이때 key 값은 image 입니다.",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
            )
            @RequestPart MultipartFile image
    ) {
        return uploadImageService.execute(image);
    }
}
