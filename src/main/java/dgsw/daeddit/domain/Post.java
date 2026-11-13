package dgsw.daeddit.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post extends BaseEntity {

    public static final int TITLE_MAX_LENGTH = 100;
    public static final int CONTENT_MAX_LENGTH = 1000;
    public static final int AUTHOR_MAX_LENGTH = 30;

    @Column(nullable = false, length = TITLE_MAX_LENGTH)
    private String title;

    @Column(nullable = false, length = CONTENT_MAX_LENGTH)
    private String content;

    @Column(nullable = false, length = AUTHOR_MAX_LENGTH)
    private String author;

    @Column(nullable = false)
    private Long likes;

    // TODO: Domain 엔티티 병합됐을 때 Domain에 대한 ManyToOne으로 바꿉니다.
    @Column(nullable = false)
    private String domain;

    private Post(String title, String content, String author, String domain) {
        this.title = Objects.requireNonNull(title, "title");
        this.content = Objects.requireNonNull(content, "content");
        this.author = Objects.requireNonNull(author, "author");
        this.likes = 0L;
        this.domain = domain;
    }

    public static Post create(String title, String content, String author, String domain) {
        return new Post(title, content, author, domain);
    }

    public void update(String title, String content, Long likes) {
        this.title = Objects.requireNonNull(title, "title");
        this.content = Objects.requireNonNull(content, "content");
        this.likes = Objects.requireNonNull(likes, "likes");
    }
}
