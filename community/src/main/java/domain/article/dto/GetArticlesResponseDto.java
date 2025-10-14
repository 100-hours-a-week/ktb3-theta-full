package domain.article.dto;

import java.util.List;

public record GetArticlesResponseDto(
		
		List<ArticleResponseDto> articles,
		int currentPage,
		int totalCount,
		int totalPages,
		boolean isLast
) {
}
