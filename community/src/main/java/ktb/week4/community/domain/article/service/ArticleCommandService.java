package ktb.week4.community.domain.article.service;

import ktb.week4.community.domain.article.dto.ArticleResponseDto;
import ktb.week4.community.domain.article.dto.CreateArticleRequestDto;
import ktb.week4.community.domain.article.dto.UpdateArticleRequestDto;
import ktb.week4.community.domain.article.entity.Article;
import ktb.week4.community.domain.article.repository.ArticleRepository;
import ktb.week4.community.domain.user.entity.User;
import ktb.week4.community.domain.user.repository.UserRepository;
import ktb.week4.community.global.exception.ErrorCode;
import ktb.week4.community.global.exception.GeneralException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class ArticleCommandService {
	
	private final ArticleRepository articleRepository;
	private final UserRepository userRepository;
	
	public ArticleResponseDto createArticle(Long userId, CreateArticleRequestDto request) {
		User writtenBy = userRepository.findById(userId).orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));
		
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
		User writtenBy = userRepository.findById(userId).orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));
		Article article = articleRepository.findById(articleId).orElseThrow(() -> new GeneralException(ErrorCode.ARTICLE_NOT_FOUND));
		if(!Objects.equals(article.getUserId(), userId)) throw new GeneralException(ErrorCode.FORBIDDEN_REQUEST);
		
		Article updatedArticle = articleRepository.update(article, Article.create(
				request.title(),
				request.content(),
				request.articleImage(),
				userId
		));
		
		return ArticleResponseDto.fromEntity(updatedArticle, writtenBy);
	}
	
	public void deleteArticle(Long userId, Long articleId) {
		userRepository.findById(userId).orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));
		Article article = articleRepository.findById(articleId).orElseThrow(() -> new GeneralException(ErrorCode.ARTICLE_NOT_FOUND));
		if(!article.getUserId().equals(userId)) {
			throw new GeneralException(ErrorCode.FORBIDDEN_REQUEST);
		}
		
		articleRepository.deleteByArticleId(article);
	}

	public void increaseViewCount(Long articleId) {
		articleRepository.increaseViewCount(validateArticleExists(articleId));
	}

	public void incrementCommentCount(Long articleId) {
		articleRepository.incrementCommentCount(validateArticleExists(articleId));
	}

	public void decrementCommentCount(Long articleId) {
		articleRepository.decrementCommentCount(validateArticleExists(articleId));
	}

	public void incrementLikeCount(Long articleId) {
		articleRepository.incrementLikeCount(validateArticleExists(articleId));
	}

	public void decrementLikeCount(Long articleId) {
		articleRepository.decrementLikeCount(validateArticleExists(articleId));
	}
	
	private Article validateArticleExists(Long articleId) {
		return articleRepository.findById(articleId).orElseThrow(() -> new GeneralException(ErrorCode.ARTICLE_NOT_FOUND));
	}
}
