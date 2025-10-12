package domain.user.controller;

import domain.user.dto.*;
import domain.user.service.UserCommandService;
import domain.user.service.UserQueryService;
import global.apiPayload.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class UserController {
	
	private final UserCommandService userCommandService;
	private final UserQueryService userQueryService;
	
	@PostMapping("/users")
	public ApiResponse<SignUpResponseDto> registerUser(@RequestBody SignUpRequestDto request) {
		return ApiResponse.onSuccess("register_success", userCommandService.createUser(request));
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
											  @RequestBody UpdatePasswordRequestDto request) {
		userCommandService.updatePassword(userId, request);
		return ApiResponse.onSuccess("password_update_success", null);
	}
	
	@DeleteMapping("/users")
	public ResponseEntity<Void> deleteUser(@RequestParam Long userId) {
		userCommandService.deleteUser(userId);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/auth/login")
	public ApiResponse<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
		return ApiResponse.onSuccess("login_success", userQueryService.login(loginRequestDto));
	}
	
	@PostMapping("/auth/logout")
	public ResponseEntity<Void> logout(@RequestParam Long userId) {
		userQueryService.logout(userId);
		return ResponseEntity.noContent().build();
	}
}
