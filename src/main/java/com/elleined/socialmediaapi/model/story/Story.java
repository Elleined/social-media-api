package com.elleined.socialmediaapi.model.story;

import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import com.elleined.socialmediaapi.model.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_story")
@NoArgsConstructor
@Getter
@Setter
public class Story extends PrimaryKeyIdentity {

    @Column(
            name = "content",
            nullable = false
    )
    private String content;

    @Column(name = "attach_picture")
    private String attachPicture;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "creator_id",
            referencedColumnName = "id",
            nullable = false,
            unique = true
    )
    private User creator;

    @Builder
    public Story(int id,
                 LocalDateTime createdAt,
                 LocalDateTime updatedAt,
                 String content,
                 String attachPicture,
                 User creator) {
        super(id, createdAt, updatedAt);
        this.content = content;
        this.attachPicture = attachPicture;
        this.creator = creator;
    }
}
