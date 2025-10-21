package ktb.week4.community.domain.comment.repository;

import ktb.week4.community.domain.comment.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Comment save(Comment comment);

    Optional<Comment> findById(Long commentId);

    List<Comment> findAllByArticleId(Long articleId, int page, int size);

    long countByArticleId(Long articleId);

    Comment update(Long commentId, Comment comment);

    void delete(Long commentId);
}