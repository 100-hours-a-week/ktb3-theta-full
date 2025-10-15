package ktb.week4.community.domain.like.repository;

import ktb.week4.community.domain.like.entity.LikeArticle;

import java.util.Optional;

public interface LikeRepository {
    LikeArticle save(LikeArticle likeArticle);

    void delete(LikeArticle likeArticle);

    Optional<LikeArticle> findByUserIdAndArticleId(Long userId, Long articleId);

    boolean existsByUserIdAndArticleId(Long userId, Long articleId);
}
