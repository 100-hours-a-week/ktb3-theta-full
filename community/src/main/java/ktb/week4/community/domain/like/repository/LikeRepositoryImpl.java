package ktb.week4.community.domain.like.repository;

import ktb.week4.community.domain.like.entity.LikeArticle;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class LikeRepositoryImpl implements LikeRepository {

    private final ConcurrentHashMap<Long, LikeArticle> likes = new ConcurrentHashMap<>();

    @Override
    public LikeArticle save(LikeArticle likeArticle) {
        likeArticle.setId(likes.keySet().stream()
                .max(Long::compareTo)
                .orElse(0L) + 1);
        likeArticle.setCreatedAt(LocalDateTime.now());
        likes.put(likeArticle.getId(), likeArticle);
        return likeArticle;
    }

    @Override
    public void delete(LikeArticle likeArticle) {
        findByUserIdAndArticleId(likeArticle.getUserId(), likeArticle.getArticleId())
                .ifPresent(foundLike -> likes.remove(foundLike.getId()));
    }

    @Override
    public Optional<LikeArticle> findByUserIdAndArticleId(Long userId, Long articleId) {
        return likes.values().stream()
                .filter(like -> like.getUserId().equals(userId) && like.getArticleId().equals(articleId))
                .findFirst();
    }

    @Override
    public boolean existsByUserIdAndArticleId(Long userId, Long articleId) {
        return likes.values().stream()
                .anyMatch(like -> like.getUserId().equals(userId) && like.getArticleId().equals(articleId));
    }
}
