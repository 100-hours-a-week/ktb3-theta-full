package domain.like.service;

import domain.article.entity.Article;
import domain.article.repository.ArticleRepository;
import domain.article.service.ArticleCommandService;
import domain.like.dto.LikeResponseDto;
import domain.like.entity.LikeArticle;
import domain.like.repository.LikeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LikeCommandService {

    private final LikeRepository likeRepository;
    private final ArticleRepository articleRepository;
    private final ArticleCommandService articleCommandService;

    public LikeResponseDto likeArticle(Long articleId, Long userId) {
		Article updatedArticle = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("article_not_found"));
		
        if (!likeRepository.existsByUserIdAndArticleId(userId, articleId)) {
            likeRepository.save(new LikeArticle(null, articleId, userId));
            articleCommandService.incrementLikeCount(articleId);
        }

        return new LikeResponseDto(articleId, updatedArticle.getLikeCount(), true);
    }

    public void unlikeArticle(Long articleId, Long userId) {
        articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("article_not_found"));

        likeRepository.findByUserIdAndArticleId(userId, articleId).ifPresent(likeArticle -> {
            likeRepository.delete(likeArticle);
            articleCommandService.decrementLikeCount(articleId);
        });
    }
}
