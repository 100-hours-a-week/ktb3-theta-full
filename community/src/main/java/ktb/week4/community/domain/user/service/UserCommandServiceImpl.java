package ktb.week4.community.domain.user.service;

import ktb.week4.community.domain.user.dto.*;
import ktb.week4.community.domain.user.entity.User;
import ktb.week4.community.domain.user.repository.UserRepository;
import ktb.week4.community.global.apiPayload.ErrorCode;
import ktb.week4.community.global.exception.GeneralException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {
	private final UserRepository userRepository;
	
	@Override
	public SignUpResponseDto createUser(SignUpRequestDto request) {
		// 이미 존재하는 이메일일 경우
		if(userRepository.findByEmail(request.email()).isPresent()) {
			throw new GeneralException(ErrorCode.EMAIL_ALREADY_EXISTS);
		}
		
		User user = userRepository.save(User.create(
				request.nickname(),
				request.password(),
				request.email(),
				request.profileImage()
		));
		
		return new SignUpResponseDto(user.getId());
	}
	
	@Override
	public UserResponseDto updateUser(Long userId, UpdateUserRequestDto request) {
		User user = validateUserExists(userId);
		if(!request.nickname().isEmpty()) {
			user.setNickname(request.nickname());
		}
		if(!request.profileImage().isEmpty()) {
			user.setProfileImage(request.profileImage());
		}

		return UserResponseDto.fromEntity(userRepository.update(userId, user));
	}
	
	@Override
	public void deleteUser(Long userId) {
		userRepository.deleteById(validateUserExists(userId));
	}
	
	@Override
	public void updatePassword(Long userId, UpdatePasswordRequestDto request) {
		userRepository.updatePassword(validateUserExists(userId), request.password());
	}
	
	private User validateUserExists(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));
	}
}
