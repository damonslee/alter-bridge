package com.yaboong.alterbridge.application.api.post.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.yaboong.alterbridge.application.common.auditing.Auditable;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by yaboong on 2019-08-29.
 */
@Entity
@Table(name = "post")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "postId", callSuper = false)  // equals, hashcode 재정의 하는데 부모클래스 호출 안한다고 경고뜨는거 방지용이 callSuper = false
@FieldDefaults(level = AccessLevel.PRIVATE)
@DynamicInsert
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@JsonIgnoreProperties("parent")
public class Post extends Auditable<String> {

    public enum Status {
        NORMAL,
        DELETED,
        ACCUSED,
    }

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

    @ManyToOne
    Post parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default // 이거 없으면 lombok Builder 로 객체생성할때 new HashSet<>() 적용안돼서 add() 시 NPE 발생
    List<Post> comments = new ArrayList<>();

    public void addComment(Post comment) {
        this.comments.add(comment);
        comment.setParent(this); // 이 관계 설정 안해주면 comment 테이블에 post_id 가 null 로 들어감
    }

    // 양방향 매핑시 순환참조가 일어날 수 있으므로, toString() 을 직접 구현함
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
