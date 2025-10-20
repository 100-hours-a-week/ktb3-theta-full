package ktb.week4.community.domain.user.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import ktb.week4.community.domain.user.dto.*;
import ktb.week4.community.domain.user.service.UserCommandService;
import ktb.week4.community.domain.user.service.UserQueryService;
import ktb.week4.community.global.apiPayload.ApiResponse;
import ktb.week4.community.global.apiPayload.SuccessCode;
import ktb.week4.community.global.apiSpecification.UserApiSpecification;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UserController implements UserApiSpecification {
	
	private final UserCommandService userCommandService;
	private final UserQueryService userQueryService;
	
	@Override
	@PostMapping("/users")
	public ApiResponse<SignUpResponseDto> registerUser(
			@RequestBody @Valid SignUpRequestDto request) {
		return ApiResponse.onCreateSuccess(SuccessCode.CREATE_SUCCESS, userCommandService.createUser(request));
	}
	
	@Override
	@GetMapping("/users")
	public ApiResponse<UserResponseDto> getUser(
			@Parameter(description = "정보를 조회할 사용자의 id", required = true, example = "1")
			@RequestParam Long userId) {
		return ApiResponse.onSuccess(SuccessCode.SUCCESS, userQueryService.getUser(userId));
	}
	
	@Override
	@PatchMapping("/users")
	public ApiResponse<UserResponseDto> updateUser(
			@Parameter(description = "정보를 수정할 사용자의 id", required = true, example = "1")
			@RequestParam Long userId,
			@RequestBody UpdateUserRequestDto request) {
		return ApiResponse.onSuccess(SuccessCode.UPDATE_SUCCESS, userCommandService.updateUser(userId, request));
	}
	
	@Override
	@PatchMapping("/users/password")
	public ApiResponse<Void> updatePassword(
			@Parameter(description = "비밀번호를 변경할 사용자의 id", required = true, example = "1")
			@RequestParam Long userId,
			@RequestBody @Valid UpdatePasswordRequestDto request) {
		userCommandService.updatePassword(userId, request);
		return ApiResponse.onSuccess(SuccessCode.UPDATE_SUCCESS, null);
	}
	
	@Override
	@DeleteMapping("/users")
	public ResponseEntity<Void> deleteUser(
			@Parameter(description = "탈퇴 처리를 진행할 사용자의 id", required = true, example = "1")
			@RequestParam Long userId) {
		userCommandService.deleteUser(userId);
		return ApiResponse.onDeleteSuccess();
	}
	
	@Override
	@PostMapping("/auth/login")
	public ApiResponse<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
		return ApiResponse.onSuccess(SuccessCode.SUCCESS, userQueryService.login(loginRequestDto));
	}
	
	@Override
	@PostMapping("/auth/logout")
	public ResponseEntity<Void> logout(
			@Parameter(description = "로그아웃 할 사용자의 id", required = true, example = "1")
			@RequestParam Long userId) {
		userQueryService.logout(userId);
		return ApiResponse.onDeleteSuccess();
	}
}
