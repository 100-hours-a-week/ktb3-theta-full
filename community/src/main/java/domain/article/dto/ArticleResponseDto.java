package domain.article.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import domain.article.entity.Article;
import domain.user.dto.WrittenByResponseDto;
import domain.user.entity.User;

import java.time.LocalDateTime;

public record ArticleResponseDto(
		
		@JsonProperty("article_id")
		Long articleId,
		
		String title,
		int likeCount,
		int commentCount,
		int viewCount,
		LocalDateTime createdAt,
		WrittenByResponseDto writtenBy
) {
	
	public static ArticleResponseDto fromEntity(
			Article article, User user) {
		return new ArticleResponseDto(article.getId(), article.getTitle(), article.getLikeCount(), article.getCommentCount(), article.getViewCount(), article.getCreatedAt(),
				WrittenByResponseDto.fromEntity(user));
	}
}
