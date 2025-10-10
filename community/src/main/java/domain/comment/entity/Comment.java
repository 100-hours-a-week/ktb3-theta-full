package domain.comment.entity;

import global.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Comment extends BaseEntity {
	
	private Long commentId;
	private String content;
	private Long userId;
	private Long articleId;
}
