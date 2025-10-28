package ktb.week4.community.domain.comment.service;

import ktb.week4.community.domain.article.loader.ArticleLoader;
import ktb.week4.community.domain.comment.dto.CommentResponseDto;
import ktb.week4.community.domain.comment.dto.GetCommentsResponseDto;
import ktb.week4.community.domain.comment.entity.Comment;
import ktb.week4.community.domain.comment.repository.CommentRepository;
import ktb.week4.community.domain.user.entity.User;
import ktb.week4.community.domain.user.loader.UserLoader;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentQueryService {
	
	private final CommentRepository commentRepository;
	private final UserLoader userLoader;
	private final ArticleLoader articleLoader;
	
	public GetCommentsResponseDto getComments(Long articleId, int page, int size) {
		articleLoader.getArticleById(articleId);
		
		List<Comment> comments = commentRepository.findAllByArticleId(articleId, page, size);
		
		List<Long> userIds = comments.stream()
				.map(Comment::getUserId)
				.distinct()
				.toList();
		
		Map<Long, User> userMap = userLoader.getUsersByIds(userIds);
		
		List<CommentResponseDto> responses = comments.stream()
				.map(comment -> {
					User user = userMap.get(comment.getUserId());
					if (user == null) {
						return CommentResponseDto.fromEntity(comment);
					}
					return CommentResponseDto.fromEntity(comment, user);
				})
				.collect(Collectors.toList());
		
		long totalCount = commentRepository.countByArticleId(articleId);
		int totalPages = (int) Math.ceil((double) totalCount / size);
		boolean isLast = page >= totalPages;
		
		return new GetCommentsResponseDto(responses, page, totalCount, totalPages, isLast);
	}
}