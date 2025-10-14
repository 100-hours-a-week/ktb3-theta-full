package domain.article.controller;

import domain.article.dto.ArticleResponseDto;
import domain.article.dto.CreateArticleRequestDto;
import domain.article.dto.GetArticlesResponseDto;
import domain.article.dto.UpdateArticleRequestDto;
import domain.article.service.ArticleCommandService;
import domain.article.service.ArticleQueryService;
import global.apiPayload.ApiResponse;
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
	public ApiResponse<GetArticlesResponseDto> getArticles(@RequestParam int page, @RequestParam int size) {
		return ApiResponse.onSuccess("articles_success", articleQueryService.getArticles(page, size));
	}
	
	@PostMapping
	public ApiResponse<ArticleResponseDto> createArticle(@RequestParam Long userId, @RequestBody CreateArticleRequestDto request) {
		return ApiResponse.onSuccess("article_create_success", articleCommandService.createArticle(userId, request));
	}
	
	@PatchMapping
	public ApiResponse<ArticleResponseDto> updateArticle(@RequestParam Long userId, @RequestParam Long articleId, @RequestBody UpdateArticleRequestDto request) {
		return ApiResponse.onSuccess("article_update_success", articleCommandService.updateArticle(userId, articleId, request));
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deleteArticle(@RequestParam Long userId, @RequestParam Long articleId) {
		articleCommandService.deleteArticle(userId, articleId);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{articleId}")
	public ApiResponse<ArticleResponseDto> getArticle(@PathVariable Long articleId) {
		articleCommandService.increaseViewCount(articleId);
		return ApiResponse.onSuccess("article_success", articleQueryService.getArticle(articleId));
	}
}
