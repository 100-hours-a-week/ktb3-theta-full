package ktb.week4.community.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateCommentRequestDto(
		@NotBlank(message = "댓글에 내용이 있어야 합니다.")
		String content
) {
}
