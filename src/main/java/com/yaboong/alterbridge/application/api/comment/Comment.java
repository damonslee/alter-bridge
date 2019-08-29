package com.yaboong.alterbridge.application.api.comment;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.yaboong.alterbridge.application.api.post.Post;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Created by yaboong on 2019-08-29.
 */
@Entity
@Table(name = "comment")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "commentId")
@FieldDefaults(level = AccessLevel.PRIVATE)
@DynamicInsert @DynamicUpdate
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property="commentId")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long commentId;

    @Column(nullable = false)
    Long postId;

    @ColumnDefault("0")
    Long likeCount;

    @Column(nullable = false)
    String content;

    @Column(nullable = false)
    String createdBy;

    @Column(nullable = false)
    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "postId", insertable = false, updatable = false)
    private Post post;

    // 양방향 매핑시 순환참조가 일어날 수 있으므로, toString() 을 직접 구현함
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
