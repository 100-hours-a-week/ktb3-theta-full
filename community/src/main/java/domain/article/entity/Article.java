package domain.article.entity;

import global.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Article extends BaseEntity {
	
	private Long id;
	private String title;
	private String content;
	private String articleImage;
	
	private int likeCount;
	private int viewCount;
	private int commentCount;
	
	private LocalDateTime deletedAt;
	
	private Long userId;
	
	public static Article create(String title, String content, String articleImage, Long userId) {
		return new Article(
				null,
				title,
				content,
				articleImage,
				0,
				0,
				0,
				null,
				userId
		);
	}
}
