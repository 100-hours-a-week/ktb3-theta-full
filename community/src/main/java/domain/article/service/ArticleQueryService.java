package domain.article.service;

import domain.article.dto.ArticleResponseDto;
import domain.article.dto.GetArticlesResponseDto;
import domain.article.entity.Article;
import domain.article.repository.ArticleRepository;
import domain.user.entity.User;
import domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ArticleQueryService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public GetArticlesResponseDto getArticles(int page, int size) {
        List<Article> articles = articleRepository.findAll(page, size);
        List<Long> userIds = articles.stream()
                .map(Article::getUserId)
                .distinct()
                .collect(Collectors.toList());

        Map<Long, User> userMap = userRepository.findByIds(userIds).stream()
                .collect(Collectors.toMap(User::getUserId, Function.identity()));

        List<ArticleResponseDto> responses = articles.stream()
                .map(article -> {
                    User user = userMap.get(article.getUserId());
                    return ArticleResponseDto.fromEntity(article, user);
                })
                .collect(Collectors.toList());

        long totalCount = articleRepository.count();
        int totalPages = (int) Math.ceil((double) totalCount / size);
        boolean isLast = page >= totalPages;

        return new GetArticlesResponseDto(responses, page, (int) totalCount, totalPages, isLast);
    }

    public ArticleResponseDto getArticle(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("Article not found"));
        User user = userRepository.findById(article.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return ArticleResponseDto.fromEntity(article, user);
    }
}
