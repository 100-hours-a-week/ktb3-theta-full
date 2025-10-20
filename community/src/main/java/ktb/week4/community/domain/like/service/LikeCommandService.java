package ktb.week4.community.domain.like.service;

import ktb.week4.community.domain.article.entity.Article;
import ktb.week4.community.domain.article.repository.ArticleRepository;
import ktb.week4.community.domain.article.service.ArticleCommandService;
import ktb.week4.community.domain.like.dto.LikeResponseDto;
import ktb.week4.community.domain.like.entity.LikeArticle;
import ktb.week4.community.domain.like.repository.LikeRepository;
import ktb.week4.community.global.apiPayload.ErrorCode;
import ktb.week4.community.global.exception.GeneralException;
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
                .orElseThrow(() -> new GeneralException(ErrorCode.ARTICLE_NOT_FOUND));
		
        if (!likeRepository.existsByUserIdAndArticleId(userId, articleId)) {
            likeRepository.save(new LikeArticle(null, articleId, userId));
            articleCommandService.incrementLikeCount(articleId);
        }

        return new LikeResponseDto(articleId, updatedArticle.getLikeCount(), true);
    }

    public void unlikeArticle(Long articleId, Long userId) {
        articleRepository.findById(articleId)
                .orElseThrow(() -> new GeneralException(ErrorCode.ARTICLE_NOT_FOUND));

        likeRepository.findByUserIdAndArticleId(userId, articleId).ifPresent(likeArticle -> {
            likeRepository.delete(likeArticle);
            articleCommandService.decrementLikeCount(articleId);
        });
    }
}
