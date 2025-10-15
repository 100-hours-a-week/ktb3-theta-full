package ktb.week4.community.domain.user.service;

import ktb.week4.community.domain.user.dto.LoginRequestDto;
import ktb.week4.community.domain.user.dto.LoginResponseDto;
import ktb.week4.community.domain.user.dto.UserResponseDto;

public interface UserQueryService {
	UserResponseDto getUser(Long userId);
	LoginResponseDto login(LoginRequestDto request);
	void logout(Long userId);
}
