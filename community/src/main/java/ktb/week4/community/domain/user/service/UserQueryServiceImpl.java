package ktb.week4.community.domain.user.service;

import ktb.week4.community.domain.user.dto.LoginRequestDto;
import ktb.week4.community.domain.user.dto.LoginResponseDto;
import ktb.week4.community.domain.user.dto.UserResponseDto;
import ktb.week4.community.domain.user.entity.User;
import ktb.week4.community.domain.user.repository.UserRepository;
import ktb.week4.community.global.apiPayload.ErrorCode;
import ktb.week4.community.global.exception.GeneralException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserQueryServiceImpl  implements  UserQueryService {
	private final UserRepository userRepository;
	
	@Override
	public UserResponseDto getUser(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));
		return UserResponseDto.fromEntity(user);
	}
	
	@Override
	public LoginResponseDto login(LoginRequestDto request) {
		User user = userRepository.findByEmail(request.email()).orElseThrow(() -> new GeneralException(ErrorCode._BAD_REQUEST));
		if(!request.password().equals(user.getPassword())) {
			throw new GeneralException(ErrorCode._BAD_REQUEST);
		}
		
		// 토큰 발급 등 로그인 처리 로직 진행
		return new LoginResponseDto(user.getId(), user.getProfileImage());
	}
	
	@Override
	public void logout(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));
		// 토큰 블랙리스트 처리, 제거 등 로그아웃 처리 로직 진행
	}
}
