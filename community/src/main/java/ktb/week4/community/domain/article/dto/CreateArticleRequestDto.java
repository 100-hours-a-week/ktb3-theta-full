package ktb.week4.community.domain.article.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record CreateArticleRequestDto(
		@NotBlank(message = "게시글 제목은 필수입니다.")
		String title,
		
		@NotBlank(message = "게시글 본문은 필수입니다.")
		String content,
		
		@JsonProperty("article_image")
		String articleImage
) {
}