package team.mb.mbserver.domain.coupon.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team.mb.mbserver.domain.coupon.model.CouponResponse;
import team.mb.mbserver.domain.coupon.model.CreateCouponRequest;
import team.mb.mbserver.domain.coupon.service.CouponService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Tag(name = "쿠폰", description = "쿠폰 관련 API 입니다.")
@RequiredArgsConstructor
@RequestMapping("/coupons")
@RestController
public class CouponController {

    private final CouponService couponService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "쿠폰 저장")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "쿠폰 저장 성공",
                    content = @Content(schema = @Schema(implementation = CreateCouponRequest.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    public void save(@RequestBody @Valid CreateCouponRequest request) {
        couponService.saveCoupon(request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{coupon-id}")
    @Operation(summary = "쿠폰 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "쿠폰 삭제 성공",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    public void delete(
            @Parameter(
                    name = "coupon-id",
                    description = "쿠폰 식별키 입니다."
            )
            @PathVariable("coupon-id") Long couponId
    ) {
        couponService.deleteCoupon(couponId);
    }

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "쿠폰 리스트 가져오기 성공",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    public List<CouponResponse> getCoupons() {
        return couponService.getCoupons();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping
    @Operation(summary = "쿠폰 선물하기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "쿠폰 선물하기 성공",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @Parameters(value = {
            @Parameter(
                    name = "coupon-id",
                    description = "쿠폰 식별키 입니다."
            ),
            @Parameter(
                    name = "account-id",
                    description = "선물 받을 유저 아이디 입니다."
            )
    })
    public void giveCoupon(
            @RequestParam("coupon-id") @NotBlank Long couponId,
            @RequestParam("account-id") @NotBlank String accountId
    ) {
        couponService.giveCoupon(couponId, accountId);
    }
}
