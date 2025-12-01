package lab25.ordermanagement.articles.models;

import jakarta.persistence.*;
import lab25.ordermanagement.articles.enums.ArticleType;
import lab25.ordermanagement.common.BaseEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table(name = "articles")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ArticleEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ArticleType type;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String articleNumber;

    @Column
    private String description;

    public Long getId() { return this.id; }

    public ArticleType getArticleType() { return this.type; }
    public ArticleEntity setArticleType(ArticleType type) {
        this.type = type;
        return this;
    }

    public String getName() { return this.name; }
    public ArticleEntity setName(String name) {
        this.name = name;
        return this;
    }

    public ArticleType getType() { return this.type; }
    public ArticleEntity setType(ArticleType type) {
        this.type = type;
        return this;
    }

    public String getArticleNumber() { return this.articleNumber; }
    public ArticleEntity setArticleNumber(String articleNumber) {
        this.articleNumber = articleNumber;
        return this;
    }

    public String getDescription() { return this.description; }
    public ArticleEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public ArticleEntity setId(long l) {
        this.id = l;
        return this;
    }
}
