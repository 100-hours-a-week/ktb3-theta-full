package ktb.week4.community.domain.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import ktb.week4.community.domain.comment.dto.CommentResponseDto;
import ktb.week4.community.domain.comment.dto.CreateCommentRequestDto;
import ktb.week4.community.domain.comment.dto.GetCommentsResponseDto;
import ktb.week4.community.domain.comment.dto.UpdateCommentRequestDto;
import ktb.week4.community.domain.comment.service.CommentCommandService;
import ktb.week4.community.domain.comment.service.CommentQueryService;
import ktb.week4.community.global.apiPayload.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles/{articleId}/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentCommandService commentCommandService;
    private final CommentQueryService commentQueryService;
	
	@Tag(name = "Comment", description = "댓글 관련 API")
	@Operation(summary = "특정 게시글의 댓글을 조회합니다.")
    @GetMapping
    public ApiResponse<GetCommentsResponseDto> getComments(
			@Parameter(description = "댓글을 조회할 게시글의 id", required = true, example = "1")
			@PathVariable Long articleId,
			@Parameter(description = "조회할 댓글의 페이지", required = true, example = "1")
            @RequestParam(name = "page")  @Min(value = 1, message = "Page parameter must be at least 1") int page,
			@Parameter(description = "조회할 댓글의 페이지 당 사이즈", required = true, example = "7")
            @RequestParam(name = "size", defaultValue = "7") @Min(value = 1, message = "Size parameter must be at least 1") int size) {
        return ApiResponse.onSuccess("comments_success", commentQueryService.getComments(articleId, page, size));
    }
	
	@Tag(name = "Comment", description = "댓글 관련 API")
	@Operation(summary = "특정 게시글에 댓글을 작성합니다.")
    @PostMapping
    public ApiResponse<CommentResponseDto> createComment(
			@Parameter(description = "댓글을 조회할 게시글의 id", required = true, example = "1")
			@PathVariable Long articleId, @RequestParam Long userId,
			@RequestBody @Valid CreateCommentRequestDto request) {
		return ApiResponse.onCreateSuccess("comments_create_success", commentCommandService.createComment(userId, articleId, request));
    }
	
	@Tag(name = "Comment", description = "댓글 관련 API")
	@Operation(summary = "특정 댓글을 수정합니다.")
    @PatchMapping("/{commentId}")
    public ApiResponse<CommentResponseDto> updateComment(
			@Parameter(description = "수정할 댓글이 달린 게시글의 id", required = true, example = "1")
			@PathVariable Long articleId,
			@Parameter(description = "수정할 댓글의 id", required = true, example = "1")
			@PathVariable Long commentId, @RequestParam Long userId,
			@RequestBody @Valid UpdateCommentRequestDto request) {
        return ApiResponse.onSuccess("comments_update_success", commentCommandService.updateComment(articleId, userId, commentId, request));
    }
	
	@Tag(name = "Comment", description = "댓글 관련 API")
	@Operation(summary = "특정 댓글을 삭제합니다.")
    @DeleteMapping("/{commentId}")
	@ApiResponses({
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "댓글 삭제 성공"),
	})
    public ResponseEntity<Void> deleteComment(
			@Parameter(description = "삭제할 댓글이 달린 게시글의 id", required = true, example = "1")
			@PathVariable Long articleId,
			@Parameter(description = "삭제할 댓글의 id", required = true, example = "1")
			@PathVariable Long commentId,
			@RequestParam Long userId) {
        commentCommandService.deleteComment(articleId, userId, commentId);
		return ApiResponse.onDeleteSuccess();
    }
}