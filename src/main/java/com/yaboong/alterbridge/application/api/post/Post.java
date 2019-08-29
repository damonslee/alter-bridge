package com.yaboong.alterbridge.application.api.post;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.yaboong.alterbridge.application.api.comment.Comment;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

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
@EqualsAndHashCode(of = "postId")
@FieldDefaults(level = AccessLevel.PRIVATE)
@DynamicInsert
@DynamicUpdate
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "postId")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long postId;

    @Column(length = 255, nullable = false)
    String category;

    @Column(length = 255, nullable = false)
    String title;

    @Column(length = 10000, nullable = false)
    String content;

    @Column(nullable = false)
    @ColumnDefault("0")
    Long viewCount;

    @Column(nullable = false)
    @ColumnDefault("0")
    Long likeCount;

    @ColumnDefault("'N'")
    String deletedYn;

    @Column(nullable = false)
    String createdBy;

    @Column(nullable = false)
    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Comment> comments;

    // 양방향 매핑시 순환참조가 일어날 수 있으므로, toString() 을 직접 구현함
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
