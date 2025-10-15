package domain.comment.service;

import domain.article.repository.ArticleRepository;
import domain.comment.dto.CommentResponseDto;
import domain.comment.dto.GetCommentsResponseDto;
import domain.comment.entity.Comment;
import domain.comment.repository.CommentRepository;
import domain.user.entity.User;
import domain.user.repository.UserRepository;
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
                .orElseThrow(() -> new IllegalArgumentException("article_not_found"));

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