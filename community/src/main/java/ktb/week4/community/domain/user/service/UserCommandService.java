package ktb.week4.community.domain.user.service;

import ktb.week4.community.domain.user.dto.*;

public interface UserCommandService {
	SignUpResponseDto createUser(SignUpRequestDto request);
	UserResponseDto updateUser(Long userId, UpdateUserRequestDto request);
	void deleteUser(Long userId);
	void updatePassword(Long userId, UpdatePasswordRequestDto request);
}
