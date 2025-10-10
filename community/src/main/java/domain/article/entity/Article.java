package domain.article.entity;

import global.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Article extends BaseEntity {
	
	private Long id;
	private String title;
	private String content;
	private String article_image;
	private Long userId;
}
