package ktb.week4.community.domain.article.repository;

import ktb.week4.community.domain.article.entity.Article;
import ktb.week4.community.domain.user.entity.User;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository {
	
	private final ConcurrentHashMap<Long, Article> articles = new ConcurrentHashMap<>();
	
	@Override
	public Article save(Article article) {
		article.setId(articles.keySet().stream()
				.max(Long::compareTo)
				.orElse(0L) + 1);
		article.setCreatedAt(LocalDateTime.now());
		return articles.put(article.getId(), article);
	}
	
	@Override
	public Article update(User user, Article oldArticle, Article article) {
		if (!article.getTitle().isEmpty()) {
			oldArticle.setTitle(article.getTitle());
		}
		if (!article.getContent().isEmpty()) {
			oldArticle.setContent(article.getContent());
		}
		if (!article.getArticleImage().isEmpty()) {
			oldArticle.setArticleImage(article.getArticleImage());
		}
		
		oldArticle.setUpdatedAt(LocalDateTime.now());
		return oldArticle;
	}
	
	@Override
	public Optional<Article> findById(Long articleId) {
		Article article = articles.get(articleId);
		if (article == null || article.getDeletedAt() != null) {
			return Optional.empty();
		}
		return Optional.of(article);
	}
	
	@Override
	public List<Article> findAll(int page, int size) {
		return articles.values().stream()
				.filter(article -> article.getDeletedAt() == null)
				.sorted(Comparator.comparing(Article::getCreatedAt).reversed())
				.skip((long) (page - 1) * size)
				.limit(size)
				.collect(Collectors.toList());
	}
	
	@Override
	public void deleteByArticleId(Article article) {
		article.setDeletedAt(LocalDateTime.now());
	}
	
	@Override
	public long count() {
		return articles.values().stream().filter(article -> article.getDeletedAt() == null).count();
	}
	
	@Override
	public void increaseViewCount(Article article) {
		article.setViewCount(article.getViewCount() + 1);
	}
	
	@Override
	public void incrementCommentCount(Article article) {
		article.setCommentCount(article.getCommentCount() + 1);
	}
	
	@Override
	public void decrementCommentCount(Article article) {
		article.setCommentCount(article.getCommentCount() - 1);
	}
	
	@Override
	public void incrementLikeCount(Article article) {
		article.setLikeCount(article.getLikeCount() + 1);
	}
	
	@Override
	public void decrementLikeCount(Article article) {
		article.setLikeCount(article.getLikeCount() - 1);
	}
}
