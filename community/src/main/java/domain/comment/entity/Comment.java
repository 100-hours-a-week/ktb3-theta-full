package domain.comment.entity;

import global.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Comment extends BaseEntity {
	
	private Long commentId;
	private String content;
	private Long userId;
	private Long articleId;

	public static Comment create(String content, Long userId, Long articleId) {
		return new Comment(null, content, userId, articleId);
	}
}
