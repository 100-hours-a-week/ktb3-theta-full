package ktb.week4.community.domain.like.service;

import ktb.week4.community.domain.article.entity.Article;
import ktb.week4.community.domain.article.repository.ArticleRepository;
import ktb.week4.community.domain.like.dto.LikeResponseDto;
import ktb.week4.community.domain.like.repository.LikeRepository;
import ktb.week4.community.global.apiPayload.ErrorCode;
import ktb.week4.community.global.exception.GeneralException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LikeQueryService {

    private final LikeRepository likeRepository;
    private final ArticleRepository articleRepository;

    public LikeResponseDto getLikeStatus(Long articleId, Long userId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new GeneralException(ErrorCode.ARTICLE_NOT_FOUND));

        boolean isLiked = likeRepository.existsByUserIdAndArticleId(userId, articleId);

        return new LikeResponseDto(articleId, article.getLikeCount(), isLiked);
    }
}
