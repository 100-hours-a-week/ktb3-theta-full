package ktb.week4.community.domain.comment.controller;

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

    @GetMapping
    public ApiResponse<GetCommentsResponseDto> getComments(@PathVariable Long articleId,
            @RequestParam(name = "page")  @Min(value = 1, message = "Page parameter must be at least 1") int page,
            @RequestParam(name = "size", defaultValue = "7") @Min(value = 1, message = "Size parameter must be at least 1") int size) {
        return ApiResponse.onSuccess("comments_success", commentQueryService.getComments(articleId, page, size));
    }

    @PostMapping
    public ApiResponse<CommentResponseDto> createComment(@PathVariable Long articleId, @RequestParam Long userId, @RequestBody @Valid CreateCommentRequestDto request) {
		return ApiResponse.onCreateSuccess("comments_create_success", commentCommandService.createComment(userId, articleId, request));
    }

    @PatchMapping("/{commentId}")
    public ApiResponse<CommentResponseDto> updateComment(@PathVariable Long articleId, @PathVariable Long commentId, @RequestParam Long userId, @RequestBody @Valid UpdateCommentRequestDto request) {
        return ApiResponse.onSuccess("comments_update_success", commentCommandService.updateComment(articleId, userId, commentId, request));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long articleId, @PathVariable Long commentId, @RequestParam Long userId) {
        commentCommandService.deleteComment(articleId, userId, commentId);
		return ApiResponse.onDeleteSuccess();
    }
}