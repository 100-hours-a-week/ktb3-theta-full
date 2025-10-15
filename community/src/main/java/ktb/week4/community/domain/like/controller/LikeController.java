package ktb.week4.community.domain.like.controller;

import ktb.week4.community.domain.like.dto.LikeResponseDto;
import ktb.week4.community.domain.like.service.LikeCommandService;
import ktb.week4.community.domain.like.service.LikeQueryService;
import ktb.week4.community.global.apiPayload.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles/{articleId}/likes")
@AllArgsConstructor
public class LikeController {

    private final LikeQueryService likeQueryService;
    private final LikeCommandService likeCommandService;

    @GetMapping
    public ApiResponse<LikeResponseDto> getLikes(
            @PathVariable Long articleId,
            @RequestParam Long userId) {
        return ApiResponse.onSuccess("article_like_success", likeQueryService.getLikeStatus(articleId, userId));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<LikeResponseDto>> createLike(
            @PathVariable Long articleId,
            @RequestParam Long userId) {
        ApiResponse<LikeResponseDto> response = ApiResponse.onSuccess("article_like_success", likeCommandService.likeArticle(articleId, userId));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteLike(
            @PathVariable Long articleId,
            @RequestParam Long userId) {
        likeCommandService.unlikeArticle(articleId, userId);
		return ApiResponse.onDeleteSuccess();
    }
}
