package ktb.week4.community.domain.user.controller;

import jakarta.validation.Valid;
import ktb.week4.community.domain.user.dto.*;
import ktb.week4.community.domain.user.service.UserCommandService;
import ktb.week4.community.domain.user.service.UserQueryService;
import ktb.week4.community.global.apiPayload.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UserController {
	
	private final UserCommandService userCommandService;
	private final UserQueryService userQueryService;
	
	@PostMapping("/users")
	public ApiResponse<SignUpResponseDto> registerUser(@RequestBody @Valid SignUpRequestDto request) {
		return ApiResponse.onCreateSuccess("register_success", userCommandService.createUser(request));
	}
	
	@GetMapping("/users")
	public ApiResponse<UserResponseDto> getUser(@RequestParam Long userId) {
		return ApiResponse.onSuccess("user_info_success", userQueryService.getUser(userId));
	}
	
	@PatchMapping("/users")
	public ApiResponse<UserResponseDto> updateUser(@RequestParam Long userId,
												   @RequestBody UpdateUserRequestDto request) {
		return ApiResponse.onSuccess("user_info_update_success", userCommandService.updateUser(userId, request));
	}
	
	@PatchMapping("/users/password")
	public ApiResponse<Void> updatePassword(@RequestParam Long userId,
											  @RequestBody @Valid UpdatePasswordRequestDto request) {
		userCommandService.updatePassword(userId, request);
		return ApiResponse.onSuccess("password_update_success", null);
	}
	
	@DeleteMapping("/users")
	public ResponseEntity<Void> deleteUser(@RequestParam Long userId) {
		userCommandService.deleteUser(userId);
		return ApiResponse.onDeleteSuccess();
	}
	
	@PostMapping("/auth/login")
	public ApiResponse<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
		return ApiResponse.onSuccess("login_success", userQueryService.login(loginRequestDto));
	}
	
	@PostMapping("/auth/logout")
	public ResponseEntity<Void> logout(@RequestParam Long userId) {
		userQueryService.logout(userId);
		return ApiResponse.onDeleteSuccess();
	}
}
