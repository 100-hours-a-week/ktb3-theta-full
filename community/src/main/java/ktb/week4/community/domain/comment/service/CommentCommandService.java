package ktb.week4.community.domain.comment.service;

import ktb.week4.community.domain.article.entity.Article;
import ktb.week4.community.domain.article.repository.ArticleRepository;
import ktb.week4.community.domain.article.service.ArticleCommandService;
import ktb.week4.community.domain.comment.dto.CommentResponseDto;
import ktb.week4.community.domain.comment.dto.CreateCommentRequestDto;
import ktb.week4.community.domain.comment.dto.UpdateCommentRequestDto;
import ktb.week4.community.domain.comment.entity.Comment;
import ktb.week4.community.domain.comment.repository.CommentRepository;
import ktb.week4.community.domain.user.entity.User;
import ktb.week4.community.domain.user.repository.UserRepository;
import ktb.week4.community.global.apiPayload.ErrorCode;
import ktb.week4.community.global.exception.GeneralException;
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
                .orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new GeneralException(ErrorCode.ARTICLE_NOT_FOUND));
		
        Comment comment = Comment.create(request.content(), userId, articleId);
        Comment savedComment = commentRepository.save(comment);

		articleRepository.incrementCommentCount(article);
        articleCommandService.incrementCommentCount(articleId);

        return CommentResponseDto.fromEntity(savedComment, user);
    }

    public CommentResponseDto updateComment(Long articleId, Long userId, Long commentId, UpdateCommentRequestDto request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new GeneralException(ErrorCode.COMMENT_NOT_FOUND));

        if (!comment.getUserId().equals(userId)) {
            throw new GeneralException(ErrorCode.FORBIDDEN_REQUEST);
        }
		if(!comment.getArticleId().equals(articleId)) {
			throw new GeneralException(ErrorCode.COMMENT_NOT_BELONG_TO_ARTICLE);
		}

        comment.setContent(request.content());
        Comment updatedComment = commentRepository.update(comment);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));

        return CommentResponseDto.fromEntity(updatedComment, user);
    }

    public void deleteComment(Long articleId, Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new GeneralException(ErrorCode.COMMENT_NOT_FOUND));

        if (!comment.getUserId().equals(userId)) {
            throw new GeneralException(ErrorCode.FORBIDDEN_REQUEST);
        }
		if(!comment.getArticleId().equals(articleId)) {
			throw new GeneralException(ErrorCode.COMMENT_NOT_BELONG_TO_ARTICLE);
		}

        commentRepository.delete(commentId);

        articleCommandService.decrementCommentCount(articleId);
    }
}
