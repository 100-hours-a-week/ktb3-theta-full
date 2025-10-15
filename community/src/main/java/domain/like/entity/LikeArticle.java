package domain.like.entity;

import global.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LikeArticle extends BaseEntity {
	private Long id;
	private Long articleId;
	private Long userId;
}
