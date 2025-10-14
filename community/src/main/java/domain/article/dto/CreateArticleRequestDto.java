package domain.article.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record CreateArticleRequestDto(
		@NotBlank
		String title,
		
		@NotBlank
		String content,
		
		@JsonProperty("article_image")
		String articleImage
) {
}