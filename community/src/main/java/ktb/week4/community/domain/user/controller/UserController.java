package ktb.week4.community.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
	
	@Tag(name = "User", description = "유저 관련 API")
	@Operation(summary = "회원가입합니다.")
	@PostMapping("/users")
	public ApiResponse<SignUpResponseDto> registerUser(
			@RequestBody @Valid SignUpRequestDto request) {
		return ApiResponse.onCreateSuccess("register_success", userCommandService.createUser(request));
	}
	
	@Tag(name = "User", description = "유저 관련 API")
	@Operation(summary = "사용자의 정보를 조회합니다.")
	@GetMapping("/users")
	public ApiResponse<UserResponseDto> getUser(
			@Parameter(description = "정보를 조회할 사용자의 id", required = true, example = "1")
			@RequestParam Long userId) {
		return ApiResponse.onSuccess("user_info_success", userQueryService.getUser(userId));
	}
	
	@Tag(name = "User", description = "유저 관련 API")
	@Operation(summary = "사용자의 정보를 수정합니다.")
	@PatchMapping("/users")
	public ApiResponse<UserResponseDto> updateUser(
			@Parameter(description = "정보를 수정할 사용자의 id", required = true, example = "1")
			@RequestParam Long userId,
			@RequestBody UpdateUserRequestDto request) {
		return ApiResponse.onSuccess("user_info_update_success", userCommandService.updateUser(userId, request));
	}
	
	@Tag(name = "User", description = "유저 관련 API")
	@Operation(summary = "사용자의 비밀번호를 변경합니다.")
	@PatchMapping("/users/password")
	public ApiResponse<Void> updatePassword(
			@Parameter(description = "비밀번호를 변경할 사용자의 id", required = true, example = "1")
			@RequestParam Long userId,
			@RequestBody @Valid UpdatePasswordRequestDto request) {
		userCommandService.updatePassword(userId, request);
		return ApiResponse.onSuccess("password_update_success", null);
	}
	
	@Tag(name = "User", description = "유저 관련 API")
	@Operation(summary = "사용자를 탈퇴 처리합니다.")
	@ApiResponses({
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "탈퇴 성공"),
	})
	@DeleteMapping("/users")
	public ResponseEntity<Void> deleteUser(
			@Parameter(description = "탈퇴 처리를 진행할 사용자의 id", required = true, example = "1")
			@RequestParam Long userId) {
		userCommandService.deleteUser(userId);
		return ApiResponse.onDeleteSuccess();
	}
	
	@Tag(name = "User", description = "유저 관련 API")
	@Operation(summary = "로그인합니다.")
	@PostMapping("/auth/login")
	public ApiResponse<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
		return ApiResponse.onSuccess("login_success", userQueryService.login(loginRequestDto));
	}
	
	@Tag(name = "User", description = "유저 관련 API")
	@Operation(summary = "로그아웃합니다.")
	@PostMapping("/auth/logout")
	@ApiResponses({
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "로그아웃 성공"),
	})
	public ResponseEntity<Void> logout(
			@Parameter(description = "로그아웃 할 사용자의 id", required = true, example = "1")
			@RequestParam Long userId) {
		userQueryService.logout(userId);
		return ApiResponse.onDeleteSuccess();
	}
}
