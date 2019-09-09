package com.yaboong.alterbridge.application.api.comment;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.yaboong.alterbridge.application.api.boardfile.BoardFile;
import com.yaboong.alterbridge.application.api.post.Post;
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
@Table(name = "comment")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "commentId", callSuper = false) // equals, hashcode 재정의 하는데 부모클래스 호출 안한다고 경고뜨는거 방지용이 callSuper = false
@FieldDefaults(level = AccessLevel.PRIVATE)
@DynamicInsert @DynamicUpdate
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property="commentId")
public class Comment extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long commentId;

    @ColumnDefault("0")
    Long likeCount;

    @ColumnDefault("'N'")
    String deletedYn;

    @Column(nullable = false)
    String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    Post post;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    List<BoardFile> files = new ArrayList<>();

    public void addFile(BoardFile file) {
        this.files.add(file);
        file.setComment(this);
    }

    // 양방향 매핑시 순환참조가 일어날 수 있으므로, toString() 을 직접 구현함
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
