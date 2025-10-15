package ktb.week4.community.domain.comment.service;

import ktb.week4.community.domain.article.repository.ArticleRepository;
import ktb.week4.community.domain.article.service.ArticleCommandService;
import ktb.week4.community.domain.comment.dto.CommentResponseDto;
import ktb.week4.community.domain.comment.dto.CreateCommentRequestDto;
import ktb.week4.community.domain.comment.dto.UpdateCommentRequestDto;
import ktb.week4.community.domain.comment.entity.Comment;
import ktb.week4.community.domain.comment.repository.CommentRepository;
import ktb.week4.community.domain.user.entity.User;
import ktb.week4.community.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentCommandService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final ArticleCommandService articleCommandService;

    public CommentResponseDto createComment(Long userId, Long articleId, CreateCommentRequestDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("Article not found"));

        Comment comment = Comment.create(request.content(), userId, articleId);
        Comment savedComment = commentRepository.save(comment);

        articleCommandService.incrementCommentCount(articleId);

        return CommentResponseDto.fromEntity(savedComment, user);
    }

    public CommentResponseDto updateComment(Long userId, Long commentId, UpdateCommentRequestDto request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("comment_not_found"));

        if (!comment.getUserId().equals(userId)) {
            throw new IllegalArgumentException("forbidden_request");
        }

        comment.setContent(request.content());
        Comment updatedComment = commentRepository.update(comment);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return CommentResponseDto.fromEntity(updatedComment, user);
    }

    public void deleteComment(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("comment_not_found"));

        if (!comment.getUserId().equals(userId)) {
            throw new IllegalArgumentException("forbidden_request");
        }

        Long articleId = comment.getArticleId();
        commentRepository.delete(commentId);

        articleCommandService.decrementCommentCount(articleId);
    }
}
