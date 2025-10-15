package domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import domain.user.entity.User;

public record UserResponseDto(
		@JsonProperty("user_id")
		Long userId,
		
		String email,
		String nickname,
		
		@JsonProperty("profile_image")
		String profileImage
) {
	public static UserResponseDto fromEntity(User user) {
		return new UserResponseDto(
				user.getId(),
				user.getEmail(),
				user.getNickname(),
				user.getProfileImage()
		);
	}
}
