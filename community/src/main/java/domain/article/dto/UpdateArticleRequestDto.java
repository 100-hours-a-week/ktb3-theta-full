package domain.article.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateArticleRequestDto(
		
		String title,
		String content,
		
		@JsonProperty("article_image")
		String articleImage
) {
}
