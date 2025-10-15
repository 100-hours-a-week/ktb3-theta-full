package ktb.week4.community.domain.article.controller;

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
	
	@GetMapping
	public ApiResponse<GetArticlesResponseDto> getArticles(
			@RequestParam(name = "page") @Min(value = 1, message = "Page parameter must be at least 1") int page,
			@RequestParam(name = "size", defaultValue = "7") @Min(value = 1, message = "Size parameter must be at least 1") int size) {
		return ApiResponse.onSuccess("articles_success", articleQueryService.getArticles(page, size));
	}
	
	@PostMapping
	public ApiResponse<ArticleResponseDto> createArticle(@RequestParam Long userId, @RequestBody @Valid CreateArticleRequestDto request) {
		return ApiResponse.onCreateSuccess("article_create_success", articleCommandService.createArticle(userId, request));
	}
	
	@PatchMapping
	public ApiResponse<ArticleResponseDto> updateArticle(@RequestParam Long userId, @RequestParam Long articleId, @RequestBody @Valid UpdateArticleRequestDto request) {
		return ApiResponse.onSuccess("article_update_success", articleCommandService.updateArticle(userId, articleId, request));
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deleteArticle(@RequestParam Long userId, @RequestParam Long articleId) {
		articleCommandService.deleteArticle(userId, articleId);
		return ApiResponse.onDeleteSuccess();
	}
	
	@GetMapping("/{articleId}")
	public ApiResponse<ArticleResponseDto> getArticle(@PathVariable Long articleId) {
		articleCommandService.increaseViewCount(articleId);
		return ApiResponse.onSuccess("article_success", articleQueryService.getArticle(articleId));
	}
}
