package ktb.week4.community.domain.comment.service;

import ktb.week4.community.domain.article.repository.ArticleRepository;
import ktb.week4.community.domain.comment.dto.CommentResponseDto;
import ktb.week4.community.domain.comment.dto.GetCommentsResponseDto;
import ktb.week4.community.domain.comment.entity.Comment;
import ktb.week4.community.domain.comment.repository.CommentRepository;
import ktb.week4.community.domain.user.entity.User;
import ktb.week4.community.domain.user.repository.UserRepository;
import ktb.week4.community.global.apiPayload.ErrorCode;
import ktb.week4.community.global.exception.GeneralException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentQueryService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public GetCommentsResponseDto getComments(Long articleId, int page, int size) {
        articleRepository.findById(articleId)
                .orElseThrow(() -> new GeneralException(ErrorCode.ARTICLE_NOT_FOUND));

        List<Comment> comments = commentRepository.findAllByArticleId(articleId, page, size);
        long totalCount = commentRepository.countByArticleId(articleId);

        List<Long> userIds = comments.stream()
                .map(Comment::getUserId)
                .distinct()
                .collect(Collectors.toList());

        Map<Long, User> userMap = userRepository.findByIds(userIds).stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        List<CommentResponseDto> responses = comments.stream()
                .map(comment -> {
                    User user = userMap.get(comment.getUserId());
                    return CommentResponseDto.fromEntity(comment, user);
                })
                .collect(Collectors.toList());

        int totalPages = (int) Math.ceil((double) totalCount / size);
        boolean isLast = page >= totalPages;

        return new GetCommentsResponseDto(responses, page, totalCount, totalPages, isLast);
    }
}