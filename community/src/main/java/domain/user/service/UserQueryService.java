package domain.user.service;

import domain.user.dto.LoginRequestDto;
import domain.user.dto.LoginResponseDto;
import domain.user.dto.UserResponseDto;

public interface UserQueryService {
	UserResponseDto getUser(Long userId);
	LoginResponseDto login(LoginRequestDto request);
	void logout(Long userId);
}
