package ktb.week4.community.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignUpRequestDto(
		
		@NotNull(message = "이메일은 필수입니다.")
		@Email
		String email,
		
		@NotBlank(message = "비밀번호는 필수입니다.")
		String password,
		
		@NotBlank(message = "사용자 별명은 필수입니다.")
		String nickname,
		
		String profileImage
) {
}
