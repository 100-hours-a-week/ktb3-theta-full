package domain.comment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import domain.comment.entity.Comment;
import domain.user.dto.WrittenByResponseDto;
import domain.user.entity.User;

import java.time.LocalDateTime;

public record CommentResponseDto(
        @JsonProperty("comment_id")
        Long commentId,
        String content,
        LocalDateTime createdAt,
        WrittenByResponseDto writtenBy
) {
    public static CommentResponseDto fromEntity(Comment comment, User user) {
        return new CommentResponseDto(
                comment.getCommentId(),
                comment.getContent(),
                comment.getCreatedAt(),
                WrittenByResponseDto.fromEntity(user)
        );
    }
}
