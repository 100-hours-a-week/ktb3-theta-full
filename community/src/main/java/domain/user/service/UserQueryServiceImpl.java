package domain.user.service;

import domain.user.dto.LoginRequestDto;
import domain.user.dto.LoginResponseDto;
import domain.user.dto.UserResponseDto;
import domain.user.entity.User;
import domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserQueryServiceImpl  implements  UserQueryService {
	private final UserRepository userRepository;
	
	@Override
	public UserResponseDto getUser(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
		return UserResponseDto.fromEntity(user);
	}
	
	@Override
	public LoginResponseDto login(LoginRequestDto request) {
		User user = userRepository.findByEmail(request.email()).orElseThrow(() -> new IllegalArgumentException("User not found"));
		
		if(!request.password().equals(user.getPassword())) {
			throw new IllegalArgumentException("Invalid password");
		}
		
		// 토큰 발급 등 로그인 처리 로직 진행
		return new LoginResponseDto(user.getId(), user.getProfileImage());
	}
	
	@Override
	public void logout(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
		// 토큰 블랙리스트 처리, 제거 등 로그아웃 처리 로직 진행
	}
}
