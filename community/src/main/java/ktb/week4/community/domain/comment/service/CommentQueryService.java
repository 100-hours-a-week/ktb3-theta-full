package ktb.week4.community.domain.comment.service;

import ktb.week4.community.domain.article.loader.ArticleLoader;
import ktb.week4.community.domain.comment.dto.CommentResponseDto;
import ktb.week4.community.domain.comment.dto.GetCommentsResponseDto;
import ktb.week4.community.domain.comment.entity.Comment;
import ktb.week4.community.domain.comment.repository.CommentRepository;
import ktb.week4.community.domain.user.entity.User;
import ktb.week4.community.domain.user.repository.UserRepository;
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
	private final UserRepository userRepository;
	private final ArticleLoader articleLoader;

    public GetCommentsResponseDto getComments(Long articleId, int page, int size) {
		articleLoader.getArticleById(articleId);
		
        List<Comment> comments = commentRepository.findAllByArticleId(articleId, page, size);
        long totalCount = commentRepository.countByArticleId(articleId);

        List<Long> userIds = comments.stream()
                .map(Comment::getUserId)
                .distinct()
                .toList();
		
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