package domain.like.service;

import domain.article.entity.Article;
import domain.article.repository.ArticleRepository;
import domain.like.dto.LikeResponseDto;
import domain.like.repository.LikeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LikeQueryService {

    private final LikeRepository likeRepository;
    private final ArticleRepository articleRepository;

    public LikeResponseDto getLikeStatus(Long articleId, Long userId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("article_not_found"));

        boolean isLiked = likeRepository.existsByUserIdAndArticleId(userId, articleId);

        return new LikeResponseDto(articleId, article.getLikeCount(), isLiked);
    }
}
