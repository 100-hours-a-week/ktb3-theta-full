package domain.article.repository;

import domain.article.entity.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
	Article save(Article article);
	Article update(Long userId, Long articleId, Article article);
	Optional<Article> findById(Long articleId);
	List<Article> findAll(int page, int size);
	void deleteByArticleId(Long userId, Long articleId);
	long count();
	void increaseViewCount(Long articleId);
	void incrementCommentCount(Long articleId);
	void decrementCommentCount(Long articleId);
}
