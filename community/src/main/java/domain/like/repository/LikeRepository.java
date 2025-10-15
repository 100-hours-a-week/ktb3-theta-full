package domain.like.repository;

import domain.like.entity.LikeArticle;

import java.util.Optional;

public interface LikeRepository {
    LikeArticle save(LikeArticle likeArticle);

    void delete(LikeArticle likeArticle);

    Optional<LikeArticle> findByUserIdAndArticleId(Long userId, Long articleId);

    boolean existsByUserIdAndArticleId(Long userId, Long articleId);
}
