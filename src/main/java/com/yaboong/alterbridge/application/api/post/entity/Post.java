package com.yaboong.alterbridge.application.api.post.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.yaboong.alterbridge.application.api.comment.Comment;
import com.yaboong.alterbridge.application.api.post.domain.PostDto;
import com.yaboong.alterbridge.application.common.auditing.Auditable;
import com.yaboong.alterbridge.application.common.type.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by yaboong on 2019-08-29.
 */
@Entity
@Table(name = "post")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "postId", callSuper = false)  // equals, hashcode 재정의 하는데 부모클래스 호출 안한다고 경고뜨는거 방지용이 callSuper = false
@FieldDefaults(level = AccessLevel.PRIVATE)
@DynamicInsert
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Post extends Auditable<String> implements Function<PostDto, Post> {

    public enum Category {
        GENERAL,
        IT,
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long postId;

    @Column(length = 255, nullable = false)
    @Enumerated(value = EnumType.STRING)
    Category category;

    @Column(length = 255)
    String title;

    @Column(length = 10000, nullable = false)
    String content;

    @Column(nullable = false)
    @ColumnDefault("0")
    Long viewCount;

    @Column(nullable = false)
    @ColumnDefault("0")
    Long likeCount;

    @Column(nullable = false)
    @ColumnDefault("'NORMAL'")
    @Enumerated(value = EnumType.STRING)
    Status status;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default // 이거 없으면 lombok Builder 로 객체생성할때 new HashSet<>() 적용안돼서 add() 시 NPE 발생
    List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.addPost(this); // 이 관계 설정 안해주면 comment 테이블에 post_id 가 null 로 들어감
    }

    public Post delete() {
        this.status = Status.DELETED;
        return this;
    }

    @Override
    public Post apply(PostDto postDto) {
        return Post.builder()
                .category(Category.valueOf(postDto.getCategory()))
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .viewCount(postDto.getViewCount())
                .likeCount(postDto.getLikeCount())
                .status(Status.valueOf(postDto.getStatus()))
                .build();
    }

    // 양방향 매핑시 순환참조가 일어날 수 있으므로, toString() 을 직접 구현함
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
