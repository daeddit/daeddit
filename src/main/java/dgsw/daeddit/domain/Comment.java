package dgsw.daeddit.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment extends BaseEntity {

    public static final int CONTENT_MAX_LENGTH = 500;
    public static final int AUTHOR_MAX_LENGTH = 30;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @Column(nullable = false, length = CONTENT_MAX_LENGTH)
    private String content;

    @Column(nullable = false, length = AUTHOR_MAX_LENGTH)
    private String author;

    @Column(nullable = false)
    private long likeCount;

    @Column(nullable = false)
    private long dislikeCount;

    @Column(nullable = false)
    private boolean deleted;

    private Comment(Post post, Comment parent, String content, String author) {
        this.post = Objects.requireNonNull(post, "post");
        this.parent = parent;
        this.content = Objects.requireNonNull(content, "content");
        this.author = Objects.requireNonNull(author, "author");
        this.likeCount = 0L;
        this.dislikeCount = 0L;
        this.deleted = false;
    }

    public static Comment create(Post post, Comment parent, String content, String author) {
        return new Comment(post, parent, content, author);
    }

    public void update(String content) {
        this.content = Objects.requireNonNull(content, "content");
    }

    public void addLike() {
        this.likeCount++;
    }

    public void addDislike() {
        this.dislikeCount++;
    }

    public void softDelete() {
        this.deleted = true;
    }

    public boolean isReply() {
        return parent != null;
    }
}
