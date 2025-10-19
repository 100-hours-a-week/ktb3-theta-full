package ktb.week4.community.domain.article.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import ktb.week4.community.domain.article.dto.ArticleResponseDto;
import ktb.week4.community.domain.article.dto.CreateArticleRequestDto;
import ktb.week4.community.domain.article.dto.GetArticlesResponseDto;
import ktb.week4.community.domain.article.dto.UpdateArticleRequestDto;
import ktb.week4.community.domain.article.service.ArticleCommandService;
import ktb.week4.community.domain.article.service.ArticleQueryService;
import ktb.week4.community.global.apiPayload.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
@AllArgsConstructor
public class ArticleController {
	private final ArticleCommandService articleCommandService;
	private final ArticleQueryService articleQueryService;
	
	@Operation(summary = "글 작성 시간을 기준으로 하여, 최신 순으로  게시글들을 조회합니다.")
	@GetMapping
	public ApiResponse<GetArticlesResponseDto> getArticles(
			@Parameter(description = "조회할 게시글의 페이지", required = true, example = "1")
			@RequestParam(name = "page") @Min(value = 1, message = "Page parameter must be at least 1") int page,
			@Parameter(description = "조회할 게시글의 페이지 당 사이즈", required = true, example = "7")
			@RequestParam(name = "size", defaultValue = "7") @Min(value = 1, message = "Size parameter must be at least 1") int size) {
		return ApiResponse.onSuccess("articles_success", articleQueryService.getArticles(page, size));
	}
	
	@Operation(summary = "게시글을 생성합니다.")
	@PostMapping
	public ApiResponse<ArticleResponseDto> createArticle(
			@Parameter(description = "게시글을 생성하는 유저의 id", required = true, example = "1")
			@RequestParam Long userId,
			@RequestBody @Valid CreateArticleRequestDto request) {
		return ApiResponse.onCreateSuccess("article_create_success", articleCommandService.createArticle(userId, request));
	}
	
	@Operation(summary = "게시글을 수정합니다.")
	@PatchMapping("/{articleId}")
	public ApiResponse<ArticleResponseDto> updateArticle(
			@Parameter(description = "수정하고자 하는 게시글의 id", required = true, example = "1")
			@PathVariable Long articleId,
			@Parameter(description = "게시글을 수정하는 유저의 id", required = true, example = "1")
			@RequestParam Long userId,
			@RequestBody @Valid UpdateArticleRequestDto request) {
		return ApiResponse.onSuccess("article_update_success", articleCommandService.updateArticle(userId, articleId, request));
	}
	
	@Operation(summary = "게시글을 삭제합니다.")
	@DeleteMapping("/{articleId}")
	@ApiResponses({
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "게시글 삭제 성공"),
	})
	public ResponseEntity<Void> deleteArticle(
			@Parameter(description = "삭제하고자 하는 게시글의 id", required = true, example = "1")
			@PathVariable Long articleId,
			@Parameter(description = "게시글을 삭제하는 유저의 id", required = true, example = "1")
			@RequestParam Long userId) {
		articleCommandService.deleteArticle(userId, articleId);
		return ApiResponse.onDeleteSuccess();
	}
	
	@Operation(summary = "특정 게시글을 조회합니다.")
	@GetMapping("/{articleId}")
	public ApiResponse<ArticleResponseDto> getArticle(
			@Parameter(description = "구체적으로 조회하고자 하는 게시글의 id", required = true, example = "1")
			@PathVariable Long articleId
	) {
		articleCommandService.increaseViewCount(articleId);
		return ApiResponse.onSuccess("article_success", articleQueryService.getArticle(articleId));
	}
}
