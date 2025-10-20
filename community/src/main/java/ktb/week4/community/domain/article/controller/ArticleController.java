package ktb.week4.community.domain.article.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import ktb.week4.community.domain.article.dto.ArticleResponseDto;
import ktb.week4.community.domain.article.dto.CreateArticleRequestDto;
import ktb.week4.community.domain.article.dto.GetArticlesResponseDto;
import ktb.week4.community.domain.article.dto.UpdateArticleRequestDto;
import ktb.week4.community.domain.article.service.ArticleCommandService;
import ktb.week4.community.domain.article.service.ArticleQueryService;
import ktb.week4.community.global.apiPayload.ApiResponse;
import ktb.week4.community.global.apiPayload.SuccessCode;
import ktb.week4.community.global.apiSpecification.ArticleApiSpecification;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
@AllArgsConstructor
public class ArticleController implements ArticleApiSpecification {
	private final ArticleCommandService articleCommandService;
	private final ArticleQueryService articleQueryService;
	
	@Override
	@GetMapping
	public ApiResponse<GetArticlesResponseDto> getArticles(
			@Parameter(description = "조회할 게시글의 페이지", required = true, example = "1")
			@RequestParam(name = "page") @Min(value = 1, message = "Page parameter must be at least 1") int page,
			@Parameter(description = "조회할 게시글의 페이지 당 사이즈", required = true, example = "7")
			@RequestParam(name = "size", defaultValue = "7") @Min(value = 1, message = "Size parameter must be at least 1") int size) {
		return ApiResponse.onSuccess(SuccessCode.SUCCESS, articleQueryService.getArticles(page, size));
	}
	
	@Override
	@PostMapping
	public ApiResponse<ArticleResponseDto> createArticle(
			@Parameter(description = "게시글을 생성하는 유저의 id", required = true, example = "1")
			@RequestParam Long userId,
			@RequestBody @Valid CreateArticleRequestDto request) {
		return ApiResponse.onCreateSuccess(SuccessCode.CREATE_SUCCESS, articleCommandService.createArticle(userId, request));
	}
	
	@Override
	@PatchMapping("/{articleId}")
	public ApiResponse<ArticleResponseDto> updateArticle(
			@Parameter(description = "수정하고자 하는 게시글의 id", required = true, example = "1")
			@PathVariable Long articleId,
			@Parameter(description = "게시글을 수정하는 유저의 id", required = true, example = "1")
			@RequestParam Long userId,
			@RequestBody @Valid UpdateArticleRequestDto request) {
		return ApiResponse.onSuccess(SuccessCode.UPDATE_SUCCESS, articleCommandService.updateArticle(userId, articleId, request));
	}
	
	@Override
	@DeleteMapping("/{articleId}")
	public ResponseEntity<Void> deleteArticle(
			@Parameter(description = "삭제하고자 하는 게시글의 id", required = true, example = "1")
			@PathVariable Long articleId,
			@Parameter(description = "게시글을 삭제하는 유저의 id", required = true, example = "1")
			@RequestParam Long userId) {
		articleCommandService.deleteArticle(userId, articleId);
		return ApiResponse.onDeleteSuccess();
	}
	
	@Override
	@GetMapping("/{articleId}")
	public ApiResponse<ArticleResponseDto> getArticle(
			@Parameter(description = "구체적으로 조회하고자 하는 게시글의 id", required = true, example = "1")
			@PathVariable Long articleId
	) {
		articleCommandService.increaseViewCount(articleId);
		return ApiResponse.onSuccess(SuccessCode.SUCCESS, articleQueryService.getArticle(articleId));
	}
}
