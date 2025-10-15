package domain.article.service;

import domain.article.dto.ArticleResponseDto;
import domain.article.dto.CreateArticleRequestDto;
import domain.article.dto.UpdateArticleRequestDto;
import domain.article.entity.Article;
import domain.article.repository.ArticleRepository;
import domain.user.entity.User;
import domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ArticleCommandService {
	
	private final ArticleRepository articleRepository;
	private final UserRepository userRepository;
	
	public ArticleResponseDto createArticle(Long userId, CreateArticleRequestDto request) {
		User writtenBy = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
		
		Article article = articleRepository.save(
				Article.create(
						request.title(),
						request.content(),
						request.articleImage(),
						userId
				)
		);
		
		return ArticleResponseDto.fromEntity(article, writtenBy);
	}
	
	public ArticleResponseDto updateArticle(Long userId, Long articleId, UpdateArticleRequestDto request) {
		User writtenBy = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
		
		Article updatedArticle = articleRepository.update(userId, articleId, Article.create(
				request.title(),
				request.content(),
				request.articleImage(),
				userId
		));
		
		return ArticleResponseDto.fromEntity(updatedArticle, writtenBy);
	}
	
	public void deleteArticle(Long userId, Long articleId) {
		userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
		
		articleRepository.deleteByArticleId(userId, articleId);
	}

	public void increaseViewCount(Long articleId) {
		articleRepository.increaseViewCount(articleId);
	}

	public void incrementCommentCount(Long articleId) {
		articleRepository.incrementCommentCount(articleId);
	}

	public void decrementCommentCount(Long articleId) {
		articleRepository.decrementCommentCount(articleId);
	}
}
