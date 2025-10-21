package ktb.week4.community.domain.article.repository;

import ktb.week4.community.domain.article.entity.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
	Article save(Article article);
	Article update(Long articleId, Article article);
	Optional<Article> findById(Long articleId);
	List<Article> findAll(int page, int size);
	void deleteByArticleId(Long articleId);
	long count();
	void increaseViewCount(Article article);
	void incrementCommentCount(Article article);
	void decrementCommentCount(Article article);
	void incrementLikeCount(Article article);
	void decrementLikeCount(Article article);
}
