package ktb.week4.community.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ktb.week4.community.domain.user.entity.User;

public record WrittenByResponseDto (
		@JsonProperty("user_id")
		Long userId,
		
		String nickname,
		
		@JsonProperty("profile_image")
		String profileImage
) {
	
	public static WrittenByResponseDto fromEntity(User user) {
		return new WrittenByResponseDto(
				user.getId(),
				user.getNickname(),
				user.getProfileImage()
		);
	}
}
