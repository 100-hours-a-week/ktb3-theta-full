package ktb.week4.community.domain.user.service;

import ktb.week4.community.domain.user.dto.*;
import ktb.week4.community.domain.user.entity.User;
import ktb.week4.community.domain.user.repository.UserRepository;
import ktb.week4.community.domain.user.loader.UserLoader;
import ktb.week4.community.domain.user.validator.UserValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserCommandService {
	private final UserRepository userRepository;
	private final UserLoader userLoader;
	private final UserValidator userValidator;
	
	public SignUpResponseDto createUser(SignUpRequestDto request) {
		userValidator.validateEmailIsNotTaken(request.email());
		
		User user = userRepository.save(User.create(
				request.nickname(),
				request.password(),
				request.email(),
				request.profileImage()
		));
		
		return new SignUpResponseDto(user.getId());
	}
	
	public UserResponseDto updateUser(Long userId, UpdateUserRequestDto request) {
		User user = userLoader.getUserById(userId);
		if(!request.nickname().isEmpty()) {
			user.setNickname(request.nickname());
		}
		if(!request.profileImage().isEmpty()) {
			user.setProfileImage(request.profileImage());
		}

		return UserResponseDto.fromEntity(userRepository.update(userId, user));
	}
	
	public void deleteUser(Long userId) {
		userLoader.getUserById(userId);
		userRepository.deleteById(userId);
	}
	
	public void updatePassword(Long userId, UpdatePasswordRequestDto request) {
		User user = userLoader.getUserById(userId);
		userRepository.updatePassword(user, request.password());
	}
}
