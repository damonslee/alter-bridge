package com.yaboong.alterbridge.application.api.post;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.yaboong.alterbridge.application.api.boardfile.BoardFile;
import com.yaboong.alterbridge.application.api.comment.Comment;
import com.yaboong.alterbridge.application.common.auditing.Auditable;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
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
@EqualsAndHashCode(of = "postId", callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@DynamicInsert
@DynamicUpdate
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "postId")
public class Post extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long postId;

    @Column(length = 255, nullable = false)
    @Enumerated(value = EnumType.STRING)
    PostCategory category;

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

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default // 이거 없으면 lombok Builder 로 객체생성할때 new HashSet<>() 적용안돼서 add() 시 NPE 발생
    List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    List<BoardFile> files = new ArrayList<>();

    public void add(Comment comment) {
        comments.add(comment);
        comment.setPost(this); // 이 관계 설정 안해주면 comment 테이블에 post_id 가 null 로 들어감
    }

    public void addFile(BoardFile file) {
        this.files.add(file);
        file.setPost(this);
    }

    // 양방향 매핑시 순환참조가 일어날 수 있으므로, toString() 을 직접 구현함
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
