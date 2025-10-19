package ktb.week4.community.domain.like.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ktb.week4.community.domain.like.dto.LikeResponseDto;
import ktb.week4.community.domain.like.service.LikeCommandService;
import ktb.week4.community.domain.like.service.LikeQueryService;
import ktb.week4.community.global.apiPayload.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles/{articleId}/likes")
@AllArgsConstructor
public class LikeController {

    private final LikeQueryService likeQueryService;
    private final LikeCommandService likeCommandService;
	
	@Tag(name = "Like", description = "좋아요 관련 API")
	@Operation(summary = "현재 로그인 한 사용자의 좋아요 여부를 조회합니다.")
    @GetMapping
    public ApiResponse<LikeResponseDto> getLikes(
			@Parameter(description = "좋아요 여부를 확인할 게시글 id", required = true, example = "1")
            @PathVariable Long articleId,
			@Parameter(description = "좋아요 여부를 확인할 사용자의 id", required = true, example = "1")
            @RequestParam Long userId) {
        return ApiResponse.onSuccess("article_like_success", likeQueryService.getLikeStatus(articleId, userId));
    }
	
	@Tag(name = "Like", description = "좋아요 관련 API")
	@Operation(summary = "현재 게시글을 좋아요 합니다.")
	@PostMapping
    public ApiResponse<LikeResponseDto> createLike(
			@Parameter(description = "좋아요룰 할 게시글 id", required = true, example = "1")
			@PathVariable Long articleId,
			@Parameter(description = "좋아요를 할 사용자의 id", required = true, example = "1")
			@RequestParam Long userId) {
        return ApiResponse.onCreateSuccess("article_like_success", likeCommandService.likeArticle(articleId, userId));
    }
	
	@Tag(name = "Like", description = "좋아요 관련 API")
	@Operation(summary = "현재 게시글을 좋아요 취소합니다.")
    @DeleteMapping
	@ApiResponses({
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "좋아요 취소 성공"),
	})
    public ResponseEntity<Void> deleteLike(
			@Parameter(description = "좋아요룰 취소할 게시글 id", required = true, example = "1")
            @PathVariable Long articleId,
			@Parameter(description = "좋아요를 취소할 사용자의 id", required = true, example = "1")
			@RequestParam Long userId) {
        likeCommandService.unlikeArticle(articleId, userId);
		return ApiResponse.onDeleteSuccess();
    }
}
