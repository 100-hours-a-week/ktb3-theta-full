package domain.comment.controller;

import domain.comment.dto.CommentResponseDto;
import domain.comment.dto.CreateCommentRequestDto;
import domain.comment.dto.GetCommentsResponseDto;
import domain.comment.dto.UpdateCommentRequestDto;
import domain.comment.service.CommentCommandService;
import domain.comment.service.CommentQueryService;
import global.apiPayload.ApiResponse;
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
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size", defaultValue = "7") int size) {
        return ApiResponse.onSuccess("comments_success", commentQueryService.getComments(articleId, page, size));
    }

    @PostMapping
    public ApiResponse<CommentResponseDto> createComment(@PathVariable Long articleId, @RequestParam Long userId, @RequestBody CreateCommentRequestDto request) {
		return ApiResponse.onCreateSuccess("comments_create_success", commentCommandService.createComment(userId, articleId, request));
    }

    @PatchMapping("/{commentId}")
    public ApiResponse<CommentResponseDto> updateComment(@PathVariable Long articleId, @PathVariable Long commentId, @RequestParam Long userId, @RequestBody UpdateCommentRequestDto request) {
        return ApiResponse.onSuccess("comments_update_success", commentCommandService.updateComment(userId, commentId, request));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long articleId, @PathVariable Long commentId, @RequestParam Long userId) {
        commentCommandService.deleteComment(userId, commentId);
		return ApiResponse.onDeleteSuccess();
    }
}