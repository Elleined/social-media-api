package com.elleined.socialmediaapi.model.story;

import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import com.elleined.socialmediaapi.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_story")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
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

    public boolean isExpired() {
        LocalDateTime storyCreation = this.getCreatedAt();
        LocalDateTime storyExpiration = storyCreation.plusDays(1);

        return LocalDateTime.now().isAfter(storyExpiration) ||
                LocalDateTime.now().equals(storyExpiration);
    }
}
