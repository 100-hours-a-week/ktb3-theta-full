package ktb.week4.community.domain.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdatePasswordRequestDto(
		@NotBlank(message = "새 비밀번호는 필수입니다.")
		String password
) {
}
