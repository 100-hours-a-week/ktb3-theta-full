package domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateUserRequestDto(
		String nickname,

		@JsonProperty("profile_image")
		String profileImage
) {
}
