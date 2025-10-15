package ktb.week4.community.domain.article.service;

import ktb.week4.community.domain.article.dto.ArticleResponseDto;
import ktb.week4.community.domain.article.dto.CreateArticleRequestDto;
import ktb.week4.community.domain.article.dto.UpdateArticleRequestDto;
import ktb.week4.community.domain.article.entity.Article;
import ktb.week4.community.domain.article.repository.ArticleRepository;
import ktb.week4.community.domain.user.entity.User;
import ktb.week4.community.domain.user.repository.UserRepository;
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

	public void incrementLikeCount(Long articleId) {
		articleRepository.incrementLikeCount(articleId);
	}

	public void decrementLikeCount(Long articleId) {
		articleRepository.decrementLikeCount(articleId);
	}
}
