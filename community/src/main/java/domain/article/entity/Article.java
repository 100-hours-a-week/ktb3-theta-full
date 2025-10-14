package domain.article.entity;

import global.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Article extends BaseEntity {
	
	private Long id;
	private String title;
	private String content;
	private String articleImage;
	private Long userId;
	private int likeCount;
	private int viewCount;
	private int commentCount;
	
	public static Article create(String title, String content, String articleImage, Long userId) {
		return new Article(
				null,
				title,
				content,
				articleImage,
				userId,
				0,
				0,
				0
		);
	}
}
