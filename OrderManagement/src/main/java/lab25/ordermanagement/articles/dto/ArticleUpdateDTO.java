package lab25.ordermanagement.articles.dto;

import lab25.ordermanagement.articles.enums.ArticleType;

public record ArticleUpdateDTO(
        ArticleType type,
        String name,
        String articleNumber,
        String description
) {
}
