package domain.comment.repository;

import domain.comment.entity.Comment;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class CommentRepositoryImpl implements CommentRepository {
	
	private final ConcurrentHashMap<Long, Comment> comments = new ConcurrentHashMap<>();
	
	@Override
	public Comment save(Comment comment) {
		comment.setCommentId(comments.keySet().stream()
				.max(Long::compareTo)
				.orElse(0L) + 1);
		comment.setCreatedAt(LocalDateTime.now());
		comments.put(comment.getCommentId(), comment);
		return comment;
	}
	
	@Override
	public Optional<Comment> findById(Long commentId) {
		return Optional.ofNullable(comments.get(commentId));
	}
	
	@Override
	public List<Comment> findAllByArticleId(Long articleId, int page, int size) {
		return comments.values().stream()
				.filter(comment -> comment.getArticleId().equals(articleId))
				.sorted(Comparator.comparing(Comment::getCreatedAt).reversed())
				.skip((long) (page - 1) * size)
				.limit(size)
				.collect(Collectors.toList());
	}
	
	@Override
	public long countByArticleId(Long articleId) {
		return comments.values().stream()
				.filter(comment -> comment.getArticleId().equals(articleId))
				.count();
	}
	
	@Override
	public Comment update(Comment comment) {
		comment.setUpdatedAt(LocalDateTime.now());
		comments.put(comment.getCommentId(), comment);
		return comment;
	}
	
	@Override
	public void delete(Long commentId) {
		comments.remove(commentId);
	}
}