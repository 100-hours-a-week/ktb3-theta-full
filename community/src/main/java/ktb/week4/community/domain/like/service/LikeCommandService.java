package ktb.week4.community.domain.like.service;

import ktb.week4.community.domain.article.entity.Article;
import ktb.week4.community.domain.article.repository.ArticleRepository;
import ktb.week4.community.domain.like.dto.LikeResponseDto;
import ktb.week4.community.domain.like.entity.LikeArticle;
import ktb.week4.community.domain.like.repository.LikeRepository;
import ktb.week4.community.domain.article.loader.ArticleLoader;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LikeCommandService {

    private final LikeRepository likeRepository;
	private final ArticleLoader articleLoader;
	private final ArticleRepository articleRepository;
	
	public LikeResponseDto likeArticle(Long articleId, Long userId) {
		Article article = articleLoader.getArticleById(articleId);
		
        if (!likeRepository.existsByUserIdAndArticleId(userId, articleId)) {
            likeRepository.save(new LikeArticle(null, articleId, userId));
			articleRepository.incrementLikeCount(article);
        }

        return new LikeResponseDto(articleId, article.getLikeCount(), true);
    }

    public void unlikeArticle(Long articleId, Long userId) {
		Article article = articleLoader.getArticleById(articleId);
		
        likeRepository.findByUserIdAndArticleId(userId, articleId).ifPresent(likeRepository::delete);
		articleRepository.decrementLikeCount(article);
    }
}
