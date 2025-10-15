package domain.user.service;

import domain.user.dto.*;
import domain.user.entity.User;
import domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {
	private final UserRepository userRepository;
	
	@Override
	public SignUpResponseDto createUser(SignUpRequestDto request) {
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
		User newUser = User.create(
				request.nickname(),
				null, null,
				request.profileImage()
		);

		return UserResponseDto.fromEntity(userRepository.update(userId, newUser));
	}
	
	@Override
	public void deleteUser(Long userId) {
		userRepository.deleteById(userId);
	}
	
	@Override
	public void updatePassword(Long userId, UpdatePasswordRequestDto request) {
		userRepository.updatePassword(userId, request.password());
	}
}
